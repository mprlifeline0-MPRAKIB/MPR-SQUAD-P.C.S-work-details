package com.mpr.squadpcs;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.view.ViewGroup;

import java.io.OutputStream;

public class MainActivity extends Activity {
    private WebView webView;
    private WebView reportWebView;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(android.graphics.Color.rgb(16, 43, 83));
        getWindow().setNavigationBarColor(android.graphics.Color.rgb(16, 43, 83));
        webView = new WebView(this);
        webView.setBackgroundColor(android.graphics.Color.WHITE);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        webView.addJavascriptInterface(new AndroidBridge(), "AndroidBridge");
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        setContentView(webView);
        if (savedInstanceState != null) webView.restoreState(savedInstanceState);
        else webView.loadUrl("file:///android_asset/index.html");
    }

    private class AndroidBridge {
        @JavascriptInterface public void printReport(String html) {
            runOnUiThread(() -> {
                try {
                    reportWebView = createReportWebView(html, view -> {
                        PrintManager manager = (PrintManager) getSystemService(PRINT_SERVICE);
                        if (manager == null) throw new IllegalStateException("Print service unavailable");
                        manager.print("MPR SQUAD P.C.S Work Details", view.createPrintDocumentAdapter("MPR_SQUAD_Report"),
                                new PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                                        .setResolution(new PrintAttributes.Resolution("mpr", "MPR", 300, 300))
                                        .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build());
                    });
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "PDF প্রিন্ট চালু করা যায়নি", Toast.LENGTH_LONG).show();
                }
            });
        }

        @JavascriptInterface public void saveReportImage(String html) {
            runOnUiThread(() -> {
                try {
                    createReportWebView(html, view -> saveRenderedReportImage(view));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "ছবি সংরক্ষণ করা যায়নি", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private interface ReportReady { void ready(WebView view); }

    private WebView createReportWebView(String html, ReportReady ready) {
        WebView report = new WebView(this);
        report.getSettings().setJavaScriptEnabled(true);
        int width = getResources().getDisplayMetrics().widthPixels;
        report.setAlpha(0.01f);
        report.setClickable(false);
        report.setFocusable(false);
        report.setFocusableInTouchMode(false);
        report.setTranslationX(width + 1000f);
        addContentView(report, new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
        report.setWebViewClient(new WebViewClient() {
            @Override public void onPageFinished(WebView view, String url) {
                view.postDelayed(() -> {
                    int contentHeight = Math.max(1, (int) (view.getContentHeight() * view.getScale()));
                    view.measure(android.view.View.MeasureSpec.makeMeasureSpec(width, android.view.View.MeasureSpec.EXACTLY),
                            android.view.View.MeasureSpec.makeMeasureSpec(contentHeight, android.view.View.MeasureSpec.EXACTLY));
                    view.layout(0, 0, width, contentHeight);
                    ready.ready(view);
                }, 500);
            }
        });
        report.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);
        return report;
    }

    private void saveRenderedReportImage(WebView report) {
        try {
            Picture picture = report.capturePicture();
            int width = Math.max(1, picture.getWidth());
            int height = Math.max(1, picture.getHeight());
            double scale = Math.min(1.0, Math.min(3000.0 / Math.max(width, height), Math.sqrt(12000000.0 / ((double) width * height))));
            int outW = Math.max(1, (int) (width * scale));
            int outH = Math.max(1, (int) (height * scale));
            Bitmap bitmap = Bitmap.createBitmap(outW, outH, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.scale((float) scale, (float) scale);
            picture.draw(canvas);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "MPR_SQUAD_Report_" + System.currentTimeMillis() + ".jpg");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MPR SQUAD");
            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri == null) throw new IllegalStateException("Gallery destination unavailable");
            try (OutputStream stream = getContentResolver().openOutputStream(uri)) {
                if (stream == null) throw new IllegalStateException("Cannot open image destination");
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            }
            bitmap.recycle();
            Toast.makeText(MainActivity.this, "রিপোর্টের ছবি Gallery-তে সংরক্ষিত হয়েছে", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "ছবি সংরক্ষণ করা যায়নি। আবার চেষ্টা করুন।", Toast.LENGTH_LONG).show();
        } finally {
            report.postDelayed(() -> { ((ViewGroup) report.getParent()).removeView(report); report.destroy(); }, 500);
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (webView != null) webView.saveState(outState);
    }

    @Override public void onBackPressed() {
        if (webView == null) { super.onBackPressed(); return; }
        webView.evaluateJavascript("(function(){return window.handleAndroidBack?window.handleAndroidBack():false})()", value -> {
            if ("false".equals(value) || value == null) MainActivity.this.finish();
        });
    }
}
