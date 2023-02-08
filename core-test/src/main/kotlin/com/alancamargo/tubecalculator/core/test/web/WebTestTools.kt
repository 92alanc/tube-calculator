package com.alancamargo.tubecalculator.core.test.web

import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.net.HttpURLConnection

val mockWebServer = MockWebServer()

fun mockWebResponse(jsonAssetPath: String, code: Int = HttpURLConnection.HTTP_OK) {
    val json = getJsonFromAssets(jsonAssetPath)
    val response = MockResponse().setBody(json).setResponseCode(code)
    mockWebServer.enqueue(response)
}

private fun getJsonFromAssets(path: String): String {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val assetManager = context.assets

    val inputStream = assetManager.open(path)
    return inputStream.bufferedReader().use { it.readText() }
}
