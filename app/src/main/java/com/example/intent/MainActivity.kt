package com.example.intent

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ShareCompat

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextWeb = findViewById<EditText>(R.id.website_edittext)
        val editTextLocation = findViewById<EditText>(R.id.location_edittext)
        val editTextShare = findViewById<EditText>(R.id.share_edittext)
        val buttonWeb = findViewById<Button>(R.id.open_website_button)
        val buttonLocation = findViewById<Button>(R.id.open_location_button)
        val buttonShare = findViewById<Button>(R.id.share_text_button)
        val buttonCamera = findViewById<Button>(R.id.button_camera)

        buttonWeb.setOnClickListener {
            openWebsite(editTextWeb.text.toString())
        }
        buttonLocation.setOnClickListener {
            openLocation(editTextLocation.text.toString())
        }
        buttonShare.setOnClickListener {
            shareText(editTextShare.text.toString())
        }
        buttonCamera.setOnClickListener {
            openCamera()
        }

    }

    fun openWebsite(text: String) {
        // Criando uma Url a partir de uma intent string.
        val webpage = Uri.parse(text)

        // Criando um intent com a url criada antes
        val webIntent = Intent(Intent.ACTION_VIEW, webpage)

        // Achando uma activity para lidar com o intent e iniciar a activity
        try {
            startActivity(webIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "No application can handle the link",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    fun openLocation(text: String) {
        // Transformando o texto em uma url
        val addressUri = Uri.parse("geo:0,0?q=$text")
        // Criando um Intent
        val intent = Intent(Intent.ACTION_VIEW, addressUri)
        intent.setPackage("com.google.android.apps.maps")

        // Resolvendo o intent, caso tenha inicia a activity se não log.d
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "No application can handle the link",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun shareText(text: String) {
        val mimeType = "text/plain"

        ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeType)
            .setChooserTitle("Share this text with: ")
            .setText(text)
            .startChooser()
    }

    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try{
            startActivity(intent)
        }catch(e: ActivityNotFoundException){
            Toast.makeText(
                this,
                "$e.message.toString()",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

