package nyx.felix

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import com.onesignal.OneSignal
import kotlinx.coroutines.DelicateCoroutinesApi
import nyx.felix.model.Page
import nyx.felix.screens.ErrorScreen
import nyx.felix.screens.LoadableView
import nyx.felix.ui.theme.FelixTheme
import nyx.felix.ui.theme.Teal200

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val TAG = "Main"

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ONESIGNAL_APP_ID = "e20b3f67-4952-4480-bed0-38b41e7d5755"

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        setContent {
            FelixTheme {
                LoadableView {


                    val result by loadAsync {
                        viewModel.fetchContent()
                    }

                    whenReady {
                        when (result) {
                            is Exception -> ErrorScreen(
                                errorTitle = "Loading failed.",
                                errorMessage = (result as Exception).message!!
                            )

                            is List<*> -> Content(result as List<Page>)
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoilApi::class)
    @Composable
    fun Content(pages: List<Page>) {

        var currentPageIndex by remember { mutableStateOf(0) }

        Box(
            Modifier
                .fillMaxSize()
                .background(Black)
                .clickable {
                    if (currentPageIndex + 1 != pages.size) {
                        currentPageIndex++
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


            LazyColumn(Modifier.fillMaxWidth()) {
                pages[currentPageIndex].imageUrl?.let { imageUrl ->
                    item {
                        Image(
                            painter = rememberImagePainter(
                                data = imageUrl,
                                imageLoader = LocalImageLoader.current,
                                builder = {
                                    crossfade(true)
                                }
                            ), contentDescription = null,

                            Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .padding(bottom = 24.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                item {
                    Text(
                        pages[currentPageIndex].text,
                        color = Teal200,
                        fontFamily = FontFamily(Font(R.font.calibri))
                    )
                }
            }
        }

        val enabled = true

        val backCallback = remember {
            object : OnBackPressedCallback(enabled) {
                override fun handleOnBackPressed() {
                    if (currentPageIndex > 0) {
                        currentPageIndex--
                    }
                }
            }
        }

        val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
            "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
        }.onBackPressedDispatcher
        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(lifecycleOwner, backDispatcher) {
            backDispatcher.addCallback(lifecycleOwner, backCallback)
            onDispose {
                backCallback.remove()
            }
        }
    }
}