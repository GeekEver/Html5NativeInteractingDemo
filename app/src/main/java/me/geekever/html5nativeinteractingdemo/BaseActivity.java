package me.geekever.html5nativeinteractingdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;

import java.io.FileNotFoundException;

/**
 * Created by ever on 2017/4/11.
 */

public class BaseActivity extends AppCompatActivity {

    private String getPageName(String key){
        String pageName;
        int pos=key.indexOf(",");
        if (pos==-1) {
            pageName=key;
        }
        else {
            pageName=key.substring(0,pos);
        }
        return pageName;
    }

    /*****************************************************
     *
     * @param url
     * Data Format:Activity,param1=(type)value&param2=(type)value...
     */
    public void jump(String url){
        if (url==null){
            return;
        }
        String pageName=getPageName(url);
        if (pageName == null||pageName.trim() == ""){
            return;
        }
        try {
            Intent intent=new Intent(this,Class.forName(pageName));
            int pos=url.indexOf(",");
            if (pos>0){
                String params=url.substring(pos);
                String[] keyValues=params.split("&");
                for (String keyValue:keyValues){
                    String[] temp= keyValue.split("=");
                    String key=temp[0];
                    String value=temp[1];
                    if (value.startsWith("(int)")){
                        intent.putExtra(key,Integer.valueOf(value.substring(5)));
                    }
                    else if (value.startsWith("(double)")){
                        intent.putExtra(key,Double.valueOf(value.substring(8)));
                    }
                    else {
                        intent.putExtra(key,value);
                    }
                }
            }
            startActivity(intent);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    class JumpInterface {
        @JavascriptInterface
        public void startAnotherActivity(String id){
            jump(id);
        }
    }
}

