package com.example.coronacounter.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coronacounter.model.Authenticator
import com.example.coronacounter.model.Shop
import com.example.coronacounter.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.tensorflow.lite.examples.detection.coronaCounter.api.Api
import org.tensorflow.lite.examples.detection.coronaCounter.api.RetrofitInstance
import org.tensorflow.lite.examples.detection.coronaCounter.model.Trial

private const val TAG = "AppViewModel"

class AppViewModel:ViewModel(){
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _shops = MutableLiveData<List<Shop>>()
    val shops: LiveData<List<Shop>> get() = _shops

    private val _shop = MutableLiveData<Shop>()
    val shop: LiveData<Shop> get() = _shop

    private val _mainShop = MutableLiveData<Shop>()
    val mainShop: LiveData<Shop> get() = _mainShop

    private val _mainStage = MutableLiveData<Int>()
    val mainStage: LiveData<Int> get() = _mainStage


    val limitPeople: Int get() = _mainShop.value!!.limitPeople(_mainStage.value!!)
    val auth = Authenticator
//    val shopdata  =  Datas.shops

    private val Api = RetrofitInstance.instance.create(Api::class.java)


    // 유저를 세팅하고, 결과를 알려주는 함수
    suspend fun signin(user: User): Boolean {
        return withContext(Dispatchers.IO){
            val DBAccess = Api.authentication(user)
            if (DBAccess.isSuccessful){ // http code
                val DBuserInfo = DBAccess.body()!!
                if (DBuserInfo.id.equals("id invalid")){
                    false
                } else if (DBuserInfo.pw.equals("password wrong")){
                    false
                } else {
                    _user.postValue(DBuserInfo)
                    true
                }
            } else{     // network error
                Log.d(TAG,"network error")
                false
            }
        }
    }
    // 지역이름을 보내주면, 거리두기 단계를 리턴해주는 함수
    suspend fun getDistance(rname: String): Integer{
        return withContext(Dispatchers.IO){
            val DBAccess = Api.getDistance(rname)
            if (DBAccess.isSuccessful){
                DBAccess.body()!!
            }
            DBAccess.body()!!
        }
    }
    // id를 보내주면 , 새로운 유저일 떄 true를 리턴해주는 함수
    suspend fun isNewUser(id:String) : Boolean {
        return withContext(Dispatchers.IO){
            val DBAccess = Api.isIdValid(id)
            if (DBAccess.isSuccessful){ // http code
                val isValid = DBAccess.body()!!
                isValid
            } else{     // network error
                Log.d(TAG,"network error")
                false
            }
        }
    }
    // user를 보내면, db에 추가해준후 성공여부를 return하는 함수
    suspend fun addUser(user:User) : Boolean {
        return withContext(Dispatchers.IO){
            val DBAccess = Api.addUser(user)
            if (DBAccess.isSuccessful){ // http code
                val didSucceed = DBAccess.body()!!
                didSucceed
            } else{     // network error
                Log.d(TAG,"addUser network error")
                false
            }
        }
    }

    //샵 리스트를 업데이트 하는 함수
    suspend fun getStatistic(shop: Shop) : List<Trial> {
        Log.d(TAG,"give me statistic"+shop.toString())
        return withContext(Dispatchers.IO) {
            val DBAccess = Api.getStatistic(shop)
            if (DBAccess.isSuccessful){ // http code
                Log.d(TAG,DBAccess.body()!!.toString())
                DBAccess.body()!!
            } else{
                Log.d(TAG,"dbaccessfailed")
                listOf<Trial>()// network error
            }
        }
    }

    suspend fun fetchShops() {
        Log.d(TAG,"fetch shops")
        withContext(Dispatchers.IO) {
            //TODO 데이터베이스에 접속
            val DBAcess = Api.getShopLists(_user.value!!)
//            _shops.postValue(shopdata[user.value?.id] ?: listOf<Shop>())
            _shops.postValue(DBAcess.body())
            Log.d(TAG,DBAcess.body().toString())
        }
    }

    //메인 스테이지를 업데이트하는 함수
    suspend fun fetchStage() {
        Log.d(TAG,"fetch primary stage")
        withContext(Dispatchers.IO) {
            //TODO 데이터베이스에 접속
            val DBAcess = Api.getDistance(_mainShop.value!!.location!!)
//            _shops.postValue(shopdata[user.value?.id] ?: listOf<Shop>())
            _mainStage.postValue(DBAcess.body()!!.toInt())
            Log.d(TAG,DBAcess.body().toString())
        }
    }

    // 주상가 =  카운팅을 할 상가를 정함.
    fun setPrimaryShop(shop:Shop){
        _mainShop.value = shop
    }
    // shop을 보내면, db에 추가해준후 성공여부를 return하는 함수
    suspend fun addShop(shop:Shop) : Boolean {
        return withContext(Dispatchers.IO){
            val DBAccess = Api.addShop(shop)
            if (DBAccess.isSuccessful){ // http code
                val didSucceed = DBAccess.body()!!
                didSucceed
            } else{     // network error
                Log.d(TAG,"shop network error")
                false
            }
        }
    }

    // shop을 보내면, db에서 삭제해준 후 성공여부를 리턴
    suspend fun deleteShop(shop:Shop) : Boolean {
        return withContext(Dispatchers.IO){
            val DBAccess = Api.deleteShop(shop)
            if (DBAccess.isSuccessful){ // http code
                val didSucceed = DBAccess.body()!!
                didSucceed
            } else{     // network error
                Log.d(TAG,"delete shop network error")
                false
            }
        }
    }

    // sid와 shop을 보내면, db에서 업데이트해준 후 성공여부를 리턴
    suspend fun editShop(shopPair:Map<String,Shop>) : Boolean {
        return withContext(Dispatchers.IO){
            val DBAccess = Api.editShop(shopPair)
            if (DBAccess.isSuccessful){ // http code
                val didSucceed = DBAccess.body()!!
                didSucceed
            } else{     // network error
                Log.d(TAG,"edit shop network error")
                false
            }
        }
    }
}
