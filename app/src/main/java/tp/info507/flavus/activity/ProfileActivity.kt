package tp.info507.flavus.activity

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import tp.info507.flavus.R
import java.io.File
import java.io.FileOutputStream

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val image = findViewById<ImageView>(R.id.profile_image)
        val pseudo = findViewById<EditText>(R.id.profile_pseudo)
        val valider = findViewById<ImageView>(R.id.valider_profil)

        // Charger l'image sauvegardée
        val savedImage = loadImageFromInternalStorage()
        if (savedImage != null) {
            image.setImageBitmap(savedImage)
        }

        // Charger le pseudo sauvegardé
        val savedPseudo = loadPseudoFromPreferences()
        pseudo.setText(savedPseudo)

        // Prendre une photo
        val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                image.setImageBitmap(bitmap)
                saveImageToInternalStorage(bitmap)
            }
        }

        image.setOnClickListener {
            takePhoto.launch(null)
        }

        valider.setOnClickListener {
            // Sauvegarder le pseudo
            savePseudoToPreferences(pseudo.text.toString())
            finish()
        }
    }

    // Méthode pour vérifier les permissions (si nécessaire)
    private fun checkPermission(permission: String): Boolean {
        var res = true
        if (ContextCompat.checkSelfPermission(applicationContext, permission) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                ActivityCompat.requestPermissions(this, arrayOf(permission), 1)
            }
            res = false
        }
        return res
    }

    // Méthode pour sauvegarder le pseudo
    private fun savePseudoToPreferences(pseudo: String) {
        val sharedPreferences = getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("profile_pseudo", pseudo)
        editor.apply()
    }

    // Méthode pour charger le pseudo
    private fun loadPseudoFromPreferences(): String? {
        val sharedPreferences = getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        return sharedPreferences.getString("profile_pseudo", "")
    }

    // Méthode pour sauvegarder l'image dans le stockage interne
    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        val fileName = "profile_image.png"
        val file = File(filesDir, fileName)
        try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.absolutePath
    }

    // Méthode pour charger l'image depuis le stockage interne
    private fun loadImageFromInternalStorage(): Bitmap? {
        val fileName = "profile_image.png"
        val file = File(filesDir, fileName)
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    }
}
