package com.example.firestorecomposedemo.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.firestorecomposedemo.screens.MainScreen
import com.example.firestorecomposedemo.screens.MyViewModel
import com.example.firestorecomposedemo.ui.theme.FirestoreComposeDemoTheme

class MainActivity : ComponentActivity() {

    // Instantiate a viewModel object
    val myViewModel by viewModels<MyViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirestoreComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(myViewModel = myViewModel)
                }
            }
        }
    }
}
