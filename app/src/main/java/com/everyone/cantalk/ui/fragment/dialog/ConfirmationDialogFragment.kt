package com.everyone.cantalk.ui.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.everyone.cantalk.R
import com.everyone.cantalk.databinding.DialogFragmentConfirmationBinding

class ConfirmationDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentConfirmationBinding

    interface SetPositiveButtonListener {
        fun onClick(clicked: Boolean)
    }

    companion object {
        private const val IMAGE = "image"
        private const val TITLE = "title"
        private const val SECONDARY = "secondary"
        private const val DESCRIPTION = "description"
        var mOnPositiveButtonClicked : SetPositiveButtonListener? = null

        fun getInstance(image: Int, title: String, secondary: String, description: String, onPositiveButtonListener: SetPositiveButtonListener) : ConfirmationDialogFragment {
            val fg =
                ConfirmationDialogFragment()
            val bundle = Bundle()
            bundle.putInt(IMAGE, image)
            bundle.putString(TITLE, title)
            bundle.putString(SECONDARY, secondary)
            bundle.putString(DESCRIPTION, description)
            fg.arguments = bundle
            if (onPositiveButtonListener != null) {
                mOnPositiveButtonClicked = onPositiveButtonListener
            }
            return fg
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.dialog_fragment_confirmation, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image = arguments?.getInt(IMAGE)
        val title = arguments?.getString(TITLE)
        val secondary = arguments?.getString(SECONDARY)
        val description = arguments?.getString(DESCRIPTION)

        if (image == 0) {
            binding.disabledImage.visibility = View.GONE
        }
        else {
            binding.disabledImage.setImageResource(image!!)
        }

        if (title.isNullOrEmpty()) {
            binding.dialogTitle.visibility = View.GONE
        }
        else {
            binding.dialogTitle.text = title
        }

        if (secondary.isNullOrEmpty()) {
            binding.dialogSecondary.visibility = View.GONE
        }
        else {
            binding.dialogSecondary.text = secondary
        }

        if (description.isNullOrEmpty()) {
            binding.dialogDescription.visibility = View.GONE
        }
        else {
            binding.dialogDescription.text = description
        }

        binding.btnNo.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnYes.setOnClickListener {
            mOnPositiveButtonClicked?.onClick(true)
            dialog?.dismiss()
        }
    }

    fun onButtonPositiveClicked(onPositiveButtonListener: SetPositiveButtonListener) {
        if (onPositiveButtonListener != null) {
            mOnPositiveButtonClicked = onPositiveButtonListener
        }
    }
}