package com.example.mydemo.main.core.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mydemo.main.core.compose.ui.theme.MyDemoTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting()

        }
    }
}

@Composable
fun Greeting() {
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .fillMaxHeight()
//            .width(600.dp)
//            .requiredWidth(300.dp)
            .background(Color.Green),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = "Hello!", modifier = Modifier.offset(0.dp, 0.dp))
        Text(text = "world!")
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Hello!")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Greeting()
}