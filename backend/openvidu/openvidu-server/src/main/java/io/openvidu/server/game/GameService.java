package io.openvidu.server.game;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.openvidu.client.internal.ProtocolElements;
import io.openvidu.server.core.Participant;
import io.openvidu.server.rpc.RpcNotificationService;
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
    protected ConcurrentHashMap<String, ArrayList<Thread>> gameThread = new ConcurrentHashMap<>();
    // 직업 관리 <sessionId , role>
    protected static ConcurrentHashMap<String, ArrayList<Roles>> gameRoles = new ConcurrentHashMap<>();
    // 역할 분배 관리 <sessionId, <Participant, role>>
    protected static ConcurrentHashMap<String, ArrayList<Characters>> userRoles = new ConcurrentHashMap<>();
    // 명함 교환 관리
    protected static ConcurrentHashMap<String, ArrayList<Participant>> exchangeCandidates = new ConcurrentHashMap<>();
    // 미션 대상 관리
    protected static ConcurrentHashMap<String, ArrayList<Participant>> missionCandidates = new ConcurrentHashMap<>();
    // 살아있는 경찰 수 관리
    protected static ConcurrentHashMap<String, Integer> alivePolices = new ConcurrentHashMap<>();

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
                gameStart(participant, message, sessionId, participants, params, data, notice);
                return;
            case USESKILL: // 스킬 사용
                useSkill(participant, message, sessionId, participants, params, data, notice);
                return;
            case EXCHANGENAME: // 명함 교환
                makeCandidates(participant, message, sessionId, participants, params, data, notice);
                return;
        }
    }

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

        Thread missionThread = gameThread.get(sessionId).get(0);
        Thread nameThread = gameThread.get(sessionId).get(0);

        //자원 반납
        gameThread.remove(sessionId);
        gameRoles.remove(sessionId);
        userRoles.remove(sessionId);

        if (missionThread != null) {
            missionThread.interrupt();
        }
        if (nameThread != null) {
            nameThread.interrupt();
        }

        data.addProperty("gameStatus", 4);
        params.add("data", data);

        //게임 종료 알리기
        for (Participant p : participants) {
            rpcNotificationService.sendNotification(p.getParticipantPrivateId(),
                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
        }
    }

    private void makeCandidates(Participant participant, JsonObject message, String sessionId, Set<Participant> participants,
                                JsonObject params, JsonObject data, RpcNotificationService notice) {

        //이렇게 넣으면 되려나?? if로 비교해야되나?
        //있으면 가져와서 넣기, 없으면 Arraylist만들어서 participant 넣고 exchangeCandidates에 넣기
        if (exchangeCandidates.containsKey(sessionId)) {
            exchangeCandidates.get(sessionId).add(participant);
        } else {
            ArrayList<Participant> pList = new ArrayList<Participant>();
            pList.add(participant);
            exchangeCandidates.put(sessionId, pList);
        }

    }

    private void useSkill(Participant participant, JsonObject message, String sessionId, Set<Participant> participants,
                          JsonObject params, JsonObject data, RpcNotificationService notice) {

        /**
         * 데이터 전송 예시
         * type : 'game';
         * data :
         * {
         *   gameStatus : 2,
         *   target : connectionId,
         *   name : 'L', 'KIRA', 'GUARD', 'BROADCASTER', 'CRIMINAL', 'POLICE' 중 하나.
         * }
         */

        String skillTarget = data.get("target").getAsString();
        String name = data.get("name").getAsString();
        ArrayList<Characters> cList = userRoles.get(sessionId);
        Characters target = null;

        //connectionId로 Character 찾아옴.
        //connectionId 자원관리 필수!!!! 나중에 숫자로 관리 된다면 편하게 구현 가능.
        for (int i = 0; i < cList.size(); i++) {
            if (cList.get(i).getParticipant().getParticipantPublicId().equals(skillTarget)) {
                target = cList.get(i);
            }
        }

        //skill대상의 직업이 name과 일치하는지 체크
        if (target.getRoles().toString().equals(name)) {
            //사망처리
            target.setAlive(false);
            //경찰일시 경찰 수 -1;
            if (target.getRoles() == Roles.POLICE) {
                alivePolices.computeIfPresent(sessionId, (k, v) -> v - 1);
            }

            //키라 사망 or 경찰 수 0명시 게임 종료
            if (target.getRoles() == Roles.KIRA || alivePolices.get(sessionId) < 1) {
                finishGame(participant, sessionId, participants, params, data);
            } else {
                //사망 소식 전하기.
                data = new JsonObject();
                data.addProperty("dead", target.getParticipant().getParticipantPublicId());
                params.add("data", data);
                for (Participant p : participants) {
                    rpcNotificationService.sendNotification(p.getParticipantPrivateId(),
                            ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
                }
            }
        }


    }

    private void gameStart(Participant participant, JsonObject message, String sessionId, Set<Participant> participants,
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
        missionCandidates.put(sessionId, mCandidates);


        //userRoles에 저장하여 관리. key값이 participant별로 역할 찾기 쉬움.
        userRoles.put(sessionId, roleMatching);

        //각자에게 역할 알려주기.
        for (int i = 0; i < roleMatching.size(); i++) {
            data.addProperty("role", roleMatching.get(i).getRoles().toString());
            params.add("data", data);
            rpcNotificationService.sendNotification(roleMatching.get(i).getParticipant().getParticipantPrivateId(),
                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
        }

        //쓰래드 생성 및 등록.
        ExchangeNameRunnable exchangeNameRun = new ExchangeNameRunnable(sessionId, notice);
        MissionStartRunnable missionStartRunnable = new MissionStartRunnable(sessionId, userRoles.get(sessionId), notice);
        Thread nameThread = new Thread(exchangeNameRun);
        Thread missionThread = new Thread(missionStartRunnable);

        //스래드 시작.(명교, 미션 쓰레드 두개 다 시작)
        nameThread.start();
        missionThread.start();

        //쓰래드 등록(둘 다 등록)
        ArrayList<Thread> threadList = new ArrayList<>();
        threadList.add(nameThread);
        threadList.add(missionThread);

        //게임 끝날때 삭제 했기 때문에 ifAbsent씀.
        gameThread.putIfAbsent(sessionId, threadList);

    }

    private void prepareGame(Participant participant, String sessionId,
                             Set<Participant> participants, JsonObject params, JsonObject data, RpcNotificationService notice) {
        log.info("PrepareGame is called by {}", participant.getParticipantPublicId());

        ArrayList<Roles> playRoles = new ArrayList<>();
        int polices = 0;

        //L, KIRA는 각각 한명씩 데이터에 넣을 필요 X
        playRoles.add(Roles.L);
        playRoles.add(Roles.KIRA);

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

        //살아있는 경찰 수 관리
        alivePolices.putIfAbsent(sessionId, polices);

        //게임 롤 등록
        gameRoles.put(sessionId, playRoles);

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
