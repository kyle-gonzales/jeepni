package com.example.jeepni.core.data

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.channels.FileChannel
import java.nio.MappedByteBuffer

class IncomeProjectionModel(private val context: Context) {
    public var tfliteModel: Interpreter? = null

    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd("boyong.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun loadModel() {
        if (tfliteModel == null) {
            val model = loadModelFile()
            tfliteModel = Interpreter(model)
        }
    }

    fun projectIncome(currentIncome: Double): Double {
        loadModel()
        // Prepare input and output tensors
        val input = floatArrayOf(currentIncome.toFloat())
        val output = FloatArray(1)

        // Run inference
        tfliteModel?.run(input, output)
        val inferredValue = output[0]

        return inferredValue.toDouble()
    }
}
