package com.alancamargo.tubecalculator.core.test.web

import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import java.net.HttpURLConnection

val mockWebServer = MockWebServer()

fun mockWebResponse(jsonAssetPath: String, code: Int = HttpURLConnection.HTTP_OK) {
    val json = getJsonFromAssets(jsonAssetPath)
    val response = MockResponse().setBody(json).setResponseCode(code)
    mockWebServer.enqueue(response)
}

fun mockWebError(code: Int) {
    val response = MockResponse().setResponseCode(code)
    mockWebServer.enqueue(response)
}

fun delayWebResponse() {
    val response = MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE)
    mockWebServer.enqueue(response)
}

fun disconnect() {
    val response = MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
    mockWebServer.enqueue(response)
}

private fun getJsonFromAssets(path: String): String {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val assetManager = context.assets

    val inputStream = assetManager.open(path)
    return inputStream.bufferedReader().use { it.readText() }
}
