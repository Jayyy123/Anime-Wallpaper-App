package com.example.animeimages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class recyclerAdapterViewHolder(view: View): RecyclerView.ViewHolder(view){

    var thumbnail = view.findViewById<ImageView>(R.id.thumbnail)
    var title = view.findViewById<TextView>(R.id.title1)

}
class recyclerAdapter(private var photoList:List<AnimePhotos>): RecyclerView.Adapter<recyclerAdapterViewHolder>() {


    private val TAG = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerAdapterViewHolder {
        val v =  LayoutInflater.from(parent.context).inflate(R.layout.browse,parent,false)
        return recyclerAdapterViewHolder(v)

    }

    override fun onBindViewHolder(holder: recyclerAdapterViewHolder, position: Int) {
        val photoItem = photoList[position]
        Picasso.with(holder.thumbnail.context).load(photoItem.images)
            .error(R.drawable.jay)
            .placeholder(R.drawable.jay)
            .into(holder.thumbnail)
        holder.title.text = photoItem.title
    }

    override fun getItemCount(): Int {
        return if (photoList.isNotEmpty()) photoList.size else 0
    }

    fun loadNewData(newPhotos: List<AnimePhotos>){
        photoList = newPhotos
        notifyDataSetChanged()
    }
    fun getData(position: Int): AnimePhotos?{
        return if (photoList.isNotEmpty()) photoList[position] else null
    }


}