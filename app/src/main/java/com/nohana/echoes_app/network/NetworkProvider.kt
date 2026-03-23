package com.nohana.echoes_app.network

import android.content.Context
import com.nohana.echoes_app.data.ServerStorage
import com.nohana.echoes_app.network.interceptor.JwtHeaderInterceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class NetworkProvider {

    companion object {
        fun getAddress(context: Context): String {
            return ServerStorage(context).getAddress()
        }

        const val ADDRESS = "http://192.168.15.77:8080/"

        // Inteceptor para mostrar log no sistema

        fun getRetrofitInstance(path: String, context: Context): Retrofit {

            val jwtInteceptor = JwtHeaderInterceptor(context)

            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val clientOkHttp = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()


            return Retrofit.Builder()
                .baseUrl(path)
                .client(clientOkHttp)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }


        fun getRetofitWithJwtInterceptor(path: String, context: Context): Retrofit {
            val jwtInteceptor = JwtHeaderInterceptor(context)

            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val clientOkHttp = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(jwtInteceptor)
                .build()


            return Retrofit.Builder()
                .baseUrl(path)
                .client(clientOkHttp)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
    }
}