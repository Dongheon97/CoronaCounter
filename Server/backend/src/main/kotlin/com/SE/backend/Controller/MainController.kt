package com.SE.backend.Controller

import com.SE.backend.domain.*
import com.SE.backend.repository.ShowMapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import kotlin.Exception
import kotlin.jvm.Throws
import kotlin.properties.Delegates

@RestController
class MainController {

    @Autowired
    private lateinit var showMapper: ShowMapper

    @GetMapping("/regionList", produces = ["application/json"])
    @ResponseBody
    @Throws(Exception::class)
    fun getList(): List<Region>{
        return showMapper.getCityList()
    }

    @PostMapping("/distance-stage", produces = ["application/json"])
    @ResponseBody
    @Throws(Exception::class)
    fun getDistanceStage(@RequestBody regionName: String?): Integer{
        var stage = showMapper.getDistanceStage(regionName!!.replace("\"",""))
        if (stage != null){
            return stage
        } else{
            return Integer(0)
        }
    }


    @GetMapping("/userInfo", produces = ["application/json"])
    @ResponseBody
    @Throws(Exception::class)
    fun getUserList(): List<DBUser>{
        return showMapper.getUserList()
    }

    @PostMapping("/login", produces = ["application/json"])
    @Throws(Exception::class)
    fun getLogin(@RequestBody data: DBUser): DBUser {
        var validUser: DBUser = showMapper.getLogin(data.id)
        if (validUser != null){
            if (!data.pw.equals(validUser.pw)){  // user pw 일치하지 않을경우
                validUser.set(0,"","password wrong","")
            }
        } else {
            validUser = DBUser()
            validUser.set(0,"id invalid","","")
        }
        return validUser
    }

    @PostMapping("/add-user", produces = ["application/json"])
    @Throws(Exception::class)
    fun AddUser(@RequestBody data: DBUser): Boolean {
        showMapper.AddUser(data.id, data.pw, data.oname)
        val validUser = showMapper.getIdValid(data.id)
        if (validUser != null) {
            return true
        } else {
            return false
        }
    }

    @PostMapping("/idValid", produces = ["application/json"])
    @ResponseBody
    @Throws(Exception::class)
    fun getIdValid(@RequestBody id: String): Boolean{
        val DBAccess = showMapper.getIdValid(id.replace("\"",""))
        if (DBAccess != null ){
            return false
        } else
            return true
    }

    @PostMapping("/shopList", produces = ["application/json"])
    @ResponseBody
    @Throws(Exception::class)
    fun getShopList(@RequestBody user: DBUser?): List<Shop>{
        val shopList : List<Shop> = showMapper.getShopList(user?.id)
        return shopList
    }

    @PostMapping("/add-shop", produces = ["application/json"])
    @ResponseBody
    @Throws(Exception::class)
    fun AddShop(@RequestBody shop: Shop?): Boolean{
        val enumNum = shop!!.businessType.priority;
        try {
            showMapper.AddShop(shop, enumNum)
        }catch (e: Exception){
            println(e.toString())
            return false
        }
        return true
    }

    @PostMapping("/delete-shop", produces = ["application/json"])
    @ResponseBody
    @Throws(Exception::class)
    fun DeleteShop(@RequestBody shop: Shop?): Boolean{
        try {
            showMapper.DeleteShop(shop!!.sname)
        }catch (e: Exception){
            println(e.toString())
            return false
        }
        return true
    }

    @PostMapping("/edit-shop", produces = ["application/json"])
    @ResponseBody
    @Throws(Exception::class)
    fun EditShop(@RequestBody shopInfo: Map<String, Shop>): Boolean{
        try {
            val shop: Shop = shopInfo.values.first()    // shop sid
            val enumNum = shop.businessType.priority;
            showMapper.EditShop(shop, enumNum, shopInfo.keys.first())
        }catch (e: Exception){
            println(e.toString())
            return false
        }
        return true
    }

    @PostMapping("/statistic", produces = ["application/json"])
    @ResponseBody
    @Throws(Exception::class)
    fun getStatistic(@RequestBody shop: Shop): List<Trial>{
        val trialInfo = showMapper.getStatistic(shop.sid)
        return trialInfo
    }

    @PostMapping("/addCountInfo", produces = ["application/json"])
    @ResponseBody
    @Throws(Exception::class)
    fun AddCountInfo(@RequestBody trial: Trial): Boolean {
        var returnBoolean by Delegates.notNull<Boolean>()
        try {
            val AddCountNumber = showMapper.AddCountNumber(trial)
            if (AddCountNumber != null){
                returnBoolean = true
            } else
                returnBoolean = false
        } catch (e: Exception){
            println(e.toString())
        }
        return returnBoolean
    }

}