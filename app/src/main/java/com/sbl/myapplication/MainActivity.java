package com.sbl.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btCallJS;
    WebView webView;

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCallJS = findViewById(R.id.bt_js);

        webView = findViewById(R.id.wv);

        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setJavaScriptEnabled(true);

        webView.loadUrl("file:///android_asset/index.html");


        /**
         * 添加javascriptInterface
         * 第一个参数：这里需要一个与js映射的java对象
         * 第二个参数：该java对象被映射为js对象后在js里面的对象名，在js中要调用该对象的方法就是通过这个来调用
         */
        webView.addJavascriptInterface(this, "mAndroid");
        //实现Android调用js里的函数
        btCallJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行JavaScript里面的changeDivContent()函数.
                webView.loadUrl("JavaScript:changeDivContent()");
            }
        });
    }

    /**
     * java可以根据字节码进行反射.
     * 一旦添加JavascriptInterface注解后,就不能在js里面执行反射的功能了.
     * 4.2及以上api与JavaScript交互时需要在方法上添加注解@JavascriptInterface 。
     */

    @JavascriptInterface
    public void showToast() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "我是不带参数的Toast!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @JavascriptInterface
    public void showToastWithParams(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
