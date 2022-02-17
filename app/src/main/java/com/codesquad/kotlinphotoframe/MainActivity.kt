package com.codesquad.kotlinphotoframe

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Range
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Dimension
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scaleMatrix
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("$tag", "onCreate")
        val photoFrameTv = findViewById<TextView>(R.id.tv_photoframe)
        val name = "Hede"
        photoFrameTv.text = "${name}의 사진액자"
        val addPhotoBtn = findViewById<Button>(R.id.btn_photoframe)
        val imageView = findViewById<ImageView>(R.id.iv_photoFrame_photoGallery)
        initImageChangeEventListener(addPhotoBtn, imageView)
    }


    fun initImageChangeEventListener(addPhotoBtn: Button, imageView: ImageView) {
        addPhotoBtn.setOnClickListener {
            val range = (1..22)
            val imgNum = range.random()
            val bitmapImg = getBitMapImage(imgNum) ?: return@setOnClickListener
            imageView.setImageBitmap(bitmapImg)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
        }
    }


    fun initActivityChange(addPhotoBtn: Button) {
        addPhotoBtn.text = "다음"
        val getResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    var message = it.data?.getStringExtra("message").toString()
                    Snackbar.make(findViewById<ConstraintLayout>(R.id.const_layout), "${message}", Snackbar.LENGTH_LONG).show()
                }
            }
        addPhotoBtn.setOnClickListener {
            val intent: Intent = Intent(this, TargetActivity::class.java)
            getResult.launch(intent)
        }
    }

    fun initSnackBarEventListener(addPhotoBtn: Button) {
        addPhotoBtn.setOnClickListener {
            val snackBar = Snackbar.make(findViewById<ConstraintLayout>(R.id.const_layout), "사진을 불러옵니다", Snackbar.LENGTH_LONG)
            snackBar.show()
        }
    }

    fun setTextViewAttribute(photoFrameTv: TextView) {
        photoFrameTv.setTextColor(Color.parseColor("#FF000000"))
        photoFrameTv.setBackgroundColor(Color.parseColor("#FF000000"))
        photoFrameTv.setTextSize(Dimension.SP, 18F)
    }

    fun getBitMapImage(imgNum: Int): Bitmap? {
        try {
            val assetManager = this.assets
            val imageFileName: String = if (imgNum < 10) "0${imgNum}" else "$imgNum"
            return BitmapFactory.decodeStream(assetManager.open("${imageFileName}.jpg"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onStart() {
        super.onStart()
        Log.d("${tag}", "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("${tag}", "reStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("${tag}", "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("${tag}", "onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d("${tag}", "onPause")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("${tag}", "onDestroy")
    }

}