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
import androidx.compose.ui.Layout
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
fun CustomListContent(modifier: Modifier = Modifier) {
    CustomColumn(modifier = modifier.padding(8.dp)) {
        Text("Column 1")
        Text("Column 2")
        Text("Column 3")
        Text("Column 4")
    }

}

//Custom layout composable for rendering single item
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

//Custom layout composable for rendering list of items
@Composable
fun CustomColumn(
    modifier: Modifier = Modifier,
    children: @Composable () -> Unit
) {
    Layout(modifier = modifier, children = children) { measurables, constraints ->

        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints = constraints)
        }

        // Track the y co-ord we have placed children up to
        var yPosition = 0

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)

                yPosition += placeable.height
            }
        }
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