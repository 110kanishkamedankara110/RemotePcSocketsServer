package com.phoenix.server.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClientMapping {
    private static Map<String, String> mappings = new HashMap<>();

    public static Map<String, String> getMappings() {
        return mappings;
    }

    public static void setMappings(String client, String host) {
        mappings.put(client, host);
    }


    public static void remove(String client) {
        Iterator<Map.Entry<String, String>> iterator = mappings.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (entry.getKey().equals(client) || entry.getValue().equals(client)) {
                iterator.remove();
            }
        }
    }

    public static String findPartner(String host) {
        Iterator<Map.Entry<String, String>> iterator = mappings.entrySet().iterator();
        String client = null;
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (entry.getKey().equals(host)) {
                client = entry.getValue();
            } else if (entry.getValue().equals(host)) {
                client = entry.getKey();
            }
        }
        return client;
    }

}
