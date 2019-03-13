package com.example.leeyo.naresha;

import java.util.HashMap;

public class Trash {

    //23,24,25,26,27,28,29,2a 디바이스 정보
    private static HashMap<String, String> attributes = new HashMap();

    static {

        attributes.put("00002a1c-0000-1000-8000-00805f9b34fb", "1");
        attributes.put("00002a35-0000-1000-8000-00805f9b34fb", "1");
    }

    public static boolean lookup(String uuid) {

        if (attributes.containsKey(uuid)) {
            return true;
        } else {
            return false;
        }

    }


    public static double dataTodouble(byte[] data) {
        int a = (data[3] << 16) + (data[2] << 8) + (data[1] & 0xff);

        double rdata = (double) a;

        rdata *= 0.1;
        return rdata;
    }

    public static String dataToyear(byte[] data) {

        int a = (data[6] << 8) + data[5];
        int mon, day, hour, min, sec;
        mon = data[7];
        day = data[8];
        hour = data[9];
        min = data[10];
        sec = data[11];

        StringBuilder str = new StringBuilder();

        str.append(String.format("%d", (data[6] << 8) + data[5] + 0x0100) + "년 ");
        str.append(String.format("%d", mon) + "월 ");
        str.append(String.format("%d", day) + "일 ");
        str.append(String.format("%d", hour) + "시 ");
        str.append(String.format("%d", min) + "분 ");
        str.append(String.format("%d", sec) + "초");

        return str.toString();
    }


}

