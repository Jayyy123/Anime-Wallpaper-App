package com.example.animeimages

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus{
    IDLE,FAILEDOREMPTY,INVALID,NOT_INITIALISED,PERMISSIONS_ERROR,UNKNOWN,OK
}
class GetRawData(private val listener: DownloadComplete ): AsyncTask<String,Void,String>() {
    private val TAG = ""
    private var downloadStatus = DownloadStatus.IDLE


    interface DownloadComplete{
        fun onDownloadComplete(data:String,downloadStatus: DownloadStatus)
    }

    override fun doInBackground(vararg params: String?): String {
        if (params[0] == null){
            downloadStatus = DownloadStatus.NOT_INITIALISED
            Log.e(TAG, "No url specified")
        }

        try {
            downloadStatus = DownloadStatus.OK
            return URL(params[0]).readText()
        }catch (e:Exception){
            val errorMessage:String = when(e){
                is MalformedURLException -> {
                    downloadStatus = DownloadStatus.INVALID
                    "Malformed url ${e.message}"
                }
                is IOException -> {
                    downloadStatus =DownloadStatus.FAILEDOREMPTY
                    "IO Exception ${e.message}"
                }
                is SecurityException -> {
                    downloadStatus = DownloadStatus.PERMISSIONS_ERROR
                    "Security Exception ${e.message}"
                }
                else ->{
                    downloadStatus = DownloadStatus.UNKNOWN
                    "Unknown Error ${e.message}"
                }
            }
            Log.e(TAG,errorMessage)
            return errorMessage

        }
    }

    override fun onPostExecute(result: String) {
        Log.d(TAG,"result is $result")
        listener.onDownloadComplete(result,downloadStatus)
    }
}