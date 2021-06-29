package com.example.ssolrangapplication.common

import android.app.Application
import android.content.Context
import com.example.ssolrangapplication.BuildConfig.SERVER_HTTP_URL
import com.example.ssolrangapplication.BuildConfig
import com.example.ssolrangapplication.common.utils.ImageLoader
import com.example.ssolrangapplication.network.AddCookieInterceptor
import com.example.ssolrangapplication.network.NetworkRepository
import com.example.ssolrangapplication.network.NetworkService
import com.example.ssolrangapplication.network.ReceivedCookieInterceptor
import com.example.ssolrangapplication.ui.home.HomeViewModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SsolRangApplication: Application() {
    companion object{
        var mContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this

        startKoin {
            androidLogger()
            androidContext(this@SsolRangApplication)
            modules(appModules)
        }
    }

    private val appModules = module {
        single<NetworkService> {
            Retrofit.Builder().baseUrl(BuildConfig.SERVER_HTTP_URL).client(OkHttpClient.Builder().apply {
                connectTimeout(30, TimeUnit.SECONDS)
                writeTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)
                addInterceptor(AddCookieInterceptor())
                addInterceptor(ReceivedCookieInterceptor())
                /**
                 * 200309
                 * addInterceptor 을 수정함.
                 * okhttp 4.3.1 버전으로 수정되면서 아래 내용을 수정했다.
                 * 기존 내용은 주석처리함.
                 */
                /**
                 * 200309
                 * addInterceptor 을 수정함.
                 * okhttp 4.3.1 버전으로 수정되면서 아래 내용을 수정했다.
                 * 기존 내용은 주석처리함.
                 */
                addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        if (!message.startsWith("{") && !message.startsWith("[")) {
                            Timber.tag("OkHttp").d(message)
                            return
                        }
                        try {
                            Timber.tag("OkHttp").d(
                                GsonBuilder().setPrettyPrinting().create().toJson(
                                JsonParser().parse(message)))
                        } catch (m: JsonSyntaxException) {
                            Timber.tag("OkHttp").d(message)
                        }
                    }

                }).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }.build()).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(
                GsonConverterFactory.create()).build().create()
        }

        single {
            NetworkRepository(get())
        }

        single {
            ImageLoader(get())
        }

//        single { MainViewModel(get()) } // 싱글톤 뷰모델 생성.
//        viewModel { MainViewModel(get()) }
        viewModel { HomeViewModel() }
//        viewModel { ShareViewModel(get()) }
//        viewModel { GalleryViewModel(get()) }
    }
}