package com.july.wikipedia.providers

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ServiceGenerator {
    private val BASE_URL = "https://en.wikipedia.org/w/api.php"


    private val builder: Retrofit.Builder =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())

    private var retrofit = builder.build()

    private val httpClient = OkHttpClient.Builder()

//    private val logging: HttpLoggingInterceptor =
//        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    fun <S> createService(serviceClass: Class<S>?): S {
//        if (!httpClient.interceptors().contains(logging)) {
//            httpClient.addInterceptor(logging)
//            builder.client(httpClient.build())
//            retrofit = builder.build()
//        }
        return retrofit.create(serviceClass)
    }
//
//    fun <S> createService(serviceClass: Class<S>?, token: String?): S {
//        if (token != null) {
//            httpClient.interceptors().clear()
//            httpClient.addInterceptor { chain ->
//                val original: Request = chain.request()
//                val builder: Request.Builder = original.newBuilder().header("Authorization", token)
//                val request: Request = builder.build()
//                chain.proceed(request)
//            }
//            builder.client(httpClient.build())
//            retrofit = builder.build()
//        }
//        return retrofit.create(serviceClass)
//    }

}