package me.geekever.html5nativeinteractingdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;

import java.io.FileNotFoundException;

/**
 * Created by ever on 2017/4/11.
 */

public abstract class BaseWebViewActivity extends AppCompatActivity {
    //获取具体页面的抽象方法，需要根据数据格式具体实现
    protected abstract String getPageName(String key);
    //页面跳转逻辑，参数的获取形式根据自定义的数据格式的不同而不同
    public abstract void jump(String url);

    class JumpInterface {
        @JavascriptInterface
        public void startAnotherActivity(String id){
            jump(id);
        }
    }
}

