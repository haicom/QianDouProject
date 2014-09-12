package com.android.qiandoulibrary.lib;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.qiandoulibrary.CpInfo;
import com.android.qiandoulibrary.UnicomConsume;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

public class ObtainCpInfo {
    private static final String TAG = "ObtainCpInfo";
    
    private static final String  QIANDOU_UNICOM = "UnicomConsume.uwc";    
    private Context    mContext;   
//    private String        mLocalDexLoad;
    
    public ObtainCpInfo(Context context) {
        Log.d(TAG, "ObtainCpInfo(Context context) " );
        mContext = context;        
     }
    
//    {"cpinfo":
//    {"cp_id":"",
//    "cp_name":"北京万爱网络科技有限公司",
//    "cp_code":"90234616155",
//    "cp_email":"tingting.he@chukong-inc.com",
//    "app_id":"903786384320140806092524929900",
//    "app_name":"诚迈新平台联调1",
//    "telephone":"02100000000"},
//    "type":"1",
//    "unicomconsume":[
//    {"pay_money":"100","vac_mode":"1","prop_name":"诚迈新平台1元","custom_code":"903786384320140806092524929900001","vac_code":"140806048932","ct_index":"","cm_index":"","number":"001"},
//    {"pay_money":"200","vac_mode":"1","prop_name":"诚迈新平台2元","custom_code":"903786384320140806092524929900002","vac_code":"140806048933","ct_index":"","cm_index":"","number":"002"},
//    {"pay_money":"300","vac_mode":"1","prop_name":"诚迈新平台3元","custom_code":"903786384320140806092524929900003","vac_code":"140806048934","ct_index":"","cm_index":"","number":"003"},
//    {"pay_money":"400","vac_mode":"1","prop_name":"诚迈新平台4元","custom_code":"903786384320140806092524929900004","vac_code":"140806048935","ct_index":"","cm_index":"","number":"004"},
//    {"pay_money":"500","vac_mode":"1","prop_name":"诚迈新平台5元","custom_code":"903786384320140806092524929900005","vac_code":"140806048936","ct_index":"","cm_index":"","number":"005"}
//    ]}
    @SuppressLint("UseSparseArrays")
    public CpInfo AnalysisJson(String localDexLoad ) {     
        BufferedReader                br      = null;
        InputStreamReader             input = null;
        String                        result = null;
        String                        temp  = null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.setLength(0);       
        
        Log.d(TAG, "AnalysisJson localDexLoad = " + localDexLoad);
        File file = new File(localDexLoad);
        
        if  ( file.exists() ) {            
            try {
                br = new BufferedReader( new FileReader( file.getAbsoluteFile() ) );
            } catch (FileNotFoundException e) {               
                e.printStackTrace();
            }
        } else {
            try {
                input = new InputStreamReader( mContext.getAssets().open(QIANDOU_UNICOM) );
            } catch (IOException e) {               
                e.printStackTrace();
            }
            
            br = new BufferedReader(input); 
        }
     
        try {          
            while ( ( temp = br.readLine() ) != null) {
//                result += temp;
                stringBuilder.append(temp);
            }
        } catch ( FileNotFoundException fileException ) {          
            fileException.printStackTrace();
        } catch (IOException e) {           
            e.printStackTrace();
        }

        Log.d(TAG, "AnalysisJson stringBuilder.toString() = " + stringBuilder.toString() );
        CpInfo cpinfo = new CpInfo();
        HashMap<Integer, UnicomConsume>  unicomConsumes = new HashMap<Integer, UnicomConsume> ( );
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject( stringBuilder.toString() ).getJSONObject("cpinfo");
            cpinfo.setCpId(  jsonObject.optString("cp_id")  );
            cpinfo.setCpName(  jsonObject.optString("cp_name")  );
            cpinfo.setCpCode(  jsonObject.optString("cp_code")  );
            cpinfo.setCpEmail(  jsonObject.optString("cp_email")  );
            cpinfo.setAppId(  jsonObject.optString("app_id")  );
            cpinfo.setAppName(  jsonObject.optString("app_name")  );
            cpinfo.setTelephone(  jsonObject.optString("telephone")  );
            cpinfo.setType(  jsonObject.optString("type")  );
            
            JSONArray  jsonArray = new JSONObject( stringBuilder.toString() ).getJSONArray("unicomconsume");
            for ( int index = 0; index < jsonArray.length(); index++ ) {
                int number;
                jsonObject = (JSONObject) jsonArray.opt(index);                
                UnicomConsume consume = new UnicomConsume();
                consume.setPayMoney(  Integer.valueOf(  jsonObject.getString("pay_money")  ) );
                consume.setVacMode(  jsonObject.getString("vac_mode")  );
                consume.setPropName( jsonObject.getString("prop_name") );
                consume.setCustomCode( jsonObject.getString("custom_code") );
                consume.setVacCode( jsonObject.getString("vac_code") );
                consume.setCtIndex( jsonObject.getString("ct_index") );
                consume.setCmIndex( jsonObject.getString("cm_index") );
                number = Integer.valueOf( jsonObject.getString("number") );     
                unicomConsumes.put(number, consume );    
                
                Log.d(TAG, "unicomConsume.getPayMoney() =  " +  consume.getPayMoney() );
                Log.d(TAG, "   unicomConsume.getVacMode()  =  " + consume.getVacMode()  );
                Log.d(TAG, "  unicomConsume.getPropName()  =  " + consume.getPropName()  );
                Log.d(TAG, "  unicomConsume.getCustomCode()  =  " + consume.getCustomCode()  );
                Log.d(TAG, "  unicomConsume.getVacCode()    =  " +  consume.getVacCode()  );
                Log.d(TAG, "   unicomConsume.getCtIndex()    =  " +  consume.getCtIndex()  );
                Log.d(TAG, "  unicomConsume.getCmIndex()     =  " +  consume.getCmIndex()  );
                Log.d(TAG, "  unicomConsume.Number()     =  " + number  );
            }
        } catch (JSONException e) {            
            e.printStackTrace();
        }
                
        cpinfo.setUnicomConsumes(unicomConsumes);
        
        return cpinfo;
    }
}
