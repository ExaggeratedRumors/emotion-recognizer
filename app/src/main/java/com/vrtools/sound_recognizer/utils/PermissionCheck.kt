package com.vrtools.sound_recognizer.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vrtools.sound_recognizer.MainActivity
import java.util.ArrayList

private val permissions : Array<String> = arrayOf(
    Manifest.permission.RECORD_AUDIO,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE
)

fun permissionCheck(activity: MainActivity): Boolean {
    val requiredPermissions = ArrayList<String>()
    for (p in permissions)
        if (ContextCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED)
            requiredPermissions.add(p)
    return if (requiredPermissions.isNotEmpty()) {
        ActivityCompat.requestPermissions(
            activity,
            requiredPermissions.toTypedArray(),
            100
        )
        true
    } else false
}
