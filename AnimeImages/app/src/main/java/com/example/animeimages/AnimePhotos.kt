package com.example.animeimages

class AnimePhotos(var title:String, var author:String, var authorID:String, var images:String,var link:String, var tags:String){
    override fun toString(): String {
        return "AnimePhotos(title='$title', author='$author', authorID='$authorID', images='$images', link='$link', tags='$tags')"
    }
}