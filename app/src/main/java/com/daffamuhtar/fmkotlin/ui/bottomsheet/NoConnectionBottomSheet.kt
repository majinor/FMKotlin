package com.daffamuhtar.fmkotlin.ui.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daffamuhtar.fmkotlin.databinding.ActivitySplashBinding
import com.daffamuhtar.fmkotlin.databinding.BsConnectiontroubleBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NoConnectionBottomSheet: BottomSheetDialogFragment() {

    private lateinit var binding: BsConnectiontroubleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BsConnectiontroubleBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDismiss(dialog: DialogInterface) {
        requireActivity().finish()
        super.onDismiss(dialog)
    }
}