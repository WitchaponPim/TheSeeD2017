package com.example.ptwitchapon.theseed2017;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class AlarmReciever extends BroadcastReceiver {

    public AlarmReciever(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,StatusActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        Log.d("Hey", "Let's rock");
    }
}
