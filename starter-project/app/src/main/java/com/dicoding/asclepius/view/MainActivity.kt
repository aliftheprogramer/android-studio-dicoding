package com.dicoding.asclepius.view

import MainViewModel
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.dicoding.asclepius.CancerData
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.HeroAdapter
import com.dicoding.asclepius.api.ArticlesItem
import com.dicoding.asclepius.api.CancerResponse
import com.dicoding.asclepius.api.services.RetrofitClient
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.google.android.material.tabs.TabLayoutMediator
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.task.vision.classifier.Classifications
//import org.tensorflow.lite.task.gms.vision.classifier.Classifications
//import org.tensorflow.lite.task.vision.classifier.Classifications
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.Serializable


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var  galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var cropLauncher: ActivityResultLauncher<Intent>
    private lateinit var heroAdapter: HeroAdapter

    private var currentImageUri: Uri? = null
    private var imageClassifierHelper: ImageClassifierHelper? = null
    private val apikey = "c3e004e0e5db4adb87e365e8c7e09960"
    private  val artcles = listOf{

    }

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }



    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchArticles()

        mainViewModel.setGalleryLauncher(registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.data
                mainViewModel.setImageUri(uri)
                uri?.let { mainViewModel.startCrop(this, it) }
            }
        })

        mainViewModel.setCropLauncher(registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                mainViewModel.setImageUri(resultUri)
                showImage()
            }
        })

        mainViewModel.currentImageUri.observe(this, { uri ->
            uri?.let { binding.previewImageView.setImageURI(it) }
        })

        mainViewModel.galleryLauncher.observe(this, { launcher ->
            galleryLauncher = launcher
        })

        mainViewModel.cropLauncher.observe(this, { launcher ->
            cropLauncher = launcher
        })


        binding.galleryButton.setOnClickListener{
            showLoading(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)
                    mainViewModel.startGallery()
                    showLoading(false)
                } else {
                    mainViewModel.startGallery()
                    showLoading(false)

                }
            }else{
                mainViewModel.startGallery()
                showLoading(false)
            }
        }

        binding.analyzeButton.setOnClickListener{
            analyzeImage()
        }

        binding.historyButton.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        Thread {
            imageClassifierHelper = ImageClassifierHelper(
                context = this,
                classifierListener = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(error: String) {
                        runOnUiThread {
                            showToast(error)
                        }
                    }

                    override fun onResults(result: List<Classifications>?, inferenceTime: Long) {
                        runOnUiThread {
                            moveToResult(result,inferenceTime)
                        }
                    }
                }
            )
        }.start()


    }

    private fun fetchArticles() {
        showLoading(true)
        RetrofitClient.instance.getTopHeadlines("cancer", "health", "en", apikey)
            .enqueue(object : Callback<CancerResponse> {
                override fun onResponse(call: Call<CancerResponse>, response: Response<CancerResponse>) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles ?: emptyList()
                        // Filter out articles with specific values
                        val filteredArticles = articles.filter { article ->
                            article.title != "[Removed]" &&
                                    article.description != "[Removed]" &&
                                    article.content != "[Removed]" &&
                                    article.source.name != "[Removed]"
                        }
                        if (filteredArticles.isNotEmpty()) {
                            setupViewPager(filteredArticles)
                        }
                    } else {
                        Log.e("MainActivity", "onResponse: ${response.message()} / gagal fetch data")
                    }
                }

                override fun onFailure(call: Call<CancerResponse>, t: Throwable) {
                    Log.e("MainActivity", "onFailure: ${t.message}")
                }
            })
    }

    private fun setupViewPager(articles: List<ArticlesItem>) {

        heroAdapter = HeroAdapter(articles)
        binding.heroViewPager.adapter = heroAdapter

        TabLayoutMediator(binding.tabLayout, binding.heroViewPager) { tab, position ->
//            tab.text = "${position + 1}"
            tab.text= ""

        }.attach()
        autoScrollHero()
    }

    private fun autoScrollHero() {
        val handler = android.os.Handler()
        val runnable = object : Runnable {
            var index = 0
            override fun run() {
                if (index == heroAdapter.itemCount) index = 0
                binding.heroViewPager.setCurrentItem(index++, true)
                handler.postDelayed(this, 7000)
            }
        }
        handler.post(runnable)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        showLoading(true)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            PERMISSION_CODE -> {
                if ( grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mainViewModel.startGallery()
                    showToast("Permission denied")
                    showLoading(false)
                } else {
                    showLoading(false)
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            currentImageUri = data?.data
            showImage()
            showLoading(false)
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.progressIndicator.visibility =
            if (loading) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }

    private fun showImage() {
        mainViewModel.currentImageUri.value?.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
        mainViewModel.currentImageUri.value?.let {
            // implementasi analisis
            imageClassifierHelper?.classifyImage(it)
        } ?: showToast("No image selected")

    }


    private fun moveToResult(results : List<Classifications>?, inferenceTime: Long) {

        Log.d("MainActivity", "moveToResult: currentImageUri = $currentImageUri")
        Log.d("MainActivity", "moveToResult: results = $results")
        Log.d("MainActivity", "moveToResult: inferenceTime = $inferenceTime")

        val serializationResults = results?.flatMap { classification ->
            classification.categories.map { category ->
                CancerData(category.label, category.score)
            }
        }

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("imageUri", mainViewModel.currentImageUri.value)
        intent.putExtra("results", ArrayList(serializationResults!!))
        intent.putExtra("inferenceTime", inferenceTime)
        Log.d("Image URI", "showImage: $mainViewModel.currentImageUri.value")
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}