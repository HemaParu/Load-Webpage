package com.example.webpageapplication

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    var toggleChecked: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toggleButton = findViewById<ToggleButton>(R.id.intent_toggle)
        val imageButton = findViewById<ImageButton>(R.id.intent_button)
        val webView = findViewById<WebView>(R.id.webpage_view)
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            toggleChecked = isChecked
            if (!isChecked) {
                webView.loadUrl("")
                loadToast("Can't load Web Page")
            }
        }

        imageButton.setOnClickListener {
            if (toggleChecked) {
                webView.webViewClient = MyWebViewClient(this)
                webView.loadUrl("https://www.google.com/")
                loadToast("Web Page Loaded")
            } else {
                loadToast("Can't load Web Page")
            }
        }
    }

    private fun loadToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url: String = request?.url.toString();
            view?.loadUrl(url)
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
            return true
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }
    }

}