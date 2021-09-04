package nyx.felix

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nyx.felix.data.Repository
import java.time.LocalDate

class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"
    val result = MutableStateFlow<Status<Any>>(Status.Loading())

 fun fetchContent() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(4000L)
                result.value = Status.Success(Repository.getTextLocally())
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                result.value = Status.Failure(e)
            }
        }
    }
}