package nyx.felix.data

import io.ktor.client.request.*
import nyx.felix.network.ktorHttpClient

object Repository {

    val client = ktorHttpClient

    suspend fun getText(): String = client.get("pxnx-rw.ddns.net:9090/text")
}