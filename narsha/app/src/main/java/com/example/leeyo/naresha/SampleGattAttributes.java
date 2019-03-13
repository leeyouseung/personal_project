/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.leeyo.naresha;

import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;

public class SampleGattAttributes {

    private static HashMap<String, String> attributes = new HashMap();

    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";

    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";


    static {

        attributes.put("00001809-0000-1000-8000-00805f9b34fb", "Heart Rate Service");

        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");

        attributes.put("00002a00-0000-1000-8000-00805f9b34fb", "디바이스 이름");

        attributes.put("0000180a-0000-1000-8000-00805f9b34fb","디바이스 정보");

        attributes.put("0000180f-0000-1000-8000-00805f9b34fb","배터리 정보");

        attributes.put("00001805-0000-1000-8000-00805f9b34fb", "현재 시각 정보");

        attributes.put("00001800-0000-1000-8000-00805f9b34fb","디바이스명");

        attributes.put("00001809-0000-1000-8000-00805f9b34fb","체온 정보");

        attributes.put("00002a29-0000-1000-8000-00805f9b34fb","Name String");

        attributes.put("00002a24-0000-1000-8000-00805f9b34fb","모델넘버");

        attributes.put("00002a25-0000-1000-8000-00805f9b34fb","시리얼 넘버");

        attributes.put("00002a27-0000-1000-8000-00805f9b34fb","Hardware Revision String");

        attributes.put("00002a26-0000-1000-8000-00805f9b34fb","Firmware Revision String");

        attributes.put("00002a28-0000-1000-8000-00805f9b34fb","Software Revision String");

        attributes.put("00002a23-0000-1000-8000-00805f9b34fb","시스템 ID");

        attributes.put("00002a19-0000-1000-8000-00805f9b34fb","배터리 잔량");

        attributes.put("00002a1c-0000-1000-8000-00805f9b34fb","체온");

        attributes.put("00002a2b-0000-1000-8000-00805f9b34fb","현재시각");

        attributes.put("00002a35-0000-1000-8000-00805f9b34fb","혈압 정보");

    }

    public static String lookup(String uuid, String defaultName) {

        String name = attributes.get(uuid);

        return name == null ? defaultName : name;

    }
}
