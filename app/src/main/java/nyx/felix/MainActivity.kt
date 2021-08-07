package nyx.felix

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import nyx.felix.Status.*
import nyx.felix.screens.ErrorScreen
import nyx.felix.screens.LoadingScreen
import nyx.felix.ui.theme.FelixTheme
import nyx.felix.ui.theme.Teal200

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
                        Content(result.data as List<String>)
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

                        ErrorScreen("Loading $TAG failed.", msg)
                    }
                }


            }
        }
    }

    @Composable
    fun Content(texts: List<String>) {

        var page by remember {
            mutableStateOf(0)
        }

        Box(
            Modifier
                .fillMaxSize()
                .background(Black)
                .clickable {
                    if (page + 1 != texts.size) {
                        page++
                    } else {
                        this@MainActivity.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://t.me/nyx69")
                            )
                        )
                    }
                }
                .padding(16.dp), Alignment.Center) {


            LazyColumn {
                item {
                    Text(
                        texts[page],
                        color = Teal200,
                        fontFamily = FontFamily(Font(R.font.calibri))
                    )
                }


            }
        }


    }
}