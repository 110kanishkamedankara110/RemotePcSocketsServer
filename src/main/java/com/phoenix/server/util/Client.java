package com.phoenix.server.util;

import jakarta.websocket.Session;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Client {
    private static Map<String, Session> clientList = new HashMap<>();

    public static Map<String, Session> getClientList() {
        return clientList;
    }

    public static Session getSession(String clientId) {
        return clientList.get(clientId);
    }

    public static void addAClient(String clientId, Session session) {
        Client.clientList.put(clientId, session);
    }

    public static void removeClient(String id) {
        if (clientList.get(id) != null) {
            clientList.remove(id);
        }
    }

    public static void removeClient(Session session) {
        Iterator<Map.Entry<String, Session>> iterator = clientList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Session> entry = iterator.next();
            if (entry.getValue().equals(session)) {
                iterator.remove();
                ClientMapping.remove(entry.getKey());
                break; // Break out of the loop since the entry is removed
            }
        }
    }

    public static String getClient(Session ses) {
        String client = null;
        Iterator<Map.Entry<String, Session>> iterator = clientList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Session> entry = iterator.next();
            if (entry.getValue().equals(ses)) {
                client = entry.getKey();
                break; // Break out of the loop since the entry is removed
            }
        }
        return client;
    }

}


