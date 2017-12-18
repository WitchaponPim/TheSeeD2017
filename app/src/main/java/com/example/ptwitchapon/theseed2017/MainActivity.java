package com.example.ptwitchapon.theseed2017;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ptwitchapon.theseed2017.API.NetworkConnectionManager;
import com.example.ptwitchapon.theseed2017.API.UserCallbackListener;
import com.example.ptwitchapon.theseed2017.Model.User;
import com.squareup.okhttp.ResponseBody;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.logging.LogManager;

import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private BluetoothAdapter mBluetoothAdapter;
    private BroadcastReceiver_BTState mBTStateUpdateReceiver;
    private Scanner_BTLE mBTLeScanner;
    private Button btn_Scan;
    private EditText user;
    private EditText pass;
    NetworkConnectionManager connect = new NetworkConnectionManager();
    UserCallbackListener userCallbackListener;
    String TAG = "hey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Scan = (Button) findViewById(R.id.btn_sign_in);
        user = (EditText) findViewById(R.id.edtEmail);
        pass = (EditText) findViewById(R.id.edtPassword);

        mBTStateUpdateReceiver = new BroadcastReceiver_BTState(getApplicationContext());

        userCallbackListener = new UserCallbackListener() {
            @Override
            public void onResponse(List<User> user, Retrofit retrofit) {
                Utils.usermodel = user;
                Log.d(TAG,Utils.usermodel.toString());
            }

            @Override
            public void onBodyError(ResponseBody responseBodyError) {
                Log.d(TAG,responseBodyError.toString());
            }

            @Override
            public void onBodyErrorIsNull() {
                Log.d(TAG, "Null null ");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, t.toString());
            }
        };


        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(getApplicationContext(), "BLE not supported", Toast.LENGTH_LONG).show();
            finish();
        }

        final BluetoothManager bluetoothManager =
                (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        findViewById(R.id.btn_sign_in).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mBTStateUpdateReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mBTStateUpdateReceiver);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                String username = user.getText().toString();
                String password = pass.getText().toString();
                if(Utils.usermodel==null) {
                    connect.callUser(userCallbackListener, username, password);
                }else{
                    Intent status = new Intent(MainActivity.this,StatusActivity.class);
                    startActivity(status);
                }

//                test();
//                Calendar.getInstance().getTime().getHours();

            break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void test(){
        Intent intent = new Intent(this,AlarmReciever.class);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(),0 ,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Date futureDate = new Date(new Date().getTime());
        futureDate.setHours(10);
        futureDate.setMinutes(10);
        futureDate.setSeconds(0);
        Log.d(TAG, String.valueOf(futureDate.getTime()));
        am.set(AlarmManager.RTC_WAKEUP,futureDate.getTime(), pi);

    }
}


