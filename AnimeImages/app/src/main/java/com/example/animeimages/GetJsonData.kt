package com.example.animeimages

import android.os.AsyncTask
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class GetJsonData(private val listener:OnData): AsyncTask<String,Void,ArrayList<AnimePhotos>>() {
    private val TAG = ""

    interface OnData{
        fun dataAvailable(data:List<AnimePhotos>)
        fun error(error:Exception)
    }

    override fun doInBackground(vararg params: String?): ArrayList<AnimePhotos> {

        val photoList = ArrayList<AnimePhotos>()

        try {
            val jsonData = JSONObject(params[0])
            val jsonArray = jsonData.getJSONArray("items")
            for (i in 0 until jsonArray.length()){
                val item = jsonArray.getJSONObject(i)
                val title = item.getString("title")
                val author = item.getString("author")
                val authorID = item.getString("author_id")
                val tags = item.getString("tags")

                val media = item.getJSONObject("media")
                val m = media.getString("m")
                val link = m.replaceFirst("_m.jpg","_b.jpg")
                val photoObject = AnimePhotos(title,author,authorID,m,link,tags)
                photoList.add(photoObject)
            }

        }catch (e:JSONException){
            e.printStackTrace()
            Log.e(TAG,"${e.message}")
            listener.error(e)
            cancel(true)
        }
        Log.d(TAG,"doInBackground ends")
        return photoList


    }

    override fun onPostExecute(result: ArrayList<AnimePhotos>) {
        listener.dataAvailable(result)
        Log.d(TAG,"onPostExecute ends.\nresult is $result")
    }
}