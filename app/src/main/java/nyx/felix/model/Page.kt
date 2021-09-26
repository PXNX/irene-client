package nyx.felix.model

import kotlinx.serialization.Serializable

@Serializable
data class Page(val text: String, val imageUrl: String? = null)
