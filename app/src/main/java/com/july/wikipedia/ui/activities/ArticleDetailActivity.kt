package com.july.wikipedia.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.july.wikipedia.R
import com.july.wikipedia.database.repositories.ItemRepository
import com.july.wikipedia.database.rooms.getItemDatabase
import com.july.wikipedia.models.WikiPageDto
import com.july.wikipedia.utils.showToast
import kotlinx.coroutines.launch

class ArticleDetailActivity : AppCompatActivity() {
    private var itemRepository: ItemRepository? = null

    private var currentPage: WikiPageDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        itemRepository = ItemRepository(getItemDatabase(application))


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Create back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Get the page from the extras
        val wikiPageJson = intent.getStringExtra("page")
        currentPage = Gson().fromJson(wikiPageJson, WikiPageDto::class.java)

        // Update toolbar's title
        supportActionBar!!.title = currentPage?.title



        val webView: WebView = findViewById(R.id.article_detail_webview)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }

        webView.loadUrl(currentPage?.fullUrl!!)

        lifecycleScope.launch {
            // Set seen for history
            itemRepository!!.updateHistory(currentPage!!.pageId, true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_menu, menu)

        val iconFav = menu?.findItem(R.id.action_favorite)

        if (currentPage?.isFav == true) {
            iconFav?.setIcon(R.drawable.baseline_favorite_24)
        } else {
            iconFav?.setIcon(R.drawable.baseline_favorite_border_24)
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.action_favorite) {   // save favorite button
            try {
                // Determine if article is already for a favorite or not
                lifecycleScope.launch {
                    val isFav = itemRepository?.findFavorite(currentPage!!.pageId)
                    if (isFav == true) {
                        // Remove favorite
                        itemRepository?.updateFavorite(currentPage!!.pageId, false)

                        // Set icon
                        item.setIcon(R.drawable.baseline_favorite_border_24)

                        showToast(
                            baseContext,
                            "Article was removed from Favorites",
                            Toast.LENGTH_SHORT
                        )
                    } else {
                        // Add favorite
                        itemRepository?.updateFavorite(currentPage!!.pageId, true)

                        // Set icon
                        item.setIcon(R.drawable.baseline_favorite_24)

                        showToast(baseContext, "Article was added to Favorites", Toast.LENGTH_SHORT)
                    }
                }
            } catch (e: Exception) {
                Log.d("HEHE", e.printStackTrace().toString())
                showToast(baseContext, "Unable to update this article", Toast.LENGTH_SHORT)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}