package com.SE.backend.repository

import com.SE.backend.domain.*
import org.apache.ibatis.annotations.*

@Mapper
interface ShowMapper {
    fun getCityList(): List<Region>
    fun getUserList(): List<DBUser>
    fun getLogin(@Param("id") id: String?): DBUser
    fun getDistanceStage(@Param("rname") rname: String?): Integer
    fun getShopList(@Param("id") id: String?): List<Shop>
    fun getIdValid(@Param("id") id: String?): DBUser
    fun AddUser(@Param("id") id: String?, @Param("pw") pw: String?,
    @Param("oname") oname: String?)
    fun AddShop(@Param("shop") shop: Shop?, @Param("BNP") businessProperty: Int?)
    fun DeleteShop(@Param("sname") shopName: String?)
    fun EditShop(@Param("shop") shop: Shop?,
    @Param("BNP") businessProperty: Int?, @Param("oldSid") ShopId: String)
    fun getStatistic(@Param("sid") shopSid: String): List<Trial>
    fun AddCountNumber(@Param("trial") trial: Trial)

}
