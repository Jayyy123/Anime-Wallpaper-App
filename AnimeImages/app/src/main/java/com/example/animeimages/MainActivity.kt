package com.example.animeimages

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animeimages.databinding.ActivityMainBinding
import java.lang.Exception
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

private const val TAG = ""

class MainActivity : AppCompatActivity(),GetRawData.DownloadComplete,GetJsonData.OnData, RecyclerViewClicker.onRecycleClickListener {




    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val recyclerViewAdapter = recyclerAdapter(ArrayList())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Anime Wallpapers by Jay"

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addOnItemTouchListener(RecyclerViewClicker(recycler_view,this,this))
        recycler_view.adapter = recyclerViewAdapter






        val url = createUri("https://www.flickr.com/services/feeds/photos_public.gne", "anime,wallpapers","en-us",true)
//        getRawData.execute("https://www.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=?&lang=zh-hk?&tags=anime,wallpapers")
        val getRawData = GetRawData(this)
        getRawData.execute(url)



    }

    fun createUri(baseUrl:String,tags:String, lang:String, matchAll:Boolean):String{
        return Uri.parse(baseUrl)
            .buildUpon()
            .appendQueryParameter("tags",tags)
            .appendQueryParameter("tagmode",if (matchAll) "ALL" else "ANY")
            .appendQueryParameter("lang",lang)
            .appendQueryParameter("format","json")
            .appendQueryParameter("nojsoncallback","1")
            .build()
            .toString()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onDownloadComplete(data:String, downloadStatus:DownloadStatus){
            if(downloadStatus == DownloadStatus.OK){
                Log.d(TAG,"completed with $data")
                val getJsonData = GetJsonData(this)
                getJsonData.execute(data)
            }else{
                Log.e(TAG,"failed with $downloadStatus and error is $data")
            }


    }

    override fun dataAvailable(data: List<AnimePhotos>) {
        Log.d(TAG,"data is $data")
        recyclerViewAdapter.loadNewData(data)
    }

    override fun error(error: Exception) {
        Log.d(TAG,"data is $error")
    }

    override fun onShortClick(view: View, position: Int) {
        Log.d(TAG, "short startsssssss")
        Toast.makeText(this,"Finally oh ${position}",Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(view: View, position: Int) {
        Log.d(TAG, "startsssssss")
        Toast.makeText(this," omo ${position}",Toast.LENGTH_SHORT).show()
    }

}