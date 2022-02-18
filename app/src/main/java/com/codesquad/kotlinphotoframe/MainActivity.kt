package com.codesquad.kotlinphotoframe

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Dimension
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.PermissionChecker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val photoFrameTv = findViewById<TextView>(R.id.tv_photoframe)
        val name = "Hede"
        val addPhotoBtn = findViewById<Button>(R.id.btn_photoframe)
        val imageView = findViewById<ImageView>(R.id.iv_photoFrame_photoGallery)
        val moveBtn = findViewById<FloatingActionButton>(R.id.floating_btn_photo_frame)
        setTextViewAttribute(photoFrameTv, name)
        moveToPhotoView(moveBtn)
        changeRandomImage(addPhotoBtn, imageView)
        moveToPhotoView(moveBtn)
    }

    fun changeRandomImage(addPhotoBtn: Button, imageView: ImageView) {
        addPhotoBtn.setOnClickListener {
            val range = (1..22)
            val imgNum = range.random()
            val bitmapImg = getBitMapImage(imgNum) ?: return@setOnClickListener
            imageView.setImageBitmap(bitmapImg)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
        }
    }

    fun moveToPhotoView(addPhotoBtn: FloatingActionButton) {
        addPhotoBtn.setOnClickListener {
            val intent: Intent = Intent(this, TargetActivity::class.java)
            startActivity(intent)
        }
    }

    fun getBitMapImage(imgNum: Int): Bitmap? {
        try {
            val assetManager = this.assets
            val imageFileName= String.format("%02d.jpg", imgNum)
            return BitmapFactory.decodeStream(assetManager.open("$imageFileName"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun getPhotoAddResult(addPhotoBtn: Button) {
        addPhotoBtn.text = "다음"
        val getResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val message = it.data?.getStringExtra("message").toString()
                    Snackbar.make(addPhotoBtn,"${message}", Snackbar.LENGTH_LONG).show()
                }
            }
        addPhotoBtn.setOnClickListener {
            val intent: Intent = Intent(this, TargetActivity::class.java)
            getResult.launch(intent)
        }
    }

    fun printPhotoLoadMessage(addPhotoBtn: Button) {
        addPhotoBtn.setOnClickListener {
            val snackBar = Snackbar.make(addPhotoBtn, "사진을 불러옵니다", Snackbar.LENGTH_LONG)
            snackBar.show()
        }
    }

    fun setTextViewAttribute(photoFrameTv: TextView, name: String) {
        photoFrameTv.text = "${name}의 사진첩"
        photoFrameTv.setTextColor(Color.BLACK)
        photoFrameTv.setTextSize(Dimension.SP, 20F)
    }

}