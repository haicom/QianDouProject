package com.android.qiandoulibrary.lib;

import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SendSms {
    private static final String TAG = "SendSms";
    private static final  String   SENT_SMS_ACTION = "SENT_SMS_ACTION";
    private static final String    DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";   
    private Context               mContext;   
    private Intent                  mSentIntent;
    private SmsManager     mSmsManager;
    private PendingIntent  mSendPI;
    private Intent                  mDeliverIntent;
    private PendingIntent  mDeliverPI;
    //SendService myServer; //此为我应用中的服务，你可以取消
    private String                   mQid;  //短信序列号ID，该参数为我的应用使用的参数，你可以不要
    
    public SendSms(Context context) {
        Log.d(TAG, " SendSms(Context context) ");
        mContext = context;
        mQid = "0";
        mDeliverIntent = new Intent(DELIVERED_SMS_ACTION);
        mSentIntent = new Intent(SENT_SMS_ACTION);
        mSmsManager = SmsManager.getDefault();      
        mSendPI = PendingIntent.getBroadcast(mContext, 0, mSentIntent, 0);
        
        //短信发送状态监控
        mContext.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                switch( getResultCode() ) {
                    case Activity.RESULT_OK:
//                        MyLog.i(TAG,"信息已发出");
                        updateStatus(mQid,"1");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "未指定失败 \n 信息未发出，请重试",  Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "无线连接关闭 \n 信息未发出，请重试",  Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "PDU失败 \n 信息未发出，请重试",  Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION) );

        //短信是否被接收状态监控
        mDeliverPI = PendingIntent.getBroadcast( mContext, 0, mDeliverIntent, 0  );
        
        mContext.registerReceiver( new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(mContext,  "SMS delivered",  Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText( mContext , "SMS not delivered",  Toast.LENGTH_SHORT).show();
                        break;
                }
               // myDialog.setMessage("已送达服务终端");
                //checkService();
            }
        },  new IntentFilter(DELIVERED_SMS_ACTION) );
    }
    
    /**
     * 发送短信，这里是我需要的几个参数，你可以根据你的具体情况来使用不同的参数
     * @param qid
     * @param mobile 要发送的目标手机号，这个必须要有
     * @param code
     * @param msg 发送的短信内容
     */
     public void sendSmSContext( String qid, String mobile, String code, String msg ) {
         this.mQid = qid;
         String msg1 = "尊敬的客户，您正在进行";
         String msg2 = "(6小时内有效)，我站工作人员不会向您索取短信内容。[带你玩新乡]";
         msg1 += msg + "操作，短信授权码为";
         String content = msg1 + code + msg2;
         
         List<String> divideContents = mSmsManager.divideMessage(content);
        
         for ( String text : divideContents )  {            
             try{
                 mSmsManager.sendTextMessage(mobile, null,  text,  mSendPI,  mDeliverPI);
             } catch(Exception e) {
                 Toast.makeText(mContext,   "短信发送失败，请检查是系统否限制本应用发送短信",   Toast.LENGTH_LONG).show();
                 e.printStackTrace();
             }           
        }
    }

    private void updateStatus(String qid, String status) {
        //短信发送成功后做什么事情，就自己定吧

    }
}
