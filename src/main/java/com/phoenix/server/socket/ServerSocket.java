package com.phoenix.server.socket;

import com.google.gson.Gson;
import com.phoenix.server.dto.Message;
import com.phoenix.server.util.Client;
import com.phoenix.server.util.ClientMapping;
import com.phoenix.server.util.Type;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

@ServerEndpoint("/remote")
public class ServerSocket {
    @OnOpen
    public void onOpen(Session userSession) {

        UUID uniqueID = UUID.randomUUID();
        String id = uniqueID.toString().replaceAll("[^0-9]", "").substring(0, 6);
        Client.addAClient(id, userSession);

        Message m = new Message();
        m.setMessage(id);
        m.setType(Type.ID);

        try {
            userSession.getBasicRemote().sendText(new Gson().toJson(m));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @OnClose
    public void onClose(Session userSession, jakarta.websocket.CloseReason reason) {
        Client.removeClient(userSession);
    }

//    @OnMessage
//    public void onMessage(String message) {
//
//        Message m = new Gson().fromJson(message, Message.class);
//        if (!Objects.equals(m.getFrom(), m.getTo())) {
//            switch (m.getType()) {
//                case CONNECTION:
//                    ClientMapping.setMappings(m.getFrom(), m.getTo());
//                    Session host = Client.getSession(m.getTo());
//
//                    Message m2 = new Message();
//                    m2.setType(Type.TO_HOST);
//                    m2.setFrom(m.getFrom());
//                    try {
//                        host.getBasicRemote().sendText(new Gson().toJson(m2));
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    break;
//
//            }
//        }
//
//
//    }
    @OnMessage
    public void onMessage(Message m) {

        System.out.println(m);


    }

    @OnMessage
    public void onMessage(byte[] img, Session ses) {

        String host = Client.getClient(ses);
        String client = ClientMapping.findPartner(host);

        Session cli = Client.getSession(client);

        try {
            cli.getBasicRemote().sendBinary(ByteBuffer.wrap(img));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
