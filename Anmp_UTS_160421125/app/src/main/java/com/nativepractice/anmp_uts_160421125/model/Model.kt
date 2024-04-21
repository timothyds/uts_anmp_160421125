package com.nativepractice.anmp_uts_160421125.model

import com.google.gson.annotations.SerializedName

data class Hobby(
    var id:Int?,
    @SerializedName("user")
    var username:String?,
    var title:String?,
    var desc:String?,
    var detail:String?,
    var photoUrl:String?
)