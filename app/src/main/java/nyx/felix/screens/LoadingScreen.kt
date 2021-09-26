package nyx.felix.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nyx.felix.R

@Composable
fun LoadingScreen() {
    val infoTexts = listOf(
        "Tap me!",
        "I like you.",
        "You. Are. Awesome.",
        "You're wholesome and kind. Just an incredible human being...",
        "How is it?",
        "You deserve it <3",
        "Sei meraviglioso!",
        "Ti voglio bene \uD83D\uDE0A",
        "Your own app!",
        "Do you like this?",
        "You are cute af \uD83D\uDE0A",
        "Hug? :3",
        "More Lasagna?",
        "The Gnocchi goddess!"
    )

    val infoText = remember { mutableStateOf(infoTexts[0]) }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .clickable { infoText.value = infoTexts[randomNumber(infoTexts.size)] }
            .padding(32.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally,
    ) {

        Image(painterResource(R.drawable.appicon), "Loading Image", Modifier.size(50.dp))

        Text(
            infoText.value,
            Modifier.padding(16.dp),
            White,
            16.sp
        )

        CircularProgressIndicator(
            Modifier
                .size(14.dp)
                .padding(16.dp), strokeWidth = 2.dp
        )
    }
}

private fun randomNumber(length: Int) = (Math.random() * length).toInt()
