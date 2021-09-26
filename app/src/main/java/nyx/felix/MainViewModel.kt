package nyx.felix

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import nyx.felix.data.Repository

class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"

    suspend fun fetchContent() = try {
        delay(2_000L)
        Repository.getText()
    } catch (e: Exception) {
        Log.e(TAG, e.message!!)
        e
    }
}