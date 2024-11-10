import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yalantis.ucrop.UCrop
import java.io.File

class MainViewModel : ViewModel() {

    private val _currentImageUri = MutableLiveData<Uri?>()
    val currentImageUri: LiveData<Uri?> get() = _currentImageUri

    private val _galleryLauncher = MutableLiveData<ActivityResultLauncher<Intent>>()
    val galleryLauncher: LiveData<ActivityResultLauncher<Intent>> get() = _galleryLauncher

    private val _cropLauncher = MutableLiveData<ActivityResultLauncher<Intent>>()
    val cropLauncher: LiveData<ActivityResultLauncher<Intent>> get() = _cropLauncher

    fun setGalleryLauncher(launcher: ActivityResultLauncher<Intent>) {
        _galleryLauncher.value = launcher
    }

    fun setCropLauncher(launcher: ActivityResultLauncher<Intent>) {
        _cropLauncher.value = launcher
    }

    fun startGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        _galleryLauncher.value?.launch(intent)
    }

    fun startCrop(context: Context, uri: Uri) {
        val uniqueFileName = "croppedImage_${System.currentTimeMillis()}.jpg"
        val destinationUri = Uri.fromFile(File(context.cacheDir, uniqueFileName))
        val options = UCrop.Options().apply {
            setFreeStyleCropEnabled(true)
        }
        val cropIntent = UCrop.of(uri, destinationUri)
            .withOptions(options)
            .getIntent(context)
        _cropLauncher.value?.launch(cropIntent)
    }

    fun setImageUri(uri: Uri?) {
        _currentImageUri.value = uri
    }

    fun rotateBitmapIfNeeded(context: Context, bitmap: Bitmap, uri: Uri): Bitmap {
        val inputStream = context.contentResolver.openInputStream(uri)
        val exif = ExifInterface(inputStream!!)
        val rotation = exif.rotationDegrees
        if (rotation != 0) {
            val matrix = Matrix().apply { postRotate(rotation.toFloat()) }
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
        return bitmap
    }

    fun toBitmap(context: Context, imageUri: Uri): Bitmap {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        return BitmapFactory.decodeStream(inputStream) ?: throw IllegalArgumentException("Failed to load bitmap from Uri")
    }
}