package com.SE.backend.domain

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Data
import kotlin.properties.Delegates

@Data
class Trial {
    @get: JsonProperty("tid")
    var tid by Delegates.notNull<Int>()

    @get: JsonProperty("tnum")
    var tnum by Delegates.notNull<Int>()

    @get: JsonProperty("sid")
    var sid by Delegates.notNull<Int>()
}