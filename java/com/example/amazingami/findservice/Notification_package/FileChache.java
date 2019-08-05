package com.example.amazingami.findservice.Notification_package;

import android.content.Context;

import java.io.File;

/**
 * Created by mayankthakur on 4/14/2017.
 */
public class FileChache {

    private File cacheDir;

    public FileChache(Context context){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))

            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"TTImages_cache");

        else
            cacheDir=context.getCacheDir();

        if(!cacheDir.exists())

            cacheDir.mkdirs();
    }

    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename=String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;

    }

    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }
}
