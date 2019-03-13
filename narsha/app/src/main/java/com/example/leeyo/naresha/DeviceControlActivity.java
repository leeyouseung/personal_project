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

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DeviceControlActivity extends Activity {
    private final static String TAG = DeviceControlActivity.class.getSimpleName();

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    public final String TempCharateristic = "00002a1c-0000-1000-8000-00805f9b34fb";
    public final String TempService = "00001809-0000-1000-8000-00805f9b34fb";
    public final String TempDevice = "0C:B2:B7:78:22:46";

    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private BluetoothGattCharacteristic mNotifyCharacteristic;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {

                Log.e(TAG, "블루투스를 초기화하지 못했습니다.");
                finish();

            }

            mBluetoothLeService.connect(TempDevice);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();

            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {

                displayGattServices(mBluetoothLeService.getSupportedGattServices());

                invalidateOptionsMenu();

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {

                invalidateOptionsMenu();


            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {

                displayGattServices(mBluetoothLeService.getSupportedGattServices());

            }
        }
    };
    //정보를 받아서 사용 가능한 데이터인지, 연결이 끊겼다고 알려주는건지 판별함.
    //broadcastUpdate함수보다 먼저 실행됨.


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);

        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }


    @Override
    protected void onResume() {

        super.onResume();

        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

        if (mBluetoothLeService != null) {

            final boolean result = mBluetoothLeService.connect(TempDevice);

            Log.d(TAG, "연결요청 결과=" + result);

        }
    }
    //그냥 연결

    @Override
    protected void onPause() {

        super.onPause();

        unregisterReceiver(mGattUpdateReceiver);

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

        unbindService(mServiceConnection);

        mBluetoothLeService = null;

    }

    private void displayGattServices(List<BluetoothGattService> gattServices) {

        if (gattServices == null) return;

        UUID uuid = null;

        for (BluetoothGattService gattService : gattServices) {//gatt 서비스를 하나하나 표시해줌

            uuid = gattService.getUuid();

            if (uuid.toString().equals(TempService)) {

                for (BluetoothGattCharacteristic gattCharacteristic : gattService.getCharacteristics()) {//이름 설정부분

                    uuid = gattCharacteristic.getUuid();

                    if(Trash.lookup(uuid.toString())){

                        ReadChar(gattCharacteristic);

                    }

                }
            }

        }

    }


    private static IntentFilter makeGattUpdateIntentFilter() {

        final IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);

        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);

        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);

        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);

        return intentFilter;
    }

    public boolean ReadChar(BluetoothGattCharacteristic characteristic) {
        if (characteristic != null) {

            final int charaProp = characteristic.getProperties();

            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {

                if (mNotifyCharacteristic != null) {

                    mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);

                    mNotifyCharacteristic = null;

                }

                mBluetoothLeService.readCharacteristic(characteristic);

            }

            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {

                mNotifyCharacteristic = characteristic;

                mBluetoothLeService.setCharacteristicNotification(characteristic, true);

            }

            return true;

        }

        return false;

    }

}



