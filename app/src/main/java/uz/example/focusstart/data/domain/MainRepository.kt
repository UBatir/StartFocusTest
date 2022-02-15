package uz.example.focusstart.data.domain

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.example.focusstart.data.ErrorMessage
import uz.example.focusstart.data.model.Currency
import uz.example.focusstart.data.model.Data
import uz.example.focusstart.data.model.Valute
import uz.example.focusstart.data.remote.ApiInterface

class MainRepository(private val api: ApiInterface,private val gson:Gson) {

    fun getCurrency(): Flow<Result<Data>> = flow{
        val response=api.getCurrencyRate()
        if (response.isSuccessful){
            emit(Result.success(response.body()!!))
        }
        else{
            var st = "Error in repository"
            response.errorBody()?.let {
                st = gson.fromJson(it.string(), ErrorMessage::class.java).message
            }
            emit(Result.failure<Data>(Throwable(st)))
        }
    }.flowOn(Dispatchers.IO)
}