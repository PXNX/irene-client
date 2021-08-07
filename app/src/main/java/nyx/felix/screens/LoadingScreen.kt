package nyx.felix.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nyx.felix.R

@Composable
fun LoadingScreen() {
    val infoTexts = listOf(
        "I like you.", "How is it?", "Tap me!", "You deserve it <3", "Sei meraviglioso!", "voglio bene <3"
    )
    val infoText = remember { mutableStateOf(infoTexts[randomNumber(infoTexts.size)]) }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(32.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally,
    ) {

        Image(painterResource( R.drawable.appicon), "Loading Image",Modifier.size(50.dp))

        Row(Modifier
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(6.dp))
            .clickable { infoText.value = infoTexts[randomNumber(infoTexts.size)] }
            .padding(4.dp),){
            CircularProgressIndicator(Modifier.size(14.dp), strokeWidth = 2.dp)

            Text(
                infoText.value,
                Modifier.padding(start = 8.dp),
                White,
                16.sp
            )
        }


    }
}

private fun randomNumber(length: Int) =  (Math.random() * length).toInt()
