package id.indocyber.api_service.retrofit

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import id.indocyber.common.base.Constants
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    fun getClient(context: Context): Retrofit {
        val client = OkHttpClient().newBuilder().connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(Interceptor {
                try {
                    it.proceed(it.request())
                } catch (e: Exception) {
                    Response.Builder()
                        .request(it.request()).message(e.message ?: "").protocol(Protocol.HTTP_1_1)
                        .code(0).body(
                            with(JsonObject()) {
                                addProperty("error", e.message)
                            }.toString().toResponseBody(
                                "application/json"
                                    .toMediaType()
                            )
                        ).build()
                }
            }).build()

        return Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }
}