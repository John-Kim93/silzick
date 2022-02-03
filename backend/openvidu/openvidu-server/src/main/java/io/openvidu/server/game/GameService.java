/*
 * (C) Copyright 2017-2020 OpenVidu (https://openvidu.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.openvidu.server.game;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.openvidu.client.internal.ProtocolElements;
import io.openvidu.server.core.Participant;
import io.openvidu.server.rpc.RpcNotificationService;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameService {

    static final int PREPAREGAME = 0;
    static final int GAMESTART = 1;
    static final int USESKILL = 2;
    static final int EXCHANGENAME = 3;


    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    static RpcNotificationService rpcNotificationService;

    /**
     * 게임 정보 관리.
     */
    //Tread 관리
    protected ConcurrentHashMap<String, Thread> gameThread = new ConcurrentHashMap<>();
    // 직업 관리 <sessionId , role>
    protected static ConcurrentHashMap<String, ArrayList<Roles>> gameRoles = new ConcurrentHashMap<>();
    // 역할 분배 관리 <sessionId, <Participant, role>>
    protected static ConcurrentHashMap<String, ArrayList<Characters>> userRoles = new ConcurrentHashMap<>();
    // 미션 대상 관리
    protected static ConcurrentHashMap<String, ArrayList<Participant>> missionCandidates = new ConcurrentHashMap<>();
    // 참가자 목록 관리
    protected static ConcurrentHashMap<String, ArrayList<Participant>> participantsList = new ConcurrentHashMap<>();
    // 살아있는 경찰 수 관리
    protected static ConcurrentHashMap<String, Integer> alivePolices = new ConcurrentHashMap<>();
    // 경찰총장, 노트주인 따로 관리
    protected static ConcurrentHashMap<String, ArrayList<Participant>> captainList = new ConcurrentHashMap<>();
    // 데스노트 적힌사람.
    protected static ConcurrentHashMap<String, ArrayList<Characters>> noteList = new ConcurrentHashMap<>();

    public void gameNavigator(Participant participant, JsonObject message, Set<Participant> participants,
                              String sessionId, RpcNotificationService notice) {

        rpcNotificationService = notice;
        JsonObject params = new JsonObject();

        String type = null;

        //타입은 그대로 돌려줌.
        if (message.has("type")) {
            type = message.get("type").getAsString();
            params.addProperty(ProtocolElements.PARTICIPANTSENDMESSAGE_TYPE_PARAM, type);
        }
        // data 파싱
        String dataString = message.get("data").getAsString();
        JsonObject data = (JsonObject) JsonParser.parseString(dataString);

        // data에 gameStatus로 게임 상태 분기
        int gameStatus = data.get("gameStatus").getAsInt();

        // 현재 상황 어떤지 담아서 돌려줌. params에 type, data 담김.
        data.addProperty("gameStatus", Integer.toString(gameStatus));
        switch (gameStatus) {
            case PREPAREGAME: // 게임 준비 화면
                prepareGame(participant, sessionId, participants, params, data, notice);
                return;
            case GAMESTART: // 게임 시작
                gameStart(participant, sessionId, participants, params, data, notice);
                return;
            case USESKILL: // 스킬 사용
                useSkill(participant, sessionId, participants, params, data, notice);
                return;
            case EXCHANGENAME: // 명교 후 류자키에게 결과 전달 메소드.
                exchangeName(participant, sessionId, params, data);
                return;
        }
    }

    //명교 결과 보내주기.
    private void exchangeName(Participant participant, String sessionId, JsonObject params, JsonObject data) {

        String name = data.get("name").getAsString();

        //역할 가져오기
        ArrayList<Characters> cList = userRoles.get(sessionId);
        Characters target = null;
        //역할 리스트에서 신호보낸 Participant찾아내기
        for (Characters c : cList) {
            if (c.getParticipant() == participant) {
                target = c;
                break;
            }
        }

        //data 초기화
        data = new JsonObject();
        //명교때 제출한 이름(직업)과 진짜 이름이 같으면 true 아니면 false
        if (target.getRoles().equals(name)) {
            data.addProperty("result", "true");
        } else {
            data.addProperty("result", "false");
        }

        //params에 data 넣기.
        params.add("data", data);

        //중요인물 리스트의 0번 = 키라, 1번 = 경찰총장
        Participant L = captainList.get(sessionId).get(1);
        //명교 결과 경찰 총장에게 알리기.
        rpcNotificationService.sendNotification(L.getParticipantPrivateId(),
                ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);

    }


    //게임 종료 메소드
    private void finishGame(Participant participant, String sessionId, Set<Participant> participants, JsonObject params, JsonObject data) {

        /**
         * 게임 종료시 전달되는 데이터 예시
         * type : 'game';
         * data :
         * {
         *   gameStatus : 4,
         * }
         */

        log.info("finishGame is called by {}", participant.getParticipantPublicId());

        Thread deathNoteThread = gameThread.get(sessionId);

        //자원 반납
        //쓰래드 자원 반납
        gameThread.remove(sessionId);
        //사용 직업 리스트 자원 반납
        gameRoles.remove(sessionId);
        //유저 직업 매칭 자원 반납
        userRoles.remove(sessionId);
        //미션 수행자 목록 자원 반납.
        missionCandidates.remove(sessionId);
        //참여자 목록 자원 반납.
        participantsList.remove(sessionId);
        //살아있는 경찰 수 자원 반납.
        alivePolices.remove(sessionId);
        //중요인물 자원 반납
        captainList.remove(sessionId);
        //노트 자원 반납.
        noteList.remove(sessionId);


        if (deathNoteThread != null) {
            deathNoteThread.interrupt();
        }

        data.addProperty("gameStatus", 4);
        params.add("data", data);

        //게임 종료 알리기
        for (Participant p : participants) {
            rpcNotificationService.sendNotification(p.getParticipantPrivateId(),
                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
        }
    }


    //스킬 사용 메소드
    private void useSkill(Participant participant, String sessionId, Set<Participant> participants,
                          JsonObject params, JsonObject data, RpcNotificationService notice) {

        /**
         * 데이터 전송 예시
         * type : 'game';
         * data :
         * {
         *   gameStatus : 2,
         *   skillType : kill / protect / announce / note / noteUse
         *   target : connectionId, / all(방송 기능 설정????)
         *   (kill, note에만 필요) name : 'L', 'KIRA', 'GUARD', 'BROADCASTER', 'CRIMINAL', 'POLICE' 중 하나.
         * }
         */
        //사용하는 스킬 타입 구별
        String skillType = data.get("skillType").getAsString();
        //역할 리스트 가져오기.
        ArrayList<Characters> cList = userRoles.get(sessionId);

        String skillTarget = data.get("target").getAsString();
        Characters target = null;
        String name = null;

        //connectionId로 Character 찾아옴.
        //connectionId 자원관리 필수!!!! 나중에 숫자로 관리 된다면 편하게 구현 가능.
        for (int i = 0; i < cList.size(); i++) {
            if (cList.get(i).getParticipant().getParticipantPublicId().equals(skillTarget)) {
                target = cList.get(i);
            }
        }

        //중요인물 리스트의 0번 = 키라, 1번 = 경찰총장
        Participant KIRA = captainList.get(sessionId).get(0);

        switch (skillType) {
            case "kill":
                name = data.get("name").getAsString();
                //skill대상의 직업이 name과 일치하는지 체크
                if (target.getRoles().toString().equals(name)) {
                    //보호되는 상태가 아니면
                    if (!target.isProtected()) {
                        //사망처리
                        target.setAlive(false);

                        //경찰일시 경찰 수 -1;
                        if (target.getRoles() == Roles.POLICE) {
                            alivePolices.computeIfPresent(sessionId, (k, v) -> v - 1);
                        }

                        //사망 소식 전하기.
                        data = new JsonObject();
                        data.addProperty("dead", target.getParticipant().getParticipantPublicId());
                        params.add("data", data);
                        for (Participant p : participants) {
                            rpcNotificationService.sendNotification(p.getParticipantPrivateId(),
                                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);

                        }
                        //보호 중이면.
                    } else {
                        //방어됨 소식 알리기.
                        data = new JsonObject();
                        data.addProperty("isprotected", target.getParticipant().getParticipantPublicId());
                        params.add("data", data);
                        for (Participant p : participants) {
                            rpcNotificationService.sendNotification(p.getParticipantPrivateId(),
                                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
                        }
                    }
                }
                break;
            case "protect":
                //스킬 타겟 보호 설정
                target.setProtected(true);
                break;
            case "announce":
                break;
            case "note":
                name = data.get("name").getAsString();
                ArrayList<Characters> nList = noteList.get(sessionId);

                //성공시 이름 적은 아이디, 실패시 실패 문구.
                if (target.getRoles().toString().equals(name)) {
                    nList.add(target);
                    data.addProperty("writeName", target.getParticipant().getParticipantPublicId());
                    params.add("data", data);
//                    ArrayList<Characters> nList = new ArrayList<Characters>();
//                    nList.add(target);
//                    noteList.putIfAbsent(sessionId,nList);

                } else {
                    data.addProperty("writeName", "the name isn't matched");
                    params.add("data", data);
                }
                //스킬 사용 결과 키라에게 알리기.
                rpcNotificationService.sendNotification(KIRA.getParticipantPrivateId(),
                        ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
                break;
//            case "noteUse":
//                for(Characters c : not) {
//                    //보호되는 상태가 아니면
//                    if (!target.isProtected()) {
//                        //사망처리
//                        target.setAlive(false);
//
//                        //경찰일시 경찰 수 -1;
//                        if (target.getRoles() == Roles.POLICE) {
//                            alivePolices.computeIfPresent(sessionId, (k, v) -> v - 1);
//                        }
//
//                        //사망 소식 전하기.
//                        data = new JsonObject();
//                        data.addProperty("dead", target.getParticipant().getParticipantPublicId());
//                        params.add("data", data);
//                        for (Participant p : participants) {
//                            rpcNotificationService.sendNotification(p.getParticipantPrivateId(),
//                                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
//
//                        }
//                        //보호 중이면.
//                    } else {
//                        //방어됨 소식 알리기.
//                        data = new JsonObject();
//                        data.addProperty("isprotected", target.getParticipant().getParticipantPublicId());
//                        params.add("data", data);
//                        for (Participant p : participants) {
//                            rpcNotificationService.sendNotification(p.getParticipantPrivateId(),
//                                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
//                        }
//                    }
//                }
//                break;
        }


        //키라 사망 or 경찰 수 0명시 게임 종료
        if (target.getRoles() == Roles.KIRA || alivePolices.get(sessionId) < 1) {
            finishGame(participant, sessionId, participants, params, data);
        }


    }

    //게임 시작 메소드
    private void gameStart(Participant participant, String sessionId, Set<Participant> participants,
                           JsonObject params, JsonObject data, RpcNotificationService notice) {

        /**
         * 데이터 전송 예시
         * type : 'game';
         * data :
         * {
         *   gameStatus : 1,
         * }
         */
        //참가자 목록 가져와서 shuffle
        ArrayList<Participant> players = new ArrayList<>(participants);
        Collections.shuffle(players);

        //직업 목록 선택한것 가져오기
        ArrayList<Roles> roles = gameRoles.get(sessionId);

        ArrayList<Participant> KIRAandL = new ArrayList<>();
        //키라 입력
        KIRAandL.add(players.get(0));
        //경찰 총장 입력.
        KIRAandL.add(players.get(1));

        //중요 역할들 목록에 담기
        captainList.putIfAbsent(sessionId, KIRAandL);

        //플레이어와 직업 목록 연결시킬 hashmap만들기
        ArrayList<Characters> roleMatching = new ArrayList<>();
        //역할 분배
        for (int i = 0; i < players.size(); i++) {
            roleMatching.add(new Characters(roles.get(i), players.get(i)));
        }

        //미션 수행할 사람 목록.
        ArrayList<Participant> mCandidates = new ArrayList<>();
        //키라랑 L빼고 미션 수행 대기자로 등록
        for (Characters c : roleMatching) {
            if (c.getRoles() == Roles.L || c.getRoles() == Roles.KIRA) continue;
            else {
                mCandidates.add(c.getParticipant());
            }
        }
        // 미션 수행자 목록 넣어서 관리.
        missionCandidates.putIfAbsent(sessionId, mCandidates);


        //userRoles에 저장하여 관리. key값이 participant별로 역할 찾기 쉬움.
        userRoles.putIfAbsent(sessionId, roleMatching);

        //각자에게 역할 알려주기.
        for (int i = 0; i < roleMatching.size(); i++) {
            data.addProperty("role", roleMatching.get(i).getRoles().toString());
            params.add("data", data);
            rpcNotificationService.sendNotification(roleMatching.get(i).getParticipant().getParticipantPrivateId(),
                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
        }

        //쓰래드 생성 및 등록.
        GameRunnable gameRunnable = new GameRunnable(sessionId, userRoles.get(sessionId), participantsList.get(sessionId), missionCandidates.get(sessionId), notice);
        Thread deathNoteThread = new Thread(gameRunnable);

        //스래드 시작.(명교, 미션 쓰레드 두개 다 시작)
        deathNoteThread.start();

        //게임 끝날때 삭제 했기 때문에 ifAbsent씀.
        gameThread.putIfAbsent(sessionId, deathNoteThread);

    }

    //게임 준비 메소드
    private void prepareGame(Participant participant, String sessionId,
                             Set<Participant> participants, JsonObject params, JsonObject data, RpcNotificationService notice) {
        log.info("PrepareGame is called by {}", participant.getParticipantPublicId());

        ArrayList<Roles> playRoles = new ArrayList<>();
        int polices = 0;

        //KIRA, 경찰총장(L)은 각각 한명씩 데이터에 넣을 필요 X
        playRoles.add(Roles.KIRA);
        playRoles.add(Roles.L);

        //boolean으로 하면 귀찮으니 전부 int로 방송인과 보디가드는 최대 1명.
        /**
         * 데이터 전송 예시
         * type : 'game';
         * data :
         * {
         *   gameStatus : 0,
         *   GUARD : 1,
         *   BROADCASTER : 0,
         *   CRIMINAL : 1,
         *   POLICE : 2
         * }
         */
        if (data.get("GUARD").getAsInt() > 0) {
            playRoles.add(Roles.GUARD);
        }
        if (data.get("BROADCASTER").getAsInt() > 0) {
            playRoles.add(Roles.BROADCASTER);
        }
        for (int i = 0; i < data.get("CRIMINAL").getAsInt(); i++) {
            playRoles.add(Roles.CRIMINAL);
        }
        for (int i = 0; i < data.get("POLICE").getAsInt(); i++) {
            playRoles.add(Roles.POLICE);
            polices++;
        }
        //참여자 목록 등록
        ArrayList<Participant> pList = new ArrayList<>(participants);
        participantsList.putIfAbsent(sessionId, pList);

        //살아있는 경찰 수 관리
        alivePolices.putIfAbsent(sessionId, polices);

        //게임 롤 등록
        gameRoles.putIfAbsent(sessionId, playRoles);

        //노트 리스트 등록.
//        noteList.putIfAbsent(sessionId, new ArrayList<Participant>());

//        //참가자 수보다 역할이 많으면 에러 발생?
//        data.addProperty("warning", "참가자 수와 역활 수가 맞지 않습니다.");
//        data.addProperty("gameStatus", 0);
//        params.add("data", data);
//
//        if (playRoles.size() > participants.size()) {
//            for (Participant p : participants) {
//                rpcNotificationService.sendNotification(p.getParticipantPrivateId(),
//                        ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
//            }
//        }else {
//            gameRoles.put(sessionId, playRoles);
//        }

    }

}
