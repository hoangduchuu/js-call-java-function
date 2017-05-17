package com.yokara.javascriptcalljava;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private String TAG = "ccc";

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.videoview);


        setupWebView("file:///android_asset/a.html");
//        setupWebView("http://192.168.1.140/html/a.html");
    }

    public class JavaScriptInterface {
        private Activity activity;

        public JavaScriptInterface(Activity activiy) {
            this.activity = activiy;
        }

        public void startVideo(String videoAddress) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(videoAddress), "video/3gpp");
            activity.startActivity(intent);
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void setupWebView(String webLink) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT2"); // add new JSInterFace rồi
        webView.addJavascriptInterface(new WebInterFace(), "toaskAction");

        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                setMyJavascripts();
            }
        });
        webView.loadUrl(webLink);

    }

    class MyJavaScriptInterface {
        @SuppressLint("LongLogTag")
        @JavascriptInterface
        public void gotPairingCode(String jsResult) {
            Log.d(TAG + "realParingCode", jsResult);
        }

        @SuppressLint("LongLogTag")
        @JavascriptInterface
        public void jsQueryName2(String jsResult) {
            Log.d(TAG + "jsQueryName2", jsResult);
        }
    }

    private void setMyJavascripts() {
        webView.loadUrl("javascript:window.HTMLOUT.gotPairingCode(document.getElementById('pairing_code').innerHTML);");
        webView.loadUrl("javascript:window.HTMLOUT2.jsQueryName2(document.getElementById('queueLabel').innerHTML);");
    }

    public class WebInterFace {
        @JavascriptInterface
        public void showToast() {
            Toast.makeText(MainActivity.this, "Toast from js2", Toast.LENGTH_SHORT).show();
            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("hehe");
            progressDialog.setTitle("javascript");
            progressDialog.show();
        }
        
        @JavascriptInterface
        public  void kaka(){
            Toast.makeText(MainActivity.this, "hehe button", Toast.LENGTH_SHORT).show();
        }
    }
}
