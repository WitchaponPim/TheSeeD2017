package com.example.ptwitchapon.theseed2017;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Kelvin on 4/20/16.
 */
public class Scanner_BTLE {

    private StatusActivity ma;

    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;
    private LinearLayout st;
    private TextView txt, txtt;
    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    private long scanPeriod;
    private int signalStrength;

    public Scanner_BTLE(StatusActivity statusActivity, long scanPeriod, int signalStrength) {
        ma = statusActivity;

        mHandler = new Handler();

        this.scanPeriod = scanPeriod;
        this.signalStrength = signalStrength;

        final BluetoothManager bluetoothManager =
                (BluetoothManager) ma.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();


    }

    public boolean isScanning() {
        return mScanning;
    }

    public void openBluetooth() {
        mBluetoothAdapter.enable();
    }

    public void start() {
        if (!Utils.checkBluetooth(mBluetoothAdapter)) {
//            Utils.requestUserBluetooth(ma);

            ma.stopScan();
        } else {
            scanLeDevice(true);
        }
    }

//    public void start(LinearLayout st, TextView txt, TextView txtt) {
//        this.st = st;
//        this.txt = txt;
//        this.txtt = txtt;
//        if (!Utils.checkBluetooth(mBluetoothAdapter)) {
//            Utils.requestUserBluetooth(ma);
//            ma.stopScan();
//        } else {
//            scanLeDevice(true);
//        }
//    }

    public void stop() {
        scanLeDevice(false);
    }

    // If you want to scan for only specific types of peripherals,
    // you can instead call startLeScan(UUID[], BluetoothAdapter.LeScanCallback),
    // providing an array of UUID objects that specify the GATT services your app supports.

    private PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, StatusActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(StatusActivity.class);
        stackBuilder.addNextIntent(intent);
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public void notification(String time) {
        Notification notification =
                new NotificationCompat.Builder(ma)
                        .setSmallIcon(R.mipmap.ic_check_pass)
                        .setContentTitle("บันทึกเวลาเรียบร้อย")
                        .setContentText("เวลา : " + time)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(getPendingIntent(ma))
                        .setAutoCancel(false)
                        .setVibrate(new long[]{300, 500, 300, 500, 300, 500, 300, 500})
                        .build();
        NotificationManager notificationManager =
                (NotificationManager) ma.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    private void scanLeDevice(final boolean enable) {
        if (enable && !mScanning) {
            // Stops scanning after a pre-defined scan period.
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
////                    Utils.toast(ma.getApplicationContext(), "Stopping BLE scan...");
////
////                    mScanning = false;
////                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
////
////                    ma.stopScan();
//                }
//            }, scanPeriod);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
//            mBluetoothAdapter.startLeScan(uuids, mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    final int new_rssi = rssi;
                    Utils.time = format.format(Calendar.getInstance().getTime()).toString();
//                    Utils.time = "08:29:57";
//                    if (rssi > signalStrength) {
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
////                                ma.addDevice(device, new_rssi,st,txt,txtt);
//                                Log.d("ค๊วย", "run: "+device);
//                                ma.addDevice(device);
//
//                            }
//                        });
//                    }
                    switch (device.getAddress()) {
                        case "D4:36:39:DE:56:B8":
                            Log.d("ค๊วย", "onLeScan: See ya!!!" + device.getAddress());
                            Utils.foundBeacon = true;
                            notification(Utils.time);
                            ma.Findtheway(Utils.time);
                            stop();
                            break;
                        case "HMSoft":
                            Log.d("ค๊วย", "onLeScan: See ya!!!" + device.getAddress());
                            Utils.foundBeacon = true;
                            notification(Utils.time);
                            ma.Findtheway(Utils.time);
                            stop();
                            break;
                        default:
                            stop();
                            if (!Utils.foundBeacon) {
                                Log.d("ค๊วย", "onLeScan: Not flower for u!" + device.getAddress());
                                Log.d("ค๊วย", "onLeScan: re scan");
                                start();
                            }
                            break;
                    }
                }
            };


}