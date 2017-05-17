 # IN ANDROID JAVA CLASS
## #1 create class WebInterFace
		public class WebInterFace {
            @JavascriptInterface
            public void showToast() {
                Toast.makeText(MainActivity.this, "Toast from js", Toast.LENGTH_SHORT).show();
            }
        }



## #2 make Webview with interface
### looking at `toaskAction`, the toast key much be equal/ match with javascript function on your html page.

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


 # IN THE HTML
 ### #1 You have a simple html page with button

 	<button onclick="androiToast()">Android Toast</button>

 ### #2 and in create function androiToast() just call the `toastKey` like this:
     <script>
    function androiToast() {
            toaskAction.showToast();

            }
    </script>

 ### notice :
 #### You can do anything with  Javascript Interface when `button clicked` in function `showToast()` of class `WebInterFace` in the top of this README


Demo https://youtu.be/3wHA3dh8k-g