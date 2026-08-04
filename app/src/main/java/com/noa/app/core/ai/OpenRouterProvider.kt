package com.noa.app.core.ai

import com.noa.app.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenRouterProvider @Inject constructor() : AIProvider {

    private val client =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    override suspend fun generate(
        prompt: String
    ): AIResponse = withContext(Dispatchers.IO) {

        try {

            val messages = JSONArray()

            messages.put(

                JSONObject().apply {

                    put("role", "user")

                    put("content", prompt)

                }

            )

            val body = JSONObject().apply {

                put("model", BuildConfig.AI_MODEL)

                put("messages", messages)

            }

            val requestBody =
                body.toString()
                    .toRequestBody(
                        "application/json".toMediaType()
                    )

            val request =
                Request.Builder()
                    .url(BASE_URL)
                    .addHeader(
                        "Authorization",
                        "Bearer ${BuildConfig.AI_API_KEY}"
                    )
                    .addHeader(
                        "Content-Type",
                        "application/json"
                    )
                    .post(requestBody)
                    .build()

            client.newCall(request).execute().use { response ->

                val bodyString =
                    response.body?.string()

                android.util.Log.d(
                    "OpenRouterProvider",
                    "HTTP ${response.code} - Body: $bodyString"
                )

                if (bodyString == null) {

                    return@withContext AIResponse.Error(
                        "پاسخی از سرور دریافت نشد."
                    )

                }

                val json = JSONObject(bodyString)

                if (json.has("error")) {

                    val errorMessage =
                        json.optJSONObject("error")
                            ?.optString("message")
                            ?: "خطای نامشخص از سرویس هوش مصنوعی."

                    return@withContext AIResponse.Error(
                        errorMessage
                    )

                }

                if (!response.isSuccessful) {

                    return@withContext AIResponse.Error(
                        "خطا در ارتباط با سرویس هوش مصنوعی (${response.code})"
                    )

                }

                val content =
                    json
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")

                AIResponse.Success(content)

            }

        } catch (e: IOException) {

            android.util.Log.e(
                "OpenRouterProvider",
                "IOException",
                e
            )

            AIResponse.Error(
                "اتصال به اینترنت برقرار نشد."
            )

        } catch (e: Exception) {

            android.util.Log.e(
                "OpenRouterProvider",
                "Parse error",
                e
            )

            AIResponse.Error(
                "خطا در پردازش پاسخ سرویس هوش مصنوعی: ${e.message}"
            )

        }

    }

    companion object {

        private const val BASE_URL =
            "https://openrouter.ai/api/v1/chat/completions"

    }

}