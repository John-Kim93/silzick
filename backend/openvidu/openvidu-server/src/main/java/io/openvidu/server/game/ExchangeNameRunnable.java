package io.openvidu.server.game;

import com.google.gson.JsonObject;
import io.openvidu.client.internal.ProtocolElements;
import io.openvidu.server.core.Participant;
import io.openvidu.server.rpc.RpcNotificationService;

import java.util.*;

public class ExchangeNameRunnable implements Runnable {

    private RpcNotificationService notice;
    private String sessionId;

    public ExchangeNameRunnable(String sessionId, RpcNotificationService notice) {
        this.sessionId = sessionId;
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
            JsonObject data = new JsonObject();
            JsonObject params = new JsonObject();

//            String temp = "signal:game";
//            params.addProperty("type", temp);
            int cnt = 1;
            while(true) {

                //1초마다 명교 희망자 확인.
                Thread.sleep(1000);

                //명교 대기자 목록 가져오기
                ArrayList<Participant> nCandidates = GameService.exchangeCandidates.get(sessionId);

                //대기자가 2명보다 크면 명교하고, 30초 쉬기.
                if(nCandidates!=null && nCandidates.size()>2) {
                    //대기자 목록 섞기
                    Collections.shuffle(nCandidates);

                    //data에 담을 정보.
                    data.addProperty("action", "exchangeStart");
                    data.addProperty("turn", cnt);
                    params.add("data", data);

                    //2명만 명교 시작.
                    for (int i = 0; i < 2; i++) {
                        notice.sendNotification(nCandidates.get(i).getParticipantPrivateId(),
                                ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
                    }

                    //명교 대기자 목록 초기화
                    GameService.exchangeCandidates.remove(sessionId);
                    GameService.exchangeCandidates.put(sessionId, new ArrayList<Participant>());

                    //명교시간 30초
                    Thread.sleep(30000);
                    cnt++;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
