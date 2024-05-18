package com.example.travaltaipei.network.beans

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class AllPageData {
    @SerializedName("total")
    var total : Int = 0

    @SerializedName("data")
    var data : List<ListItemData>? = null


}

