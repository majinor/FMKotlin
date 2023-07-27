package com.daffamuhtar.fmkotlin.util

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.constants.Constanta
import com.daffamuhtar.fmkotlin.ui.bottomsheet.PhotoVideoPreviewBottomSheet
import com.google.android.material.imageview.ShapeableImageView

class PhotoHelper {
    companion object{
        fun setPhotoOrVideo(
            context: Context,
            fileUrl: String,
            ivPhoto: ShapeableImageView,
        ) {
            if ((fileUrl.substring(fileUrl.lastIndexOf(".") + 1) == "jpg") or
                (fileUrl.substring(fileUrl.lastIndexOf(".") + 1) == "jpeg") or
                (fileUrl.substring(fileUrl.lastIndexOf(".") + 1) == "png")
            ) {

                Glide.with(ivPhoto.context)
                    .load(fileUrl)
                    .centerCrop()
                    .placeholder(R.drawable.baseline_image_24)
                    .override(200, 200)
                    .into(ivPhoto)

                ivPhoto.setOnClickListener{
                    val bundle = Bundle()
                    bundle.putString(Constanta.EXTRA_PREVIEW, fileUrl)
                    bundle.putString("WHO", "Photo")
                    val preview = PhotoVideoPreviewBottomSheet()
                    preview.setArguments(bundle)
                    preview.show((context as AppCompatActivity).supportFragmentManager,
                        "previewPhoto"
                    )
                }
            } else {
                try {
                    Glide.with(ivPhoto.context)
                        .load(fileUrl)
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .centerCrop()
                        .into(ivPhoto)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ivPhoto.setForeground(
                            ContextCompat.getDrawable(
                                ivPhoto.context,
                                R.drawable.ic_play_arrow_black_24dp
                            )
                        )
                    }
                    ivPhoto.setOnClickListener{
                        val bundle = Bundle()
                        bundle.putString(Constanta.EXTRA_PREVIEW, fileUrl)
                        bundle.putString("WHO", "Video")
                        val preview = PhotoVideoPreviewBottomSheet()
                        preview.setArguments(bundle)
                        preview.show((context as AppCompatActivity).supportFragmentManager,
                            "previewPhoto"
                        )
                    }

                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                }
            }
        }
    }
}