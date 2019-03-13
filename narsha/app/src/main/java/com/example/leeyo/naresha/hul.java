package com.example.leeyo.naresha;

import android.annotation.SuppressLint;
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
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class hul extends AppCompatActivity {

    @SuppressLint("CutPasteId")public static ListView hulListView;

    public static List<HulItem> hulList;

    private boolean mConnected = false;

    public static HulListViewAdapter hulListViewAdapter;

    private final static String TAG = DeviceControlActivity.class.getSimpleName();

    public final String HulService = "00001810-0000-1000-8000-00805f9b34fb";
    public final String HulDeviceS = "B4:99:4C:5A:A3:B2";

    public static int PreB = 0;
    public static int PreL = 0;
    public static int PreHul = 0;
    public static int PreHul2 = 0;
    public static boolean initialHul = false;

    public BluetoothDevice TempDevice;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private BluetoothGattCharacteristic mNotifyCharacteristic;

    private BluetoothAdapter mBluetoothAdapter;

    private boolean mScanning;

    private Handler mHandler;

    private static final int REQUEST_ENABLE_BT = 1;

    private static final long SCAN_PERIOD = 10000;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = bluetoothManager.getAdapter();
        //ble deviceScanActivity onCreate 메서드

        setContentView(R.layout.activity_hul);

        hulList = new ArrayList<>();

        hulListView = findViewById(R.id.hul_list_view);

        hulListViewAdapter = new HulListViewAdapter(this, hulList);

        hulListView.setAdapter(hulListViewAdapter);

        scanLeDevice(true);
    }

    public static void hulAddList(String MaxHul, String MinHul, String AvgHul, String Check) {

        hulList.add(new HulItem(MaxHul, MinHul, AvgHul, Check, BluetoothLeService.get_DATE()));

        hulListView.getAdapter().getItem(0);
    }

    public static void refreshList(){

        hulListViewAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void scanLeDevice(final boolean enable) {

        if (enable) {

            mHandler.postDelayed(new Runnable() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                @Override
                public void run() {

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
                    if (device.getAddress().equals("B4:99:4C:5A:A3:B2") /*혈압계 주소)*/) {

                        TempDevice = device;

                        controlAct();
                    }
                }
            });
        }
    };

    public void controlAct(){
        final BluetoothDevice device = TempDevice;

        if (device == null) return;     // 디바이스 값이 널이면 함수 종료

        controlActivity();
    }

    public void controlActivity(){

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);

        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

        mConnected = true;
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {

                Log.e(TAG, "블루투스를 초기화하지 못했습니다.");
                finish();
            }

            mBluetoothLeService.connect(HulDeviceS);
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

                try {
                    finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    };
    //정보를 받아서 사용 가능한 데이터인지, 연결이 끊겼다고 알려주는건지 판별함.
    //broadcastUpdate 함수보다 먼저 실행됨.

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onResume() {

        super.onResume();

        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

        if (mBluetoothLeService != null) {

            final boolean result = mBluetoothLeService.connect(HulDeviceS);

            Log.d(TAG, "연결요청 결과 = " + result);
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

            if (uuid.toString().equals(HulService)) {

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
}