package com.example.akash.rxkotlinsample

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

object ApiClient {
  private val REQUEST_TIME_OUT : Long= 60
  private lateinit var okHttpClient: OkHttpClient
  private val BASE_URL= "https://demo7936314.mockable.io/"

  val client: Retrofit
    get() {
        initOkHttp();

      return Retrofit.Builder()
          .baseUrl(BASE_URL)
          .client(okHttpClient)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build()
    }

  fun initOkHttp(){
      val builder= OkHttpClient.Builder()
          .connectTimeout(REQUEST_TIME_OUT, SECONDS)
          .readTimeout(REQUEST_TIME_OUT, SECONDS)
          .writeTimeout(REQUEST_TIME_OUT, SECONDS);

    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    builder.addInterceptor(httpLoggingInterceptor)
    builder.addInterceptor { chain ->
      val originalRequest = chain.request()
      val requestBuilder = originalRequest.newBuilder()
      requestBuilder.addHeader("Accept", "application/json")
          .addHeader("Request-Type", "Android")
          .addHeader("Content-Type", "application/json")
      chain.proceed(requestBuilder.build())
    }
    okHttpClient = builder.build()
  }
}