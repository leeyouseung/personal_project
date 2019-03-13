package com.example.leeyo.naresha;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class chon extends AppCompatActivity{

    private final static String TAG = DeviceControlActivity.class.getSimpleName();

    public final String TempCharateristic = "00002a1c-0000-1000-8000-00805f9b34fb";
    public final String TempService = "00001809-0000-1000-8000-00805f9b34fb";
    public final String TempDeviceS = "0C:B2:B7:78:22:46";

    public BluetoothDevice TempDevice;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private BluetoothGattCharacteristic mNotifyCharacteristic;

    private BluetoothAdapter mBluetoothAdapter;

    private boolean mScanning;

    private Handler mHandler;

    private boolean mConnected = false;

    private static final int REQUEST_ENABLE_BT = 1;

    private static final long SCAN_PERIOD = 10000;

    public static List<ChonItem> chonList = new ArrayList<>();

    public static double prevTemp = 0.0;

    @SuppressLint({"CutPasteId", "StaticFieldLeak"})
    public static ListView chonListView;

    @SuppressLint("StaticFieldLeak")
    public static ChonListViewAdapter chonListViewAdapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mHandler = new Handler();

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        assert bluetoothManager != null;
        mBluetoothAdapter = bluetoothManager.getAdapter();
        //ble deviceScanActivity onCreate 메서드

        setContentView(R.layout.activity_chon);

        chonListView = findViewById(R.id.chon_list_view);

        chonListViewAdapter = new ChonListViewAdapter(this, chonList);

        chonListView.setAdapter(chonListViewAdapter);

        scanLeDevice(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {

            finish();

            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void scanLeDevice(final boolean enable) {

        if (enable) {

            mHandler.postDelayed(new Runnable() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                @Override
                public void run() {

                    mScanning = false;

                    mBluetoothAdapter.stopLeScan(mLeScanCallback);

                    invalidateOptionsMenu();
                }

            }, SCAN_PERIOD);

            mScanning = true;

            mBluetoothAdapter.startLeScan(mLeScanCallback);

        } else {

            mScanning = false;

            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }

        invalidateOptionsMenu();
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (device.getAddress().equals("B4:99:4C:5A:A3:B2") //혈압계 주소
                            || device.getAddress().equals("0C:B2:B7:78:22:46")//체온계 주소
                            ) {

                        TempDevice = device;

                        controlAct();
                    }
                }
            });
        }
    };

    public void controlAct(){
        final BluetoothDevice device = TempDevice;

        if (device == null) return;//디바이스 값이 널이면 함수 종료

        controlActivity();
    }

    public void controlActivity(){

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);

        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @SuppressLint("NewApi")
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {

                Log.e(TAG, "블루투스를 초기화하지 못했습니다.");
                finish();
            }

            mBluetoothLeService.connect(TempDeviceS);
            mConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onResume() {

        super.onResume();

        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

        if (mBluetoothLeService != null) {

            final boolean result = mBluetoothLeService.connect(TempDeviceS);

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

        if(mConnected == true) {
            unbindService(mServiceConnection);
        }

        mBluetoothLeService = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
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

    public static void updateListView(){

        chonListView.getAdapter().getItem(0);

        chonListViewAdapter.notifyDataSetChanged();
    }

    public static void addListView(String Temp, String Date,String Sub){
        chonList.add(new ChonItem(Temp.substring(0, 4) + "'C", Date, Sub));
    }
}
