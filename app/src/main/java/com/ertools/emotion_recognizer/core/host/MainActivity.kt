package com.ertools.emotion_recognizer.core.host

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ertools.emotion_recognizer.presentation.theme.Theme
import com.ertools.emotion_recognizer.presentation.ui.MainView
import com.ertools.emotion_recognizer.utils.PERMISSIONS.REQUIRED_PERMISSIONS

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
            Theme.MainTheme {
                MainView()
            }
        }
    }
}