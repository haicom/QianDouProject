package com.android.qiandoulibrary.lib;

import com.android.qiandoulibrary.CpInfo;
import com.android.qiandoulibrary.IQianDouInterface;
import android.content.Context;
import android.util.Log;


public class MainDalvik implements IQianDouInterface {
    private static final String TAG = "MainDalvik";
    private Context    mContext;   
    private SendSms  mSendSms;
    private ObtainCpInfo mObtainCpInfo;
   
    public MainDalvik(Context context) {
       Log.d(TAG, "MainDalvik(Context context)");
       mContext = context;    
       mSendSms = new SendSms(context);
       mObtainCpInfo = new ObtainCpInfo(context);
    }
  
   
    @Override
    public boolean sendMessageText() {
        Log.d(TAG, "MainDalvik sendMessageText ");
        mSendSms.sendSmSContext("0",  "18951088071",  "1234",  "Test");        
        return false;
    }
    
  

    @Override
    public CpInfo getCpInfo(String localDexLoad) {
        return mObtainCpInfo.AnalysisJson(localDexLoad);        
    }

}
