package nyx.felix

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nyx.felix.data.Repository

class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"
    val result = MutableStateFlow<Status<Any>>(Status.Loading())

    init {
        fetchContent()
    }

    private fun fetchContent() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                result.value = Status.Success(Repository.getText())
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                result.value = Status.Failure(e)
            }
        }
    }
}