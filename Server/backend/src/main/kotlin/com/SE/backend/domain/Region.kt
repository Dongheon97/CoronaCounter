package com.SE.backend.domain

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Data
import kotlin.properties.Delegates

// lombok을 이용해 getter와 setter 세팅
// https://sehajyang.github.io/etc/2019/03/07/kotlin-and-lombok.html/ - 참고

@Data
class Region {
    var rid by Delegates.notNull<Int>()
    lateinit var rname: String
    var stage: Int = 0
}


