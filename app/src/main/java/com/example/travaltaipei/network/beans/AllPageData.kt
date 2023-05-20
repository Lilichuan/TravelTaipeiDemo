package com.example.travaltaipei.network.beans

import com.google.gson.annotations.SerializedName

class AllPageData {
    @SerializedName("total")
    var total : Int = 0

    @SerializedName("data")
    var data : List<ListItemData>? = null


}

