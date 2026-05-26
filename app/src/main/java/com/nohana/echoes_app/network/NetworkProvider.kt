package com.nohana.echoes_app.network

import android.content.Context
import com.nohana.echoes_app.network.interceptor.JwtHeaderInterceptor
import okhttp3.CertificatePinner
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.URL

object NetworkProvider {

    private val HOSTS = setOf(
        "echoes.avraham.dev.br",
        "10.131.109.139"
    )

    fun buildSecureClient(urlString: String): OkHttpClient {
        val url = URL(urlString)
        val host = url.host

        if (!HOSTS.contains(host)) {
            throw SecurityException("Host desconhecido. $host")
        }

        val certificatePinner = CertificatePinner.Builder()
            .add("echoes.avraham.dev.br", "sha256/N77K0gJYjPYv8mqO0+xuQy571mH/SRPqFBXD5nnsonQ=")
            .add("echoes.avraham.dev.br", "sha256/iFvwVyJSxnQdyaUvUERIf+8qk7gRze3612JMwoO3zdU=")
            .build()

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
             .certificatePinner(certificatePinner)
             .hostnameVerifier { host, session ->
                 HOSTS.contains(host)
             }
            .build()
    }

    fun buildSecureClientWithJwt(urlString: String, context: Context): OkHttpClient {
        val url = URL(urlString)
        val host = url.host

        if (!HOSTS.contains(host)) {
            throw SecurityException("Host desconhecido. $host")
        }

        val certificatePinner = CertificatePinner.Builder()
            .add("echoes.avraham.dev.br", "sha256/N77K0gJYjPYv8mqO0+xuQy571mH/SRPqFBXD5nnsonQ=")
            .add("echoes.avraham.dev.br", "sha256/iFvwVyJSxnQdyaUvUERIf+8qk7gRze3612JMwoO3zdU=")
            .build()

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val jwtInteceptor = JwtHeaderInterceptor(context)


        return OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .hostnameVerifier { host, session ->
                HOSTS.contains(host)
            }
            .addInterceptor(jwtInteceptor)
            .build()
    }

    fun getAddress(): String {
        return "http://10.131.109.139:8080/"
        // return "https://echoes.avraham.dev.br/"
    }

    fun getRetrofitInstance(path: String, context: Context): Retrofit {


        val clientOkHttp = buildSecureClient(path)

        return Retrofit.Builder()
            .baseUrl(path)
            .client(clientOkHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }


    fun getRetofitWithJwtInterceptor(path: String, context: Context): Retrofit {
        val jwtInteceptor = JwtHeaderInterceptor(context)

        val clientOkHttp = buildSecureClientWithJwt(path, context)

        return Retrofit.Builder()
            .baseUrl(path)
            .client(clientOkHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }
}
