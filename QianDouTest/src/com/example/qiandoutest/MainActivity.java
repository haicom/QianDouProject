package com.example.qiandoutest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.android.qiandoulibrary.CpInfo;
import com.android.qiandoulibrary.MainLibrary;
import com.android.qiandoulibrary.UnicomConsume;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements  OnClickListener {
    private static String TAG = "MainActivity";
    private Button mButton;    
    private static final  String   SENT_SMS_ACTION = "SENT_SMS_ACTION";
    private Context mContext;
    private Intent    mSentIntent;
    private SmsManager mSmsManager;

    private PendingIntent mSentPI;
    private static final String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
    private Intent mDeliverIntent = new Intent(DELIVERED_SMS_ACTION);
    private PendingIntent mDeliverPI;
    //SendService myServer; //此为我应用中的服务，你可以取消
    private String   mQid = "0";  //短信序列号ID，该参数为我的应用使用的参数，你可以不要
    private MainLibrary mLibrary;
   
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {      
        
//        mSentIntent = new Intent(SENT_SMS_ACTION);
//        mSmsManager = SmsManager.getDefault();
        mContext = this;
//        mSentPI = PendingIntent.getBroadcast(mContext, 0, mSentIntent, 0);
//        
//        //短信发送状态监控
//        mContext.registerReceiver(new BroadcastReceiver(){
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                switch(getResultCode()){
//                    case Activity.RESULT_OK:
////                        MyLog.i(TAG,"信息已发出");
//                        updateStatus(mQid,"1");
//                        break;
//                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                        Toast.makeText(context, "未指定失败 \n 信息未发出，请重试",  Toast.LENGTH_LONG).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_RADIO_OFF:
//                        Toast.makeText(context, "无线连接关闭 \n 信息未发出，请重试",  Toast.LENGTH_LONG).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NULL_PDU:
//                        Toast.makeText(context, "PDU失败 \n 信息未发出，请重试",  Toast.LENGTH_LONG).show();
//                        break;
//                }
//
//            }
//        }, new IntentFilter(SENT_SMS_ACTION) );
//
//        //短信是否被接收状态监控
//        mDeliverPI = PendingIntent.getBroadcast(mContext, 0, mDeliverIntent, 0);
//        mContext.registerReceiver(new BroadcastReceiver(){
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                switch (getResultCode()) {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(getBaseContext(), "SMS delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        Toast.makeText(getBaseContext(), "SMS not delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//               // myDialog.setMessage("已送达服务终端");
//                //checkService();
//            }
//        }, new IntentFilter(DELIVERED_SMS_ACTION) );
//        
        String[] fileNames;        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mButton = (Button) this.findViewById(R.id.button);
        mButton.setOnClickListener(this);
        
        try {
            fileNames = this.getResources().getAssets().list("");
            for (String assname: fileNames) {
                Log.d(TAG, "findFileName assname = " + assname );
             
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "findFileName e.toString() = " + e.toString() );
        }
        
         mLibrary = new MainLibrary(this);        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("UnlocalizedSms")
    @Override
    public  void onClick (View v) {
        if  ( v == mButton )  {  
            Log.d(TAG, "MainActivity onClick ");            
//            send("0", "18951088071", null, "Test");
            mLibrary.sendMessageText();
            CpInfo cpInfo = mLibrary.getCpInfo();
            Log.d(TAG, " cpInfo.getCpId() = " + cpInfo.getCpId() );
            Log.d(TAG, "  cpInfo.getCpName()  = " + cpInfo.getCpName()  );
            Log.d(TAG, "  cpInfo.getCpCode()   = " + cpInfo.getCpCode()  );
            Log.d(TAG, "  cpInfo.getCpEmail()  = " + cpInfo.getCpEmail()  );
            Log.d(TAG, "   cpInfo.getAppId()    = " + cpInfo.getAppId()  );
            Log.d(TAG, "   cpInfo.getAppName()    = " + cpInfo.getAppName()  );
            Log.d(TAG, "  cpInfo.getTelephone()    = " + cpInfo.getTelephone()  );
            Log.d(TAG, "  cpInfo.getType()    = " + cpInfo.getType()  );
            
            HashMap<Integer, UnicomConsume>  unicomConsumes  = cpInfo.getUnicomConsumes();
            
            Log.d( TAG, "  unicomConsumes.size()    = " +  unicomConsumes.size() );
            Log.d( TAG, " unicomConsumes = " + unicomConsumes );
            
            
            Iterator iter = unicomConsumes.entrySet().iterator();
            while ( iter.hasNext() ) {
                  Map.Entry entry = (Map.Entry) iter.next();
                  int  key = (Integer) entry.getKey();
                  UnicomConsume unicomConsume = (UnicomConsume) entry.getValue();
                
                  Log.d(TAG, " key = " + key );
                Log.d(TAG, "unicomConsume.getPayMoney() = " + unicomConsume.getPayMoney() );
                Log.d(TAG, "   unicomConsume.getVacMode()  = " + unicomConsume.getVacMode()  );
                Log.d(TAG, "  unicomConsume.getPropName()  = " + unicomConsume.getPropName()  );
                Log.d(TAG, "  unicomConsume.getCustomCode()  = " + unicomConsume.getCustomCode()  );
                Log.d(TAG, "  unicomConsume.getVacCode()    = " + unicomConsume.getVacCode()  );
                Log.d(TAG, "   unicomConsume.getCtIndex()    = " + unicomConsume.getCtIndex()  );
                Log.d(TAG, "  unicomConsume.getCmIndex()     = " + unicomConsume.getCmIndex()  );
            }
            
            UnicomConsume unicomConsume = unicomConsumes.get(1);
            Log.d(TAG, " unicomConsume = " + unicomConsume.toString() );
            
            Log.d(TAG, "unicomConsume.getPayMoney() = " + unicomConsume.getPayMoney() );
            Log.d(TAG, "   unicomConsume.getVacMode()  = " + unicomConsume.getVacMode()  );
            Log.d(TAG, "  unicomConsume.getPropName()  = " + unicomConsume.getPropName()  );
            Log.d(TAG, "  unicomConsume.getCustomCode()  = " + unicomConsume.getCustomCode()  );
            Log.d(TAG, "  unicomConsume.getVacCode()    = " + unicomConsume.getVacCode()  );
            Log.d(TAG, "   unicomConsume.getCtIndex()    = " + unicomConsume.getCtIndex()  );
            Log.d(TAG, "  unicomConsume.getCmIndex()     = " + unicomConsume.getCmIndex()  );
//            for ( int index = 0; index <= unicomConsumes.size(); index++ ) {
//                UnicomConsume unicomConsume = unicomConsumes.get(index);
//                Log.d(TAG, "unicomConsume.getPayMoney() = " + unicomConsume.getPayMoney() );
//                Log.d(TAG, "   unicomConsume.getVacMode()  = " + unicomConsume.getVacMode()  );
//                Log.d(TAG, "  unicomConsume.getPropName()  = " + unicomConsume.getPropName()  );
//                Log.d(TAG, "  unicomConsume.getCustomCode()  = " + unicomConsume.getCustomCode()  );
//                Log.d(TAG, "  unicomConsume.getVacCode()    = " + unicomConsume.getVacCode()  );
//                Log.d(TAG, "   unicomConsume.getCtIndex()    = " + unicomConsume.getCtIndex()  );
//                Log.d(TAG, "  unicomConsume.getCmIndex()     = " + unicomConsume.getCmIndex()  );
//                Log.d(TAG, "  unicomConsume.Number()     = " + index  );
//            }
        }
        
    }
    
//    private void sendSMS(String phoneNumber, String message) {
//        if (MobNumber != null) {
//
//            for (int i = 0; i < MobNumber.size(); i++) {
//                String message = MessageText.getText().toString();
//                String tempMobileNumber = MobNumber.get(i).toString();
//                sendSMS(tempMobileNumber, message);
//            }
//            
//
//
//        SmsManager sms = SmsManager.getDefault();
//        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
//    }
        
//        /**
//         * 发送短信，这里是我需要的几个参数，你可以根据你的具体情况来使用不同的参数
//         * @param qid
//         * @param mobile 要发送的目标手机号，这个必须要有
//         * @param code
//         * @param msg 发送的短信内容
//         */
//        public void send(String qid,String mobile,String code,String msg) {
//            this.mQid = qid;
//            String msg1 = "尊敬的客户，您正在进行";
//            String msg2 = "(6小时内有效)，我站工作人员不会向您索取短信内容。[带你玩新乡]";
//            msg1 += msg + "操作，短信授权码为";
//            String content = msg1 + code + msg2;
//            List<String> divideContents = mSmsManager.divideMessage(content);
//            for ( String text : divideContents ) {
//                try{
//                    mSmsManager.sendTextMessage(mobile, null,  text,  mSentPI,  mDeliverPI);
//                } catch(Exception e){
//                    Toast.makeText(this.mContext,  "短信发送失败，请检查是系统否限制本应用发送短信", 5000).show();
//                    e.printStackTrace();
//                } 
//            }
//        }
//
//        private void updateStatus(String qid, String status) {
//            //短信发送成功后做什么事情，就自己定吧
//        }

}
