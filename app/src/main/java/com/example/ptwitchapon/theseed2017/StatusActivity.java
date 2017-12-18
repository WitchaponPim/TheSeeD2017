package com.example.ptwitchapon.theseed2017;

import android.bluetooth.BluetoothAdapter;

import android.content.IntentFilter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;


import com.example.ptwitchapon.theseed2017.Model.User;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener {
    private String path ="http://perfectv.hol.es/";
    public String TAG = "StatusAct";
    private String test;
    LinearLayout st1, st2;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private BluetoothAdapter mBluetoothAdapter;
    private HashMap<String, BTLE_Device> mBTDevicesHashMap;
    private ArrayList<BTLE_Device> mBTDevicesArrayList;
    private BroadcastReceiver_BTState mBTStateUpdateReceiver;
    public static final int REQUEST_ENABLE_BT = 1;
    public static final int BTLE_SERVICES = 2;
    private Scanner_BTLE mBTLeScanner;
    private TextView name,group,major,emId,textin, textin_time, textout, textout_time;
    private ImageView profile;

    User usermodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        mBTStateUpdateReceiver = new BroadcastReceiver_BTState(getApplicationContext());

        mBTLeScanner = new Scanner_BTLE(this, 5000, -95);
        mBTLeScanner.openBluetooth();
//        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
//            Toast.makeText(getApplicationContext(), "BLE not supported", Toast.LENGTH_LONG).show();
//            finish();
//        } else {
//            Toast.makeText(getApplicationContext(), "Bluetooth", Toast.LENGTH_LONG).show();
//        }

        if(!Utils.foundBeacon) {
            startScan();
        }
        profile = (ImageView) findViewById(R.id.propic);
        name = (TextView) findViewById(R.id.txtName);
        group = (TextView) findViewById(R.id.txtGroup);
        major = (TextView) findViewById(R.id.txtMajor);
        emId = (TextView) findViewById(R.id.em_id);
        textin = (TextView) findViewById(R.id.statusin);
        textout = (TextView) findViewById(R.id.statusout);
        textout_time = (TextView) findViewById(R.id.statusoutTime);
        textin_time = (TextView) findViewById(R.id.statusinTime);
        st1 = (LinearLayout) findViewById(R.id.sta1);
        st2 = (LinearLayout) findViewById(R.id.sta2);

        Findtheway(Utils.time);

        findViewById(R.id.statusin).setOnClickListener(this);
        findViewById(R.id.statusout).setOnClickListener(this);

        name.setText(Utils.usermodel.get(0).getName()+" "+Utils.usermodel.get(0).getLastname());
        group.setText(Utils.usermodel.get(0).getGroup());
        major.setText(Utils.usermodel.get(0).getMajor());
        emId.setText(Utils.usermodel.get(0).getId_user());
        Log.d(TAG, path+Utils.usermodel.get(0).getPicpath());
        Picasso.with(getApplicationContext()).load(path+Utils.usermodel.get(0).getPicpath()).into(profile);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mBTStateUpdateReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.usermodel.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mBTStateUpdateReceiver);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Findtheway(Utils.time);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.statusin:


                break;
            case R.id.statusout:


                break;
            default:
                break;
        }
    }
    public boolean comparedate(String d1,String d2){

        try {
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);
            Log.d(TAG,"Date1 : "+sdf.format(date1));
            Log.d(TAG, "Date2 : "+sdf.format(date2));

            if(date1.equals(date2)){
                Log.d(TAG,"return");
                return true;
            }
        } catch (ParseException e) {
            Log.d(TAG, e.toString());

        }
        return false;
    }
//    public void addDevice(BluetoothDevice device/*, int rssi,LinearLayout st,TextView txt,TextView txtt*/) {
//        switch (device.getAddress()) {
////            case "D4:36:39:DE:56:B8":
//            case "F8:04:2E:AD:FF:83":
////                    Log.d("test", device.getAddress() + " I found u.)");
////                    txt.setText("Success : ");
////                    txt.setOnClickListener(null);
////                    st.setBackground(getResources().getDrawable(R.drawable.checked));
////                    txtt.setText(format.format(Calendar.getInstance().getTime()).toString());
//                test = device.toString();
//                Log.d(TAG, "addDevice: see ya!!!"+test);
//                Utils.foundBeacon = true;
//                stopScan();
//                break;
//            default:
////                    st.setBackground(getResources().getDrawable(R.drawable.check_fail));
////                    txt.setText("Scan Again ");
//                    stopScan();
//                    if(!Utils.foundBeacon){
//                        Log.d(TAG, "addDevice: re scan");
//                        startScan();
//                    }
//                break;
//        }
//    }



    public void Findtheway(String time) {
        if (Utils.foundBeacon){
            textin.setText("Success : ");
            textin_time.setText(time);
//            textin_time.setText("08:29:57");
            st1.setBackground(getResources().getDrawable(R.drawable.checked));
        }
    }

    public void startScan() {

            mBTLeScanner.start();


    }

//    public void startScan(LinearLayout st, TextView txt, TextView txtt) {
//        txt.setText("Scanning...");
//        mBTLeScanner.start(st, txt, txtt);
//    }

    public void stopScan() {
        mBTLeScanner.stop();
    }
}
