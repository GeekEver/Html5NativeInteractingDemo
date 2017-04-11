package me.geekever.html5nativeinteractingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainWebViewActivity extends BaseWebViewActivity {
    private WebView mWebView;
    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton=(Button)findViewById(R.id.color_changer);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color="#00FFFF";
                mWebView.loadUrl("javascript: changeColor('"+color+"');");
            }
        });
        mWebView=(WebView)findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JumpInterface(),"Android");
        mWebView.loadUrl("http://123.206.57.23/testNH.html");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.removeAllViews();
        mWebView.destroy();
    }

    /**
     *
     * @param key
     * Data Format:Activity,param1=(type)value&param2=(type)value...
     * @return
     */
    @Override
    protected String getPageName(String key) {
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

    /**
     *
     * @param url
     * Data Format:Activity,param1=(type)value&param2=(type)value...
     */
    @Override
    public void jump(String url) {
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
}