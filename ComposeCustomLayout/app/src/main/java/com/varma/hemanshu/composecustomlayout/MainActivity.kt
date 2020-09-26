package com.varma.hemanshu.composecustomlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.FirstBaseline
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.AlignmentLine
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.varma.hemanshu.composecustomlayout.ui.ComposeCustomLayoutTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCustomLayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                    //  or
//                    TextWithNormalPaddingPreview()
                    // or
//                    TextWithPaddingToBaselinePreview()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun Modifier.firstBaselineToTop(firstBaselineToTop: Dp) =
    Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        //Check, if the composable has a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseLine = placeable[FirstBaseline]

        //Height of the composable with padding - first baseline
        val placeableY = firstBaselineToTop.toIntPx() - firstBaseLine
        val height = placeable.height + placeableY

        layout(placeable.width, height) {
            //Where the composable gets placed
            placeable.placeRelative(0, placeableY)
        }
    }

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeCustomLayoutTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun TextWithPaddingToBaselinePreview() {
    Text("Hello Compose!", Modifier.firstBaselineToTop(firstBaselineToTop = 32.dp))
}

@Preview(showBackground = true)
@Composable
fun TextWithNormalPaddingPreview() {
    Text("Hello Compose", Modifier.padding(top = 32.dp))
}