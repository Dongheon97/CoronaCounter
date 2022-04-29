package com.SE.backend.domain

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Data
import kotlin.properties.Delegates

@Data
class Shop {
    @get: JsonProperty("sid")
    var sid: String = ""

    @get: JsonProperty("sname")
    var sname: String = ""

    @get: JsonProperty("rname")
    var rname: String = ""

    @get: JsonProperty("onum")
    var onum by Delegates.notNull<Int>()

    @get: JsonProperty("maxPeople")
    lateinit var maxPeople: Integer

    @get: JsonProperty("businessType")
    lateinit var businessType: BusinessType


}