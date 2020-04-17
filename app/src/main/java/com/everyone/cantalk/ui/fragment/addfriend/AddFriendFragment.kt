package com.everyone.cantalk.ui.fragment.addfriend

import android.graphics.Bitmap
import androidx.navigation.fragment.findNavController
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseFragment
import com.everyone.cantalk.databinding.FragmentAddFriendBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

class AddFriendFragment : BaseFragment<AddFriendViewModel, FragmentAddFriendBinding>(AddFriendViewModel::class.java, R.layout.fragment_add_friend) {

    override fun setListener() {
        super.setListener()
        generateQrCode()

        binding.imageQrCode.setOnClickListener {
            findNavController().navigate(R.id.action_addFriendFragment_to_readingMessageFragment)
        }
    }

    fun generateQrCode() {
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