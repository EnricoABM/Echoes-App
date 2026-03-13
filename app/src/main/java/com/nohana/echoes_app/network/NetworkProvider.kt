package com.nohana.echoes_app.network

import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class NetworkProvider {

    companion object {

        // Inteceptor para mostrar log no sistema
        private val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Cliente OkHttp
        private val clientOkHttp = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // Instância do Retrofit
        fun getRetrofitInstance(path: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(path)
                .client(clientOkHttp)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
    }
}