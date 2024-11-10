package com.dicoding.asclepius.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.dicoding.asclepius.CancerData
import com.dicoding.asclepius.R
import com.dicoding.asclepius.database.AppDatabase
import com.dicoding.asclepius.database.PredictionData
import com.dicoding.asclepius.databinding.ActivityResultBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//import org.tensorflow.lite.task.vision.classifier.Classifications

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        val results = intent.getSerializableExtra("results") as? List<CancerData>
        val inferenceTime = intent.getLongExtra("inferenceTime", 0L)
        Log.d("ResultActivity", "onCreate: imageUri = $imageUri")
        Log.d("ResultActivity", "onCreate: results = $results")
        Log.d("ResultActivity", "onCreate: inferenceTime = $inferenceTime")

        imageUri?.let {
            binding.resultImage.setImageURI(it)
        }

        val resultText = StringBuilder()
        results?.forEach { classification ->
            resultText.append("${classification.label} : ${classification.getScoreAsPercentage()}\n")
        }
        binding.resultText.text = resultText.toString()
        binding.inferenceTimeTextView.text = "Inference Time: $inferenceTime ms"

        results?.forEach { classification ->
            val predictionData = PredictionData(
                imageUri = imageUri.toString(),
                label = classification.label,
                score = classification.score,
                inferenceTime = inferenceTime
            )
            savePredictionData(predictionData)
        }
    }

    private fun savePredictionData(predictionData: PredictionData) {
        val database = AppDatabase.getDatabase(this)
        lifecycleScope.launch(Dispatchers.IO) {
            database.predictionDao().insert(predictionData)
        }
    }
}