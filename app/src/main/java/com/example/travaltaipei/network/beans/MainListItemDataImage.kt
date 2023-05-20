package com.example.travaltaipei.network.beans

import com.google.gson.annotations.SerializedName

class MainListItemDataImage {
    @SerializedName("src")
    var src : String? = null

    @SerializedName("subject")
    var subject : String? = null

    @SerializedName("ext")
    var ext : String? = null
}