package com.example.jeepni.core.data

import org.tensorflow.lite.Interpreter

class IncomeProjectionModel(context: Context) {
    private var tfliteModel: TFLite.Interpreter? = null

    fun loadModel(){
        if (tfliteModel == null) {
            // Loads the model from assets
            tfliteModel = TFLiteTask.loadFromAsset(context, "boyong.tflite")
        }
    }

    fun projectIncome(currentIncome: Double): Double {
        loadModel()
        // Prepare inpit and output tensors
        val input = FloatArray(1)
        input[0] = currentIncome.toFloat()

        val output = FloatArray(1){ FloatArray(1) } // 2D array for [1][1] output shape

        // Run inference
        tfliteModel!!.run(input, output)
        val inferredValue = output[0][0]

        return inferredValue.toDouble()
    }
}