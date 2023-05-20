package com.example.travaltaipei.network.beans

import com.google.gson.annotations.SerializedName

class ListItemData {
    @SerializedName("id")
    var id : Int = 0

    @SerializedName("name")
    var name : String? = null

    //introduction
    @SerializedName("introduction")
    var introduction : String? = null

    //open_time
    @SerializedName("open_time")
    var open_time : String? = null

    //official_site
    @SerializedName("official_site")
    var official_site : String? = null

    @SerializedName("images")
    var images : List<MainListItemDataImage>? = null



}