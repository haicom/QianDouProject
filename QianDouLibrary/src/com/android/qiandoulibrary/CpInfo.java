package com.android.qiandoulibrary;

import java.util.HashMap;
import java.util.Map;


import android.os.Parcel;
import android.os.Parcelable;

public class CpInfo implements Parcelable {
    String  mCpId;
    String  mCpName;
    String  mCpCode;
    String  mCpEmail;
    String  mAppId;
    String  mAppName;
    String  mTelephone;
    String mType;
    HashMap<Integer, UnicomConsume>  mUnicomConsumes;
   
    public CpInfo() {
        super();
    }
    
    private CpInfo(Parcel in) {
        mCpId = in.readString();
        mCpName = in.readString();
        mCpCode = in.readString();
        mCpEmail = in.readString();
        mAppId = in.readString();
        mAppName = in.readString();
        mTelephone = in.readString();
        mType = in.readString();
        mUnicomConsumes = in.readHashMap(  HashMap.class.getClassLoader()  );
    }
    
    @Override
    public int describeContents() {
       
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mCpId);
        out.writeString(mCpName);
        out.writeString(mCpCode);
        out.writeString(mCpEmail);
        out.writeString(mAppId);
        out.writeString(mAppName);
        out.writeString(mTelephone);
        out.writeString(mType);
        out.writeMap(mUnicomConsumes);        
    }
    
    public static final Parcelable.Creator<CpInfo> CREATOR
        = new Parcelable.Creator<CpInfo>() {
            public CpInfo createFromParcel(Parcel in) {
                return new CpInfo(in);
            }
        
            public CpInfo[] newArray(int size) {
                return new CpInfo[size];
            }
     };

    public String getCpId() {
        return mCpId;
    }

    public void setCpId(String cpId) {
        this.mCpId = cpId;
    }

    public String getCpName() {
        return mCpName;
    }

    public void setCpName(String cpName) {
        this.mCpName = cpName;
    }

    public String getCpCode() {
        return mCpCode;
    }

    public void setCpCode(String cpCode) {
        this.mCpCode = cpCode;
    }

    public String getCpEmail() {
        return mCpEmail;
    }

    public void setCpEmail(String cpEmail) {
        this.mCpEmail = cpEmail;
    }

    public String getAppId() {
        return mAppId;
    }

    public void setAppId(String appId) {
        this.mAppId = appId;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        this.mAppName = appName;
    }

    public String getTelephone() {
        return mTelephone;
    }

    public void setTelephone(String telephone) {
        this.mTelephone = telephone;
    }

    
    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public HashMap<Integer, UnicomConsume> getUnicomConsumes() {
        return mUnicomConsumes;
    }

    public void setUnicomConsumes(HashMap<Integer, UnicomConsume> unicomConsumes) {
        this.mUnicomConsumes = unicomConsumes;
    }
     
}
