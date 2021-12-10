package org.tensorflow.lite.examples.detection.coronaCounter.model

import com.google.gson.annotations.SerializedName

data class Trial (
    @SerializedName("tid")
    var tid : Int,

    @SerializedName("tnum")
    var tnum : Int,

    @SerializedName("sid")
    var sid : Int
)