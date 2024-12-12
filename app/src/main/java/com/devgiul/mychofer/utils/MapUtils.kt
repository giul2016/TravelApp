package com.devgiul.mychofer.utils
import android.util.Log
import com.devgiul.mychofer.data.dto.Location
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object MapUtils {

    fun fetchRoute(
        origin: Location,
        destination: Location,
        apiKey: String,
        callback: (String) -> Unit
    ) {
        val url = "https://maps.googleapis.com/maps/api/directions/json" +
                "?origin=${origin.latitude},${origin.longitude}" +
                "&destination=${destination.latitude},${destination.longitude}" +
                "&key=$apiKey"
        Log.d("DirectionsAPI", "URL: $url")

        Thread {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonObject = JSONObject(response)
                val polyline = jsonObject.getJSONArray("routes")
                    .getJSONObject(0)
                    .getJSONObject("overview_polyline")
                    .getString("points")

                callback(polyline)
            } catch (e: Exception) {
                e.printStackTrace()
                callback("") // Retorna vazio em caso de erro
            }
        }.start()
    }

    fun generateStaticMapUrl(
        origin: Location,
        destination: Location,
        polyline: String,
        apiKey: String
    ): String {
        return "https://maps.googleapis.com/maps/api/staticmap" +
                "?size=600x400" +
                "&markers=color:red|label:A|${origin.latitude},${origin.longitude}" +
                "&markers=color:green|label:B|${destination.latitude},${destination.longitude}" +
                "&path=enc:${polyline}" +
                "&key=$apiKey"
    }
}
