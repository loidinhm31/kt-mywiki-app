package com.july.wikipedia.activities

import com.july.wikipedia.WikiApplication
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.july.wikipedia.R
import com.july.wikipedia.managers.WikiManager
import com.july.wikipedia.models.WikiPage
import org.jetbrains.anko.toast

class ArticleDetailActivity : AppCompatActivity() {
    private var wikiManager: WikiManager? = null
    private var currentPage: WikiPage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        wikiManager = (applicationContext as WikiApplication).wikiManager

        setSupportActionBar(findViewById(R.id.toolbar))
        // create back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // get the page from the extras
        val wikiPageJson = intent.getStringExtra("page")
        currentPage = Gson().fromJson<WikiPage>(wikiPageJson, WikiPage::class.java)

        // update toolbar's title
        supportActionBar!!.title = currentPage?.title

        val detailWebView: WebView = findViewById(R.id.article_detail_webview)
        detailWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return true
            }
        }

        detailWebView.loadUrl(currentPage!!.fullurl!!)
        wikiManager?.addHistory(currentPage!!)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.action_favorite) {
            try {
                // determine if article is already for a favorite or not
                if (wikiManager!!.getIsFavorite(currentPage!!.pageid!!)) {
                    wikiManager!!.removeFavorite(currentPage!!.pageid!!)
                    toast("Article was removed from Favorites")
                } else {
                    wikiManager!!.addFavorite(currentPage!!)
                    toast("Article was added to Favorites")
                }
            } catch (e: Exception) {
                toast("Unable to update this article")
                e.printStackTrace()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}