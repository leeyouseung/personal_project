package com.example.leeyo.naresha;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BluetoothLeService extends Service {

    private final static String TAG = BluetoothLeService.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final String TempCharateristic = "00002a1c-0000-1000-8000-00805f9b34fb";

    public final static String ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA = "com.example.bluetooth.le.EXTRA_DATA";

    public static String MaxHul;
    public static String MinHul;
    public static String AvgHul;
    public static String CurCheck;

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {//연결 시도

            String intentAction;

            if (newState == BluetoothProfile.STATE_CONNECTED) {//메세지 수신받음

                intentAction = ACTION_GATT_CONNECTED;

                mConnectionState = STATE_CONNECTED;

                broadcastUpdate(intentAction);//127~178줄에 함수가 있음

                Log.i(TAG, "GATT서버에 연결되었습니다.");//디바이스의 gatt서버에 연결되었음

                Log.i(TAG, "Attempting to start service discovery:" + mBluetoothGatt.discoverServices());

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {

                intentAction = ACTION_GATT_DISCONNECTED;

                mConnectionState = STATE_DISCONNECTED;

                Log.i(TAG, "GATT서버와 연결이 끊겼습니다.");

                broadcastUpdate(intentAction);

            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {

            if (status == BluetoothGatt.GATT_SUCCESS) {

                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);

            } else {

                Log.w(TAG, "onServicesDiscovered received: " + status);

            }
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,BluetoothGattCharacteristic characteristic,int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {

                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);

            }
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,BluetoothGattCharacteristic characteristic) {//데이터 수신받음

            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);

        }

    };

    private void broadcastUpdate(final String action) {

        final Intent intent = new Intent(action);
        sendBroadcast(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic) {

        final byte[] data = characteristic.getValue();

        if (data != null && data.length > 0) {

            final StringBuilder stringBuilder = new StringBuilder(data.length);

            for (byte byteChar : data)
                stringBuilder.append(String.format("%02X", byteChar) + " ");

            if (characteristic.getUuid().toString().equals("00002a1c-0000-1000-8000-00805f9b34fb")){//체온 정보

                Collections.reverse(chon.chonList);

                chonDataReturn(data);

            }
            else if(characteristic.getUuid().toString().equals("00002a35-0000-1000-8000-00805f9b34fb")){//혈압 정보

                Collections.reverse(hul.hulList);

                if(isPulseError(data)){
                    return;
                }

                dataToPulse(data);

                hul.hulAddList(MaxHul,MinHul, AvgHul, CurCheck);

                Collections.reverse(hul.hulList);

                hul.refreshList();

            }

        }

    }

    public class LocalBinder extends Binder {

        BluetoothLeService getService() {
            return BluetoothLeService.this;
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public boolean onUnbind(Intent intent) {

        close();
        return super.onUnbind(intent);

    }

    private final IBinder mBinder = new LocalBinder();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public boolean initialize() {

        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "블루투스 매니저를 초기화하지 못했습니다.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            Log.e(TAG, "블루투스 매니저를 가져오지 못했습니다.");
            return false;
        }

        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public boolean connect(final String address) {

        if (mBluetoothAdapter == null || address == null) {

            Log.w(TAG, "주소가 지정되지 않았거나 블루투스어댑터가 초기화되지 않았습니다.");
            return false;

        }

        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress) && mBluetoothGatt != null) {

            Log.d(TAG, "기존 블루투스 GATT로 연결을 시도합니다.");

            if (mBluetoothGatt.connect()) {

                mConnectionState = STATE_CONNECTING;
                return true;

            } else {

                return false;

            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

        if (device == null) {

            Log.w(TAG, "기기를 찾을수 없어 연결하지 못했습니다.");
            return false;

        }

        mBluetoothGatt = device.connectGatt(this, true, mGattCallback);
        Log.d(TAG, "새 연결을 만듭니다.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "블루투스 어댑터가 초기화되지 않았습니다.");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {

        if (mBluetoothAdapter == null || mBluetoothGatt == null) {

            Log.w(TAG, "블루투스 어댑터가 초기화되지 않았습니다.");
            return;

        }
        if(Trash.lookup(characteristic.getUuid().toString())) {
            BluetoothGattDescriptor bluetoothGattDescriptor = characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
            mBluetoothGatt.writeDescriptor(bluetoothGattDescriptor);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {

        if (mBluetoothAdapter == null || mBluetoothGatt == null) {

            Log.w(TAG, "블루투스 어댑터가 초기화되지 않았습니다.");
            return;

        }

        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public List<BluetoothGattService> getSupportedGattServices() {

        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();

    }

    public byte[] get_DATE_int() {
        long cur = System.currentTimeMillis();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
        String datetime = date.format(new Date(cur));

        byte[] rdate = new byte[datetime.length()/2];

        int k=0;
        for(int i=0;i<rdate.length;i++) {
            rdate[i] = (byte)Integer.parseInt(datetime.substring(k,k+2));
            k+=2;
        }
        byte temp;
        temp = rdate[0];
        rdate[0] = rdate[1];
        rdate[1] = temp;

        return rdate;
    }

    public static boolean isPulseError(byte data[]) {
        int pulse1 = data[1] & 0xff;
        int pulse2 = data[3] & 0xff;
        int pulse3 = data[5] & 0xff;
        int pulse4 = data[7] & 0xff;

        if(pulse1 == 255 || pulse2 == 255|| pulse3 == 255|| pulse4 == 255) {
            return true;
        }
        return false;
    }

    public static void dataToPulse(byte data[]) {
        int pulse1 = data[1] & 0xff;
        int pulse2 = data[3] & 0xff;
        int pulse3 = data[5] & 0xff;

        MaxHul = Integer.toString(pulse1) + "mmHg ";
        MinHul = Integer.toString(pulse2) + "mmHg ";
        AvgHul = Integer.toString(pulse3) + "mmHg ";

        if(hul.initialHul == false) {
            CurCheck = "0/0";
            hul.initialHul = true;
            hul.PreB = pulse1;
            hul.PreL = pulse2;
            hul.PreHul = pulse1;
            hul.PreHul2 = pulse2;

        } else {
            int PreB = pulse1 - hul.PreHul;
            int PreL = pulse2 - hul.PreHul2;
            hul.PreHul = pulse1;
            hul.PreHul2 = pulse2;

            CurCheck = Integer.toString(PreB) + "/" + Integer.toString(PreL);

            hul.PreB = PreB;
            hul.PreL = PreL;
        }
    }

    public String dataToTemp(byte data[]){
        if(data == null){
            return "안돼애애애";
        }
        double Temp = Trash.dataTodouble(data);

        String Year = Trash.dataToyear(data);
        String R;
        R = String.valueOf(Temp);
        R+= "\n" + Year;
        return R;
    }

    public static String get_DATE(){
        long cur = System.currentTimeMillis();
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
        String datetime = date.format(new Date(cur));
        return datetime;
    }

    public void chonDataReturn(byte[] data) {

        String Date = get_DATE();


        String Temp = dataToTemp(data);

        double Temperature = Double.parseDouble(Temp.substring(0, 4));

        if (chon.prevTemp == 0.0) {

            chon.addListView(Temp.substring(0, 4) + "'C", Date, "0.0");

        } else {

            double curTemp = Temperature - chon.prevTemp;

            String curTempS = String.valueOf(curTemp).substring(0, 3);

            if (curTemp > 0) {
                curTempS = "+" + curTempS;
            } else if (curTemp < 0) {
                curTempS = curTempS + String.valueOf(curTemp).substring(3, 4);
            }

            chon.addListView(Temp.substring(0, 4) + "'C", Date, curTempS);

            Collections.reverse(chon.chonList);

        }
        chon.prevTemp = Temperature;

        chon.updateListView();
    }
}