package com.naram.flo.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.ViewStub
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.databinding.*
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naram.flo.R
import com.naram.flo.data.local.model.Lyrics

object BindingAdapter {
    /**
     * ImageView 에 앨범 커버 이미지를 load
     */
    @JvmStatic
    @BindingAdapter(
        value = ["loadImage"],
        requireAll = false
    )
    fun loadImage(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }

    /**
     * ViewStub 사용 시, 주석 해제
     */
//    @JvmStatic
//    @BindingAdapter(
//        value = ["android:visibility"]
//    )
//    fun visibility(viewStub: ViewStub, flag: Boolean) {
//        if (flag) {
//            when (viewStub.id) {
//                R.id.vsSong -> viewStub.visibility = View.GONE
//                R.id.vsLyrics -> viewStub.visibility = View.VISIBLE
//                else -> viewStub.visibility = View.VISIBLE
//            }
//        } else {
//            when (viewStub.id) {
//                R.id.vsLyrics -> viewStub.visibility = View.GONE
//                R.id.vsSong -> viewStub.visibility = View.VISIBLE
//                else -> viewStub.visibility = View.GONE
//            }
//        }
//    }

    /**
     * flag 에 따라 현재 보이는 화면을 바꿈
     */
    @JvmStatic
    @BindingAdapter(
        value = ["android:visibility"]
    )
    fun visibility(view: View, flag: Boolean) {
        if (flag) {
            when (view.id) {
                R.id.clSongInformation -> view.visibility = View.GONE
                R.id.clFullLyrics -> view.visibility = View.VISIBLE
                else -> view.visibility = View.VISIBLE
            }
        } else {
            when (view.id) {
                R.id.clFullLyrics -> view.visibility = View.GONE
                R.id.clSongInformation -> view.visibility = View.VISIBLE
                else -> view.visibility = View.GONE
            }
        }
    }

    /**
     * Splash 관련
     */
    @JvmStatic
    @BindingAdapter(
        value = ["splashVisibility"]
    )
    fun splashVisibility(view: View, splashFlag: Boolean) {
        if (splashFlag) {
            when (view) {
                is ImageView -> view.visibility = View.GONE
                else -> view.visibility = View.VISIBLE
            }
        } else {
            when (view) {
                is ImageView -> view.visibility = View.VISIBLE
                else -> view.visibility = View.GONE
            }
        }
    }

    /**
     * SeekBar Control Listener
     */
    @JvmStatic
    @BindingAdapter(
        value = ["seekBarChangeListener"]
    )
    fun seekBarChangeListener(seekBar: SeekBar, changeListener: SeekBar.OnSeekBarChangeListener) {
        seekBar.setOnSeekBarChangeListener(changeListener)
    }

    /**
     * RecyclerView 설정
     */
    @SuppressLint("NotifyDataSetChanged")
    @JvmStatic
    @BindingAdapter(
        value = [
            "setItems",
            "vm"]
    )
    fun setRecyclerView(
        recyclerView: RecyclerView,
        items: ObservableArrayList<Lyrics>,
        vm: MainViewModel
    ) {
        if (recyclerView.adapter == null) {
            val adapter = LyricsRecyclerAdapter(vm)
            adapter.setHasStableIds(true)
            recyclerView.adapter = adapter
        }

        val adapter = recyclerView.adapter as LyricsRecyclerAdapter
        adapter.items = items
        adapter.notifyDataSetChanged()
    }

    /**
     * RecyclerView 현재 가사에 focus
     */
    @JvmStatic
    @BindingAdapter(
        value = [
            "scrollToPosition",
            "isSearchModeOn"
        ]
    )
    fun scrollToPosition(
        recyclerView: RecyclerView,
        position: Int,
        isSearchModeOn: Boolean
    ) {
        if (recyclerView.adapter != null && !isSearchModeOn) {
            if (position > -1) {
                val layoutManager = LinearLayoutManager(recyclerView.context)
                recyclerView.layoutManager = layoutManager

                val centerOfScreen = recyclerView.height / 2
                layoutManager.scrollToPositionWithOffset(position, centerOfScreen)
            }
        }
    }

    /**
     * 재생 버튼 / 정지 버튼 이미지 load
     */
    @JvmStatic
    @BindingAdapter(
        value = ["setButtonImage"]
    )
    fun setButtonImage(imageButton: ImageButton, resId: ObservableInt) {
        Glide.with(imageButton.context)
            .load(resId.get())
            .into(imageButton)
    }

    /**
     * 전체 가사 화면에서 현재 재생중인 가사 Highlighting
     */
    @JvmStatic
    @BindingAdapter(
        value = [
            "android:textStyle",
            "android:textColor"
        ],
        requireAll = false
    )
    fun setHighlighting(view: View, highlighting: Boolean, color: String) {
        when (highlighting) {
            true -> {
                (view as TextView).apply {
                    setTextColor(Color.parseColor(color))
                    setTypeface(null, Typeface.BOLD)
                }
            }
            else -> {
                (view as TextView).apply {
                    setTextColor(Color.parseColor(color))
                    setTypeface(null, Typeface.NORMAL)
                }
            }
        }
    }

    /**
     * 타이틀 TextView 가 흐를 수 있도록 함
     */
    @JvmStatic
    @BindingAdapter(
        value = [
            "android:selected"
        ]
    )
    fun selected(view: View, flag: Boolean) {
        if (flag) (view as TextView).isSelected = true
    }
}