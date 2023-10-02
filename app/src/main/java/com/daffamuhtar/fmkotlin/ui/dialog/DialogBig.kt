package com.daffamuhtar.fmkotlin.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.constants.ConstantsDialog
import com.daffamuhtar.fmkotlin.databinding.DialogBigBinding

class DialogBig (context: Context?) : AlertDialog(context) {
    private lateinit var binding: DialogBigBinding

    private var dialogTitle: String? = null
    private var dialogMessage: String? = null
    private var dialogType: String? = null

    private var textBtnPositive: String? = null
    private var textBtnNegative: String? = null
    private var textBtnClose: String? = null
    private var textBtnCancel: String? = null

    private var oclPositive: View.OnClickListener? = null
    private var oclNegative: View.OnClickListener? = null
    private var oclClose: View.OnClickListener? = null
    private var oclCancel: View.OnClickListener? = null

    private var cancleListener: DialogInterface.OnCancelListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogBigBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        prepareView()

    }

    private fun prepareView() {
        setDialogType(getDialogType(dialogType!!)!!)
        setOnCancelListener(cancleListener)
        if (dialogTitle != null) {
            binding.tvTitle.setText(getDialogTitle())
            binding.tvTitle.setVisibility(View.VISIBLE)
        }
        if (dialogMessage != null) {
            binding.tvMessage.setVisibility(View.VISIBLE)
            binding.tvMessage.setText(getDialogMessage())
        }
        if (oclPositive != null) {
            binding.btnPositive.setVisibility(View.VISIBLE)
            binding.btnPositive.setOnClickListener(oclPositive)
            binding.btnPositive.setText(textBtnPositive)
        }
        if (oclNegative != null) {
            binding.btnNegative.setVisibility(View.VISIBLE)
            binding.btnNegative.setOnClickListener(oclNegative)
            binding.btnNegative.setText(textBtnNegative)
        }
        if (oclClose != null) {
            binding.btnClose.setVisibility(View.VISIBLE)
            binding.btnClose.setOnClickListener(oclClose)
            binding.btnClose.setText(textBtnClose)
        }
        if (oclCancel != null) {
            binding.btnCancel.setVisibility(View.VISIBLE)
            binding.btnCancel.setOnClickListener(oclCancel)
            binding.btnCancel.setText(textBtnCancel)
        }
    }

    fun setDialogType(dialogType: String) {
        this.dialogType = dialogType
    }

    fun getDialogTitle(): String? {
        return dialogTitle
    }

    fun setDialogTitle(title: String) {
        this.dialogTitle = title
    }

    fun getDialogMessage(): String? {
        return dialogMessage
    }

    fun setDialogMessage(message: String) {
        this.dialogMessage = message
    }

    fun setOclPositive(buttonText: String, onClickListener: View.OnClickListener) {
        textBtnPositive = buttonText
        this.oclPositive = onClickListener
    }

    fun setOclNegative(buttonText: String, onClickListener: View.OnClickListener) {
        textBtnNegative = buttonText
        this.oclNegative = onClickListener
    }

    fun setOclClose(buttonText: String, onClickListener: View.OnClickListener) {
        textBtnClose = buttonText
        this.oclClose = onClickListener
    }

    fun setOclCancel(buttonText: String, onClickListener: View.OnClickListener) {
        textBtnCancel = buttonText
        this.oclCancel = onClickListener
    }

    fun setCancleListener(cancleListener: DialogInterface.OnCancelListener) {
        this.cancleListener = cancleListener
    }

    private fun getDialogType(dialogType: String): String? {
        if (dialogType === ConstantsDialog.DIALOG_TYPE_CONFIRMATION) {
            binding.ivImage.setImageResource(R.drawable.ic_help_outline_black_24dp)
            binding.ivImage.setBackgroundResource(R.drawable.ic_circle_yellow)
        } else if (dialogType === ConstantsDialog.DIALOG_TYPE_ERROR) {
            binding.ivImage.setImageResource(R.drawable.ic_false_circle_red_24dp)
            binding.ivImage.setBackgroundResource(R.drawable.ic_circle_redsoft)
            binding.ivImage.setPaddingRelative(35, 35, 35, 35)
        } else if (dialogType === ConstantsDialog.DIALOG_TYPE_SUCCESS) {
            binding.ivImage.setImageResource(R.drawable.icon_verify_repair)
            binding.ivImage.setBackgroundResource(R.drawable.ic_circle_yellow)
            binding.ivImage.setPadding(10, 10, 10, 10)
        } else if (dialogType === ConstantsDialog.DIALOG_TYPE_WARNING) {
            binding.ivImage.setImageResource(R.drawable.ic_baseline_error_red_24)
            binding.ivImage.setBackgroundResource(R.drawable.ic_circle_redsoft)
            binding.ivImage.setPaddingRelative(35, 35, 35, 35)
        }
        return dialogType
    }


}