package com.daffamuhtar.fmkotlin.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Display
import android.widget.VideoView
import java.lang.reflect.Method


class CustomVideoView : VideoView {
    private var width = 0
    private var height = 0
    private lateinit var context: Context
    private var listener: VideoSizeChangeListener? = null

    /**
     * @return true: fullscreen
     */
    var isFullscreen = false
        private set

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    /**
     * get video screen width and height for calculate size
     *
     * @param context Context
     */
    private fun init(context: Context) {
        this.context = context
        setScreenSize()
    }

    /**
     * calculate real screen size
     */
    private fun setScreenSize() {
        val display: Display = (context as Activity?)!!.windowManager.defaultDisplay
        if (Build.VERSION.SDK_INT >= 17) {
            //new pleasant way to get real metrics
            val realMetrics = DisplayMetrics()
            display.getRealMetrics(realMetrics)
            width = realMetrics.widthPixels
            height = realMetrics.heightPixels
        } else if (Build.VERSION.SDK_INT >= 14) {
            //reflection for this weird in-between time
            try {
                val mGetRawH: Method = Display::class.java.getMethod("getRawHeight")
                val mGetRawW: Method = Display::class.java.getMethod("getRawWidth")
                width = mGetRawW.invoke(display) as Int
                height = mGetRawH.invoke(display) as Int
            } catch (e: Exception) {
                //this may not be 100% accurate, but it's all we've got
                width = display.getWidth()
                height = display.getHeight()
            }
        } else {
            //This should be close, as lower API devices should not have window navigation bars
            width = display.getWidth()
            height = display.getHeight()
        }

        // when landscape w > h, swap it
        if (width > height) {
            val temp = width
            width = height
            height = temp
        }
    }

    /**
     * set video size change listener
     *
     */
    fun setVideoSizeChangeListener(listener: VideoSizeChangeListener?) {
        this.listener = listener
    }

    interface VideoSizeChangeListener {
        /**
         * when landscape
         */
        fun onFullScreen()

        /**
         * when portrait
         */
        fun onNormalSize()
    }

    override fun setVideoURI(uri: Uri?) {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(getContext(), uri)
        width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)!!.toInt()
        height =
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)!!.toInt()
        super.setVideoURI(uri)
        super.setVideoURI(uri)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        isFullscreen = if (context.getResources()
                .getConfiguration().orientation === Configuration.ORIENTATION_LANDSCAPE
        ) {
            // full screen when landscape
            setSize(height, width)
            if (listener != null) listener!!.onFullScreen()
            true
        } else {
            // height = width * 9/16
            setSize(width, width * 9 / 16)
            if (listener != null) listener!!.onNormalSize()
            false
        }
    }

    /**
     * set video sie
     *
     * @param w Width
     * @param h Height
     */
    private fun setSize(w: Int, h: Int) {
        setMeasuredDimension(w, h)
        holder.setFixedSize(w, h)
    }
}