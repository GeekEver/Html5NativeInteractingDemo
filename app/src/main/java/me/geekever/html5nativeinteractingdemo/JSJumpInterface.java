package me.geekever.html5nativeinteractingdemo;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

/**
 * Created by ever on 2017/4/11.
 */

/**
 * Obsolete
 */
public class JSJumpInterface {
    private Context mContext;
    JSJumpInterface(Context context){
        mContext=context;
    }
    @JavascriptInterface
    public String startAnotherActivity(String id){
        Class<?> activityClass;
        try {
            activityClass=Class.forName(id);
            Intent intent=new Intent(mContext,activityClass);
            mContext.startActivity(intent);
            return id;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
