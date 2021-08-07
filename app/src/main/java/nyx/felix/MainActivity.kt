package nyx.felix

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import nyx.felix.Status.*
import nyx.felix.screens.ErrorScreen
import nyx.felix.screens.LoadingScreen
import nyx.felix.ui.theme.FelixTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FelixTheme {
                when (val result = viewModel.result.collectAsState().value) {
                    is Loading -> {
                        Log.w(TAG, "Loading")
                        LoadingScreen()
                    }

                    is Success -> {
                        Log.w(TAG, "Success")
                        Content(result.data as String)
                    }

                    is Failure -> {
                        val e = result.exception as Exception

                        Log.e(
                            TAG,
                            "Loading $TAG failed: ${e.message}\n\n--- Stacktrace: ${
                                Log.getStackTraceString(e)
                            }"
                        )

                        val msg = e.message!!

                        ErrorScreen("Loading $TAG failed.", msg,)
                    }
                }


            }
        }
    }

    @Composable
    fun Content(text:String) {
        LazyColumn(Modifier.fillMaxSize().padding(20.dp)){
            item{
                Text(text, color=White)
            }
        }
    }
}