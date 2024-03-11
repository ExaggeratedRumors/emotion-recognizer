package com.vrtools.sound_recognizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vrtools.sound_recognizer.theme.SoundRecognizerTheme
import com.vrtools.sound_recognizer.utils.PERMISSIONS.REQUIRED_PERMISSIONS
import com.vrtools.sound_recognizer.view.MainView

class MainActivity : ComponentActivity() {
    override fun onStart() {
        super.onStart()
        val permissionsGained = REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                this, it
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        }
        if(!permissionsGained)
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoundRecognizerTheme {
                MainView()
            }
        }
    }
}