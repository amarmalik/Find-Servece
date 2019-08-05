package com.example.amazingami.findservice.Notification_package;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by mayankthakur on 4/14/2017.
 */
public class Util {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
}
