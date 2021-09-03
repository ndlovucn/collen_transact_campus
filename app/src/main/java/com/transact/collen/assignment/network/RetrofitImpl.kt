package com.mywallst.collen.assignment.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.transact.collen.assignment.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class RetrofitImpl : IAuthRetrofit {

    lateinit var retrofit: Retrofit

    init {
        setupRetrofit()
    }

    override fun <T> create(api: Class<T>): T {
        return retrofit.create(api)
    }


    override fun setupRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    override fun client(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        when {
            BuildConfig.DEBUG -> logging.level = HttpLoggingInterceptor.Level.BODY
            else -> logging.level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(RequestInterceptor())
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
    }
}

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val stringUrl = request.url().toString()
        val newRequest = Request.Builder().url(stringUrl).build()
        return chain.proceed(newRequest)

    }


}

@Suppress("Unused")
object NullToEmptyStringAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}