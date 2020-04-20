package com.everyone.cantalk.ui.addfriend

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.everyone.cantalk.R
import com.everyone.cantalk.databinding.ActivityAddFriendBinding
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import me.dm7.barcodescanner.zxing.ZXingScannerView

class AddFriendActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var binding : ActivityAddFriendBinding

    companion object{
        const val EXTRA_FRIEND_ID = "EXTRA_FRIEND_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_friend)

        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object: PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    binding.barcodeScanner.setResultHandler(this@AddFriendActivity)
                    binding.barcodeScanner.startCamera()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {

                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(this@AddFriendActivity, "You Must Accept this permission", Toast.LENGTH_SHORT).show()
                }
            })
            .check()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.barcodeScanner.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        binding.barcodeScanner.setResultHandler(this)
        binding.barcodeScanner.startCamera()
    }

    override fun onPause() {
        super.onPause()
        binding.barcodeScanner.stopCamera()
    }


    override fun handleResult(rawResult: Result?) {
        val intent = Intent()
        intent.putExtra(EXTRA_FRIEND_ID, rawResult?.text)
        setResult(RESULT_OK, intent)
        finish()
    }
}