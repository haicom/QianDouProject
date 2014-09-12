package com.android.qiandoulibrary;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;

import dalvik.system.DexClassLoader;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class MainLibrary {
    private static final String TAG = "MainLibrary";    
    private static final int BUFFER_SIZE = 1024 * 2;
    
    private static final String QIANDOU_DEX_NAME = "qiandoudex.jar";
    private static final String  QIANDOU_UNICOM = "unicomconsume.uwc";
    
    private Context mContext;
//    private String    mLoadDexPath;
    private String    mLocalDexLoad;
    private File         mFile;
    private IQianDouInterface  sIQianDouInterface;
    private ProgressDialog mProgressDialog = null;
    
    /**
     * Constructs a new MainLibrary instance
     *
     * @param context : Binder context for this service
     */
    public MainLibrary(Context context) {
        mContext = context;
        String[] fileNames;
        String filePath = mContext.getFilesDir().toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.setLength(0);       
        
        try {
            fileNames = mContext.getResources().getAssets().list("");
            for (String assname: fileNames) {
                Log.d(TAG, "findFileName assname = " + assname );             
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "findFileName e.toString() = " + e.toString() );
        }
        
//        mLocalDexLoad =  stringBuilder.append(filePath).append(File.separator).
//                append(QIANDOU_UNICOM).toString();
        
        final File  unicomFile = new File( mContext.getDir("dex", Context.MODE_PRIVATE),
                QIANDOU_UNICOM);
//        File  file = new File( mLocalDexLoad );    
        mLocalDexLoad =  unicomFile.getAbsolutePath();
        if ( !unicomFile.exists() ) {
            copyToCacheDir(unicomFile, QIANDOU_UNICOM);
            
        }
       
        
        // Before the  dex file can be processed by the DexClassLoader,
        // it has to be first copied from R.RAW resource to a storage location.
        final File dexInternalStoragePath = new File( mContext.getDir("dex", Context.MODE_PRIVATE),
                QIANDOU_DEX_NAME);
        if (  !dexInternalStoragePath.exists()  ) {
            copyToCacheDir(dexInternalStoragePath, QIANDOU_DEX_NAME);
        }       
        dalvikLoadInstance(dexInternalStoragePath);
    }
    
    // File I/O code to copy the  dex file from R.RAW  resource to internal storage.
    private boolean copyToCacheDir(File storagePath, String name) {
        String[] fileNames;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
//        String filePath = mContext.getFilesDir().toString();
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.setLength(0);    
        
//        String loadDexPath =  stringBuilder.append(filePath).toString();
//        String localDexLoad =  stringBuilder.append(File.separator).append(name).toString();
//        File  file = new File( localDexLoad );
        
        byte[] buffer = new byte[BUFFER_SIZE];
        
        int  n = 0;
        
    
        
        try {
            //InputStream input = mContext.getResources().openRawResource(id);
            InputStream input = new BufferedInputStream( mContext.getAssets().open(name) );
            //InputStream input = mContext.getResources().getAssets().open(name);
            OutputStream output = new FileOutputStream(storagePath);
            
            in = new BufferedInputStream(input, BUFFER_SIZE);
            out = new BufferedOutputStream(output, BUFFER_SIZE);
            
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                
            }
            
            out.flush();            
            out.close();
            in.close();
            
            return true;
            
        } catch (IOException e) {            
            
            if ( in != null ) {
                try {
                    in.close();
                    
                } catch (IOException ioe) {                   
                    e.printStackTrace();
                }
            }
            
            if ( out != null ) {
                try {
                    out.close();
                } catch (IOException ioe) {                   
                    e.printStackTrace();
                }
            }
            
            return false;
        }        
    }
    
    
    public boolean dalvikLoadInstance(File file) {
        boolean iResult = false;
     
        
        // Internal storage where the DexClassLoader writes the optimized dex file to.
        final File optimizedDexOutputPath = mContext.getDir("outdex", Context.MODE_PRIVATE);
   
//        DexClassLoader classLoader = new DexClassLoader(mFile.getAbsolutePath(),
//                mContext.getFilesDir().toString(), null, mContext.getClassLoader() );

        // Initialize the class loader with the secondary dex file.
        DexClassLoader cl = new DexClassLoader(
                file.getAbsolutePath(),  // in  "jar"
                optimizedDexOutputPath.getAbsolutePath(),  // out "dex"
                null,
                mContext.getClassLoader() );
        
          Log.d(TAG, "in path -> " + file.getAbsolutePath()
                +  ", out path -> " + optimizedDexOutputPath.getAbsolutePath());
          
            Class libProviderClazz      = null;       
            Class[] parameter              = null;
            Constructor constructor = null;
            
            if  ( sIQianDouInterface == null  ) {
                try {
                    // Load the library class from the class loader.
                    libProviderClazz = cl.loadClass("com.android.qiandoulibrary.lib.MainDalvik");
                    parameter = new Class[]{Context.class};
                    constructor = libProviderClazz.getConstructor(parameter);
                     Log.d(TAG, " constructor  = " + constructor); 
                    sIQianDouInterface = (IQianDouInterface)constructor.newInstance(mContext);
                    iResult = true;
                } catch (Exception exception) {
                    // Handle exception gracefully here.
                    exception.printStackTrace();
                    iResult = false;
                }
            }
            
        return iResult;
    }
   
 
    

//    private class PrepareDexTask extends AsyncTask<File, Void, Boolean> {
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//            if (mProgressDialog != null)  mProgressDialog.cancel();
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            super.onPostExecute(result);
//            if (mProgressDialog != null)  mProgressDialog.cancel();
//        }
//
//        @Override
//        protected Boolean doInBackground(File... dexInternalStoragePaths) {
//           // prepareDex(dexInternalStoragePaths[0]);
//            return null;
//        }
//    }
    
    public boolean sendMessageText() {
        return sIQianDouInterface.sendMessageText();
    }
    
    public CpInfo getCpInfo() {
        return sIQianDouInterface.getCpInfo(mLocalDexLoad);
    }
   
}
