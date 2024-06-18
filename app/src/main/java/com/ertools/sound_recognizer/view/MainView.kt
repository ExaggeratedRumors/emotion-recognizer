package com.ertools.emotion_recognizer.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ertools.emotion_recognizer.ui.Strings.MAIN_VIEW_TITLE


@Preview
@Composable
fun MainView () {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = MAIN_VIEW_TITLE, style = MaterialTheme.typography.h4)
        val context = LocalContext.current
        AudioVisualizer(context)
    }
}
