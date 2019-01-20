package com.williampembleton.milestone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ME TESTING", "At AlarmReceiver");
        Intent i = new Intent(context, AllTasks.class);
        context.startService(i);
    }
}
