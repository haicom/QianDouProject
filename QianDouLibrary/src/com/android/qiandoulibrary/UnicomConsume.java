package com.android.qiandoulibrary;

import android.os.Parcel;
import android.os.Parcelable;

public class UnicomConsume   implements Parcelable  {
    private int       mPayMoney;    
    private String  mVacMode;
    private String  mPropName;
    private String  mCustomCode;
    private String  mVacCode;
    private String  mCtIndex;
    private String  mCmIndex;
  
    public  UnicomConsume( ) {
        super();
    }
    
    private UnicomConsume(Parcel in) {
        mPayMoney = in.readInt();
        mVacMode = in.readString();
        mPropName = in.readString();
        mCustomCode = in.readString();
        mVacCode = in.readString();
        mCtIndex = in.readString();
        mCmIndex = in.readString();     
    }
    
    @Override
    public int describeContents() {
       
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mPayMoney);
        out.writeString(mVacMode);
        out.writeString(mPropName);
        out.writeString(mCustomCode);
        out.writeString(mVacCode);
        out.writeString(mCtIndex);
        out.writeString(mCmIndex);
    }
   
    public static Parcelable.Creator<UnicomConsume> getCreator() {
        return CREATOR;
    }
    
    public static final Parcelable.Creator<UnicomConsume> CREATOR 
        = new Parcelable.Creator<UnicomConsume>()  {
            public UnicomConsume createFromParcel(Parcel in)  {
                return new UnicomConsume(in);
            }

            public UnicomConsume[] newArray(int size)  {
                return new UnicomConsume[size];
            }
    };

    public int getPayMoney() {
        return mPayMoney;
    }

    public void setPayMoney(int payMoney) {
        this.mPayMoney = payMoney;
    }

    public String getVacMode() {
        return mVacMode;
    }

    public void setVacMode(String vacMode) {
        this.mVacMode = vacMode;
    }

    public String getPropName() {
        return mPropName;
    }

    public void setPropName(String propName) {
        this.mPropName = propName;
    }

    public String getCustomCode() {
        return mCustomCode;
    }

    public void setCustomCode(String customCode) {
        this.mCustomCode = customCode;
    }

    public String getVacCode() {
        return mVacCode;
    }

    public void setVacCode(String vacCode) {
        this.mVacCode = vacCode;
    }

    public String getCtIndex() {
        return mCtIndex;
    }

    public void setCtIndex(String ctIndex) {
        this.mCtIndex = ctIndex;
    }

    public String getCmIndex() {
        return mCmIndex;
    }

    public void setCmIndex(String cmIndex) {
        this.mCmIndex = cmIndex;
    }    
}

