package com.nohana.echoes_app.network.interceptor

import android.content.Context
import androidx.compose.runtime.collectAsState
import com.nohana.echoes_app.data.TokenStorage
import com.nohana.echoes_app.data.dataStorage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class JwtHeaderInterceptor(
    private val context: Context
): Interceptor {

    private val tokenStorage = TokenStorage(context.applicationContext)
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val token = tokenStorage.getToken()

        val request = if (token.isNotEmpty()) {
            chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }
        chain.proceed(request)
    }

}