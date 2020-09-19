package com.varma.hemanshu.composebasics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.varma.hemanshu.composebasics.ui.ComposeBasicsTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppThemeContainer {
                ScreenContent()
            }
        }
    }
}

@Composable
fun AppThemeContainer(content: @Composable () -> Unit) {
    ComposeBasicsTheme {
        Surface(color = Color.Yellow) {
            content()
        }
    }
}

@Composable
fun ScreenContent(names: List<String> = listOf("Android", "Kotlin", "Compose")) {
    //State Hoisting
    val counterState = remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxHeight()) {
        Column(modifier = Modifier.weight(1f)) {
            for (name in names) {
                Greeting(name = name)
                Divider(color = Color.Black)
            }
        }
        Counter(count = counterState.value,
                updateCount = { newCount ->
                    counterState.value = newCount
                }
        )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = name, modifier = Modifier.padding(24.dp))
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(onClick = { updateCount(count + 1) },
            backgroundColor = if (count > 5) Color.Green else Color.White
    ) {
        Text(text = "Clicked $count times")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeBasicsTheme {
        AppThemeContainer {
            ScreenContent()
        }
    }
}