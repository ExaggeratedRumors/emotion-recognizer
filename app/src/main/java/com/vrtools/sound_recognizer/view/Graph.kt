package com.vrtools.sound_recognizer.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun Graph (data: ByteArray) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        data.forEach { sample ->
            val animatedHeight by animateFloatAsState(
                targetValue = sample.toFloat(),
                animationSpec = spring()
            )

            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height((animatedHeight).dp)
                    .background(Color.Blue)
            )
        }
    }



}


//var data: IntArray = IntArray(0)
//    val fade = Paint()
//    val paint = Paint()
//    val screen = Rect()
//    val labels = ArrayList<String>()
//    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
//    lateinit var lineCanvas: Canvas
//    val isViewInit = AtomicBoolean(false)
//
//
//    LaunchedEffect(Unit) {
//        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap!!)
//        onDrawGraph(canvas)
//        lineCanvas = Canvas(bitmap!!)
//        isViewInit.set(true)
//        fade.color = LABEL_COLOR.value.toInt()
//        fade.xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
//        for(i in 0..THIRDS_NUMBER) {
//            labels.add(String())
//        }
//    }
//
//    Column (
//        modifier = Modifier.fillMaxWidth()
//    ){
//        Canvas(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            if(!isViewInit.get()) return@Canvas
//            screen.set(0, 0, width, height)
//            paint.textSize = 35f
//            lineCanvas.drawColor(Color.TRANSPARENT)
//            onDrawGraph(lineCanvas)
//            lineCanvas.drawPaint(fade)
//            val matrix = Matrix()
//            //drawBitmap(bitmap!!, matrix, null)
//        }
//     }









/// V1

/*    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(!isViewInit.get()) return
        screen.set(0, 0, width, height)
        paint.textSize = 35f
        lineCanvas.drawColor(Color.TRANSPARENT)
        drawComponents(canvas)
        lineCanvas.drawPaint(fade)
        matrix.reset()
        canvas.drawBitmap(bitmap, matrix, null)
    }

    private fun drawComponents(canvas: Canvas) {
        val drawingVector = FloatArray(2 * data.size * 4)
        for(i in data.indices) {
            drawingVector[i * 4] = i * 32f + UPON_LABELS_OFFSET
            drawingVector[i * 4 + 2] = i * 32f + UPON_LABELS_OFFSET
            drawingVector[i * 4 + 1] = height.toFloat() + LOWER_LABELS_OFFSET
            drawingVector[i * 4 + 3] = height.toFloat() + LOWER_LABELS_OFFSET - (data[i] * 12 - 10)
            drawLabel(i, drawingVector, canvas)
        }
        drawLines(drawingVector)
    }*/

/* private fun drawLines(vector: FloatArray) {
     paint.strokeWidth = 10f
     paint.isAntiAlias = true
     paint.color = LINE_COLOR.value.toInt()
     lineCanvas.drawLines(vector, paint)
 }

 private fun drawLabel(i: Int, vector: FloatArray, canvas: Canvas) {
     labels[i].onRefresh(data[i])
     var shift = 100
     if(i % 2 == 0) shift += 50
     if(i % 4 == 1 || i % 4 == 0)
         paint.color = Color.GRAY
     else paint.color = Color.WHITE
     canvas.drawText(
         labels[i].value.toString(),
         vector[i * 4] - 7,
         vector[i * 4 + 1] + shift,
         paint
     )
 }*/