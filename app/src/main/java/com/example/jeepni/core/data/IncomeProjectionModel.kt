package com.example.jeepni.core.data

import android.content.Context
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.channels.FileChannel
import java.nio.MappedByteBuffer

class IncomeProjectionModel(private val context: Context) {
    var tfliteModel: Interpreter? = null

    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd("boyong.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun try_open(){
        val fileDescriptor = try {
            context.assets.openFd("boyong.tflite")
        } catch (e: Exception) {
            Log.d("WEIRD", e.toString())
        }
    }

    fun loadModel() {
        if (tfliteModel == null) {
            try {
                val model = loadModelFile()
                tfliteModel = Interpreter(model)
            }catch (e: Exception){
                Log.d("WEIRD", e.toString())
            }
        }
    }

    fun projectIncome(currentIncome: Double): Double {
        loadModel()
        // Prepare input and output tensors
        val input = FloatArray(1)
        input[0] = currentIncome.toFloat()

        val output = Array(1) { FloatArray(1) }

        // Run inference
        tfliteModel?.run(input, output)
        val inferredValue = output[0][0]

        return inferredValue.toDouble()
    }
}
