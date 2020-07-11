package com.everyone.cantalk.ui.addfriend.showing

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseActivity
import com.everyone.cantalk.databinding.ActivityQrcodeBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

class QRCodeActivity : BaseActivity<ViewModel, ActivityQrcodeBinding>(ViewModel::class.java,
    R.layout.activity_qrcode
) {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, QRCodeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generateQrCode()
    }

    private fun generateQrCode() {
        val text : String = load().id
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix : BitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,500,500)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap : Bitmap = barcodeEncoder.createBitmap(bitMatrix)
            binding.imageQrCode.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

}
