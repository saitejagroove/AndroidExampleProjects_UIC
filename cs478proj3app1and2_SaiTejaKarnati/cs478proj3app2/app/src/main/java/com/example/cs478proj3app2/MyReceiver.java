package com.example.cs478proj3app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        int temp = intent.getIntExtra("item", 0);
        if(temp ==0) { //restaurants button2
            Intent i = new Intent(context,Main2Activity.class);
            context.startActivity(i);

        }else{ //attractions button1
            Intent i = new Intent(context,MainActivity.class);
            context.startActivity(i);
        }

    }
}
