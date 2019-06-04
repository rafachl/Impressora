package com.iposprinter.printertestdemo;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MemInfo {

    public static long getmem_UNUSED(Context mContext)
    {
    long MEM_UNUSED;
    ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
    ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
    am.getMemoryInfo(mi);
    MEM_UNUSED = mi.availMem / 1048576;
    return MEM_UNUSED;
    }

    public static long getmem_TOLAL()
    {
    long mTotal;
    String path = "/proc/meminfo";
    String content = null;
    BufferedReader br = null;
    try
    {
        br = new BufferedReader(new FileReader(path), 8);
        String line;
        if ((line = br.readLine()) != null)
        {
            content = line;
        }
    }
    catch (FileNotFoundException e)
    {
        e.printStackTrace();
    }
    catch (IOException e)
    {
        e.printStackTrace();
    }
    finally
    {
        if (br != null)
        {
            try
            {
                br.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    // beginIndex
    int begin = content.indexOf(':');
    // endIndex
    int end = content.indexOf('k');
    // 截取字符串信息
    content = content.substring(begin + 1, end).trim();
    mTotal = Integer.parseInt(content);
    return mTotal;
}

    /*加快bitmap回收，减少内存占用*/
    public static void bitmapRecycle(Bitmap bitmap)
    {
        if (bitmap != null && !bitmap.isRecycled())
        {
            bitmap.recycle();
        }
    }
}
