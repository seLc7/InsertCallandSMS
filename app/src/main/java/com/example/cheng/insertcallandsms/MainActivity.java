package com.example.cheng.insertcallandsms;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int callNum, callDuration, callType;
    List<String[]> callLogList = new ArrayList<>();

    public static Uri mSmsUri = Uri.parse("content://sms");
    private String read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addCallLog = (Button) findViewById(R.id.add_call_log);
        final EditText callNumEdit = (EditText) findViewById(R.id.call_num_edit);
        final EditText callDurationEdit = (EditText) findViewById(R.id.call_duration_edit);
        final EditText callTypeEdit = (EditText) findViewById(R.id.call_type_edit);


        String defaultSmsApp = Telephony.Sms.getDefaultSmsPackage(MainActivity.this);
        Log.i("context", defaultSmsApp);
        Log.i("packagenem",MainActivity.this.getPackageName());
        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
        startActivity(intent);
        /*addCallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String callNumStr = callNumEdit.getText().toString();
                callNum = Integer.parseInt(callNumStr);
                String callDurationStr = callDurationEdit.getText().toString();
                callDuration = Integer.parseInt(callDurationStr);
                String callTypStr = callTypeEdit.getText().toString();
                callType = Integer.parseInt(callTypStr);

                insertCallLog(callNum, callDuration, callType, true, 0);
            }
        });*/
//        insertCallLog(12312131, 20, 1, true, 0);
        List<String[]> callList = getCallLogList();
        for (int i = 0; i < callList.size(); i++) {
            insertCallLog(Long.parseLong(callList.get(i)[0]),
                    Integer.parseInt(callList.get(i)[1]), Integer.parseInt(callList.get(i)[2]), true, 0);
        }

//        insertSMS();

        addCallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertSMS();
            }
        });
    }

    private void insertCallLog(long num, int duration, int type, boolean get, long i) {
        // TODO Auto-generated method stub
        ContentValues values = new ContentValues();
        values.put(CallLog.Calls.NUMBER, num);
        values.put(CallLog.Calls.DATE, System.currentTimeMillis() + i);
        values.put(CallLog.Calls.DURATION, duration);
        values.put(CallLog.Calls.TYPE, type);//未接
        values.put(CallLog.Calls.NEW, get);//0已看1未看

        getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
    }

    private void insertSMS() {
        // TODO Auto-generated method stub
        ContentValues values = new ContentValues();
        values.put(Telephony.Sms.THREAD_ID, "3");
        values.put(Telephony.Sms.ADDRESS, "13898878776");
        values.put(Telephony.Sms.BODY, "您好!");
        values.put(Telephony.Sms.DATE, System.currentTimeMillis());
        values.put(read, 0);
        values.put(Telephony.Sms.TYPE, 1);
        values.put(Telephony.Sms.SERVICE_CENTER, "+8613010776500");

        getContentResolver().insert(Telephony.Sms.CONTENT_URI, values);

        Log.i("tag", "insert SMS");
    }

    public List<String[]> getCallLogList() {
        callLogList.add(new String[]{"123123", "20", "1"});
        callLogList.add(new String[]{"212523225", "20", "1"});
        callLogList.add(new String[]{"123123", "22", "1"});
        callLogList.add(new String[]{"498983048", "25", "3"});
        callLogList.add(new String[]{"1582934208", "33", "2"});
        callLogList.add(new String[]{"15238293842", "34", "2"});

        return callLogList;
    }
}
