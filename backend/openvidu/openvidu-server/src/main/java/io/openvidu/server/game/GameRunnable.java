package io.openvidu.server.game;

import com.google.gson.JsonObject;
import io.openvidu.client.internal.ProtocolElements;
import io.openvidu.server.core.Participant;
import io.openvidu.server.rpc.RpcNotificationService;

import java.util.ArrayList;
import java.util.Collections;

public class GameRunnable implements Runnable {

    private ArrayList<Characters> userRoles;
    private ArrayList<Participant> missionCandidates;
    private ArrayList<Participant> participantsList;
    private RpcNotificationService notice;
    private String sessionId;

    public GameRunnable(String sessionId, ArrayList<Characters> userRoles, ArrayList<Participant> participantsList, ArrayList<Participant> missionCandidates, RpcNotificationService notice) {
        this.sessionId = sessionId;
        this.userRoles = userRoles;
        this.missionCandidates = missionCandidates;
        this.notice = notice;
    }

    @Override
    public void run() {

//        //미션 수행할 사람 목록.
//        ArrayList<Participant> missionCandidates = new ArrayList<>();
//        //키라랑 L빼고 미션 수행 대기자로 등록
//        for (Participant p : userRoles.keySet()) {
//            if (userRoles.get(p) == Roles.L || userRoles.get(p) == Roles.KIRA) continue;
//            else {
//                missionCandidates.add(p);
//            }
//        }
//
//        //명함 교환할 사람 목록.
//        ArrayList<Participant> exchangeCandidates = new ArrayList<>(participants);


        try {
//            String temp = "signal:game";
//            params.addProperty("type", temp);
            JsonObject data = new JsonObject();
            JsonObject params = new JsonObject();

            int exchangeCnt = 1;
            int misssionCnt = 1;
            while (true) {
                //처음 30초 sleep
                Thread.sleep(30000);

                /**
                 * 명교 시작.
                 */

                //모든 참가자 목록 가져오기(랜덤 2명)
                ArrayList<Participant> pList = participantsList;

                //참가자 목록 섞기
                Collections.shuffle(pList);

                //data에 담을 정보
                data.addProperty("action", "missionStart");
                data.addProperty("turn", exchangeCnt);
                params.add("data", data);

                //2명만 명교 시작.
                for (int i = 0; i < 2; i++) {
                    notice.sendNotification(pList.get(i).getParticipantPrivateId(),
                            ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
                }
                exchangeCnt++;
                /**
                 * 명교 끝
                 */

                /**
                 * 명교 시작.
                 */
                //30초 sleep후 명교, 미션 동시에 실행.
                Thread.sleep(30000);

                //참가자 목록 섞기
                Collections.shuffle(pList);

                //cnt 횟수 갱신해서 보내기.
                data.addProperty("turn", exchangeCnt);
                params.add("data", data);

                //2명만 명교 시작.
                for (int i = 0; i < 2; i++) {
                    notice.sendNotification(pList.get(i).getParticipantPrivateId(),
                            ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
                }

                exchangeCnt++;
                /**
                 * 명교 끝
                 */

                /**
                 * 미션 시작.
                 */

                //역할 목록 가져오기
                ArrayList<Characters> roles = userRoles;

                //미션 대기자 목록 섞기
                Collections.shuffle(missionCandidates);

                //data에 담을 정보.
                data.addProperty("action", "missionStart");
                data.addProperty("turn", misssionCnt);
                params.add("data", data);

                //2명만 미션 시작.
                for (int i = 0; i < 2; i++) {
                    notice.sendNotification(missionCandidates.get(i).getParticipantPrivateId(),
                            ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
                }

                boolean isPolice = false;
                //미션 수행중인 사람이 police면 추종자에게 알리기.
                for (int i = 0; i < roles.size(); i++) {
                    if (roles.get(i).getParticipant() == missionCandidates.get(0) || roles.get(i).getParticipant() == missionCandidates.get(1)) {
                        isPolice = true;
                        break;
                    }
                }

                if (isPolice) {
                    //data에 담을 정보.
                    data.remove("action");
                    data.addProperty("announce", "policeMissionStart");
                    params.add("data", data);

                    //추종자들에게 전달.
                    for (int i = 0; i < roles.size(); i++) {
                        if (roles.get(i).getRoles() == Roles.CRIMINAL) {
                            notice.sendNotification(roles.get(i).getParticipant().getParticipantPrivateId(),
                                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
                        }
                    }
                }

                misssionCnt++;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}