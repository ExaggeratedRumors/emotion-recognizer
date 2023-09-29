package com.vrtools.sound_recognizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.vrtools.sound_recognizer.ui.theme.SoundrecognizerTheme
import com.vrtools.sound_recognizer.ui.view.LegacyGraphView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoundrecognizerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                    AndroidView (
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            LegacyGraphView(context)
                        }
                    ) {
                        it.initView()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SoundrecognizerTheme {
        Greeting("Android")
    }
}

/*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practice.voice_recognizer.model.*
import com.practice.voice_recognizer.view.ViewContainer
import com.practice.voice_recognizer.utils.permissionCheck

class MainActivity : AppCompatActivity() {
    private lateinit var view: ViewContainer
    private lateinit var routine: RecordingRoutine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionCheck(this)
        setContentView(R.layout.activity_main)
        view = ViewContainer(this)
        routine = RecordingRoutine(this, view)
    }

    override fun onStart() {
        super.onStart()
        routine.start()
    }

    override fun onPause() {
        super.onPause()
        routine.stop()
    }

    override fun onResume() {
        super.onResume()
        routine.release()
    }

    override fun onStop() {
        super.onStop()
        routine.stop()
    }
}
 */