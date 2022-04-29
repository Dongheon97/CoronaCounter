package com.SE.backend.domain

import lombok.Data
import kotlin.properties.Delegates

@Data
class DBUser{
    var onum by Delegates.notNull<Int>()
    lateinit var id: String
    lateinit var pw: String
    lateinit var oname: String

    fun set(_onum: Int,_id:String, _pw:String, _oname: String){
        this.onum = _onum
        this.id = _id
        this.pw = _pw
        this.oname = _oname
    }
}
