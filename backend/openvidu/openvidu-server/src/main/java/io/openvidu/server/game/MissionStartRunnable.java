package io.openvidu.server.game;

import com.google.gson.JsonObject;
import io.openvidu.client.internal.ProtocolElements;
import io.openvidu.server.core.Participant;
import io.openvidu.server.rpc.RpcNotificationService;

import java.util.ArrayList;
import java.util.Collections;

public class MissionStartRunnable implements Runnable {

    private ArrayList<Characters> userRoles;
    private RpcNotificationService notice;
    private String sessionId;

    public MissionStartRunnable(String sessionId, ArrayList<Characters> userRoles, RpcNotificationService notice) {
        this.sessionId = sessionId;
        this.userRoles = userRoles;
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

            int cnt = 1;
            while (true) {

                //60초마다 실행.
                Thread.sleep(60000);

                //명교 대기자 목록 가져오기
                ArrayList<Participant> mCandidates = GameService.missionCandidates.get(sessionId);
                ArrayList<Characters> roles = userRoles;

                //대기자 목록 섞기
                Collections.shuffle(mCandidates);

                //data에 담을 정보.
                data.addProperty("action", "missionStart");
                data.addProperty("turn", cnt);
                params.add("data", data);

                //2명만 미션 시작.
                for (int i = 0; i < 2; i++) {
                    notice.sendNotification(mCandidates.get(i).getParticipantPrivateId(),
                            ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
                }

                boolean isPolice = false;
                //미션 수행중인 사람이 police면 추종자에게 알리기.
                for (int i = 0; i < roles.size(); i++) {
                    if (roles.get(i).getParticipant() == mCandidates.get(0) || roles.get(i).getParticipant() == mCandidates.get(1)) {
                        isPolice = true;
                        break;
                    }
                }

                if(isPolice) {
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

                cnt++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}