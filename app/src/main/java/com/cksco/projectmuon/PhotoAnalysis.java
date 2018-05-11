package com.cksco.projectmuon;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Admin on 2018-05-11.
 */

public class PhotoAnalysis extends AsyncTask<ByteBuffer,Integer,Void> {
int mThreshold;
int mIntensity;
int mWidth,mHeight;
Context mContext;
PhotoAnalysis(int width,int height){
    mWidth=width;
    mHeight=height;
}
PhotoAnalysis(int width,int height,Context context) {
mWidth=width;
mHeight=height;
mContext=context;
}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mThreshold=300;

    }

    @Override
    protected Void doInBackground(ByteBuffer... byteBuffers) {
       ByteBuffer buff=byteBuffers[0];
       for(int i=0;i<buff.array().length-13;i+=4){
           mIntensity=0;
           for (int r=0;r<3;r++) {
               mIntensity += buff.get(i + r);

           }
           if(mIntensity>=mThreshold){
               downloadFromBuffer(buff,"test");
               return null;
           }
       }
       return null;

    }
    protected void downloadFromBuffer(ByteBuffer buff,String filename){
        buff.rewind();
        Bitmap bitmap= Bitmap.createBitmap(mWidth,mHeight,Bitmap.Config.ARGB_8888);

        bitmap.copyPixelsFromBuffer(buff);
        android.graphics.Matrix matrix=new android.graphics.Matrix();
        matrix.preScale(1f,-1f);
        Bitmap bitmap2=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,false);
        bitmap=null;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG, 50, bytes);
        File extStorDirect = Environment.getExternalStorageDirectory();
        File file = new File(extStorDirect + File.separator + filename);
        FileOutputStream fileOutputStream = null;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes.toByteArray());
            ContentResolver cr = mContext.getContentResolver();

            String imagePath = file.getAbsolutePath();
            String name = file.getName();
            String description = "My bitmap created by Android-er";
            String savedURL = MediaStore.Images.Media
                    .insertImage(cr, imagePath, name, description);
           System.out.println("file made bois");


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    }


