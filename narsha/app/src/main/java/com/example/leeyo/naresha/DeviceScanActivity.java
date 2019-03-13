package com.example.leeyo.naresha;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class DeviceScanActivity extends ListActivity {

    private BluetoothAdapter mBluetoothAdapter;

    private BluetoothDevice TempDevice;

    private boolean mScanning;

    private Handler mHandler;

    private static final int REQUEST_ENABLE_BT = 1;

    private static final long SCAN_PERIOD = 10000;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mHandler = new Handler();

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = bluetoothManager.getAdapter();

    }


    @Override
    protected void onResume() {

        super.onResume();

        if (!mBluetoothAdapter.isEnabled()) {

            if (!mBluetoothAdapter.isEnabled()) {

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

            }
        }

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

    @Override
    protected void onPause() {

        super.onPause();

        scanLeDevice(false);

    }




    private void scanLeDevice(final boolean enable) {

        if (enable) {

            mHandler.postDelayed(new Runnable() {

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

        final Intent intent = new Intent(this, DeviceControlActivity.class);//인텐트로 값넘길 준비

        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());

        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
        //디바이스 이름,주소를 DeviceControlActivity에 넘김

        if (mScanning) {//스캔 중이면 스캔을 중단시키고 넘어감.

            mBluetoothAdapter.stopLeScan(mLeScanCallback);

            mScanning = false;

        }
        startActivity(intent);
        finish();
    }

}