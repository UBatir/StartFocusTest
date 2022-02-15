package uz.example.focusstart.di

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.example.focusstart.BuildConfig.BASE_URL
import uz.example.focusstart.data.domain.MainRepository
import uz.example.focusstart.data.remote.ApiInterface
import uz.example.focusstart.ui.MainAdapter
import uz.example.focusstart.ui.MainViewModel
import uz.example.focusstart.utils.addLoggingInterceptor
import java.util.concurrent.TimeUnit

private const val timeOut = 50L

val networkModule = module {
    single {
        GsonBuilder().setLenient().create()
    }



    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addLoggingInterceptor()
            .connectTimeout(timeout = timeOut, TimeUnit.SECONDS)
            .readTimeout(timeout = timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeout = timeOut, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .build()

    }



    single {
        get<Retrofit>().create(ApiInterface::class.java)
    }

}

val repositoryModule = module {
    single { MainRepository(get(),get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

val adapterModule = module {
    single { MainAdapter() }
}
