package uz.example.focusstart.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import uz.example.focusstart.data.model.Currency
import uz.example.focusstart.data.model.Data
import uz.example.focusstart.data.model.Valute

interface ApiInterface {

    @GET("daily_json.js")
    suspend fun getCurrencyRate():Response<Data>
}