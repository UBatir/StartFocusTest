package uz.example.focusstart.utils

import android.util.Log
import androidx.viewbinding.ViewBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import uz.example.focusstart.BuildConfig

fun OkHttpClient.Builder.addLoggingInterceptor(): OkHttpClient.Builder {
    HttpLoggingInterceptor.Level.HEADERS
    val logging = HttpLoggingInterceptor.Logger { message -> Log.d("HTTP",message) }
    if (BuildConfig.LOGGING) {
        addInterceptor(HttpLoggingInterceptor(logging))
    }
    return this
}

fun <T : ViewBinding> T.scope(block: T.() -> Unit) {
    block(this)
}