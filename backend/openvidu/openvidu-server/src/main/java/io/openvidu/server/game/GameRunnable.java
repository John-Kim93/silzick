package io.openvidu.server.game;

import com.google.gson.JsonObject;
import io.openvidu.client.internal.ProtocolElements;
import io.openvidu.server.core.Participant;
import io.openvidu.server.rpc.RpcNotificationService;

import java.util.ArrayList;
import java.util.Collections;

public class GameRunnable implements Runnable {

    private ArrayList<Participant> participantsList;
    private RpcNotificationService rpcNotificationService;
    private String sessionId;

    public GameRunnable(String sessionId, ArrayList<Participant> participantsList, RpcNotificationService notice) {
        this.sessionId = sessionId;
        this.participantsList = participantsList;
        this.rpcNotificationService = notice;
    }

    @Override
    public void run() {
        try {
            JsonObject data = new JsonObject();
            JsonObject params = new JsonObject();
            int exchangeCnt = 1;
            int misssionCnt = 1;

            //타입 지정
            String type = "signal:autoSystem";
            params.addProperty(ProtocolElements.PARTICIPANTSENDMESSAGE_TYPE_PARAM, type);

//            //모든 참가자 목록 가져오기(랜덤 2명)
//            ArrayList<Participant> pList = participantsList;


            while (true) {
                //역할 목록 가져오기
                ArrayList<Characters> roles = GameService.roleMatching.get(sessionId);

                //살아있는 대상자만
                ArrayList<Characters> list = new ArrayList<>();
                for (Characters c : roles) {
                    if (c.isAlive()) {
                        list.add(c);
                    }
                }

                roles = GameService.roleMatching.get(sessionId);
                /**
                 * 명교 시작.
                 */
                ExchangeName(list, data, exchangeCnt, params);
                exchangeCnt++;
                /**
                 * 명교 끝
                 */

                //살아있는 대상자만
                list = new ArrayList<>();
                for (Characters c : roles) {
                    if (c.isAlive()) {
                        list.add(c);
                    }
                }

                /**
                 * 명교 시작.
                 */
                ExchangeName(list, data, exchangeCnt, params);
                exchangeCnt++;
                /**
                 * 명교 끝
                 */

                /**
                 * 미션 시작.
                 */
                //미션 시작할 사람 목록
                ArrayList<Characters> missionCandidates = new ArrayList<>(list);
                //0,1번은 명교하러 갔으니 제외
                missionCandidates.remove(1);
                missionCandidates.remove(0);

                //미션 대기자 목록 섞기
                Collections.shuffle(missionCandidates);

                //data에 담을 정보.
                data.addProperty("action", "missionStart");
                data.addProperty("turn", misssionCnt);
                params.add("data", data);

                //2명만 미션 시작.
                for (int i = 0; i < 2; i++) {
                    rpcNotificationService.sendNotification(missionCandidates.get(i).getParticipant().getParticipantPrivateId(),
                            ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
                }

                boolean isPolice = false;
                //미션 수행중인 사람이 police면 추종자에게 알리기.
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getRoles() == Roles.POLICE &&
                            (list.get(i) == missionCandidates.get(0) || list.get(i) == missionCandidates.get(1))) {
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
                            rpcNotificationService.sendNotification(roles.get(i).getParticipant().getParticipantPrivateId(),
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

    private void ExchangeName(ArrayList<Characters> roles, JsonObject data, int exchangeCnt, JsonObject params) throws InterruptedException {
        /**
         * 명교 시작.
         */
        //처음 30초 sleep
        Thread.sleep(30000);

        //참가자 목록 섞기
        Collections.shuffle(roles);

        //data에 담을 정보
        data.addProperty("action", "exchangeNameStart");
        data.addProperty("turn", exchangeCnt);
        params.add("data", data);

        //2명만 명교 시작.
        for (int i = 0; i < 2; i++) {
            rpcNotificationService.sendNotification(roles.get(i).getParticipant().getParticipantPrivateId(),
                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
        }
        /**
         * 명교 끝
         */
    }
}