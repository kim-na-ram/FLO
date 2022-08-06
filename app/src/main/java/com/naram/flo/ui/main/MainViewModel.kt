package com.naram.flo.ui.main

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.databinding.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naram.flo.R
import com.naram.flo.data.local.model.Lyrics
import com.naram.flo.data.remote.api.ApiRepository
import com.naram.flo.util.Const.HIGHLIGHT
import com.naram.flo.util.Const.WHITE
import com.naram.flo.util.Extensions.convertTime
import com.naram.flo.util.Extensions.timeToInt
import com.naram.flo.util.Extensions.timeToStringMS
import com.naram.flo.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    // RxJava Variable
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    // DataBinding
    val splashFlag = ObservableBoolean(false)
    val lyricsFlag = ObservableBoolean(false)

    val singer = ObservableField("")
    val album = ObservableField("")
    val title = ObservableField("")
    val image = ObservableField("")
    val nowLyrics = ObservableField("")
    val nextLyrics = ObservableField("")
    val nowLyricsIndex = ObservableInt(-1)
    var isSearchModeOn = ObservableBoolean(false)
    var lyrics = ObservableArrayList<Lyrics>()

    val resId = ObservableInt(R.drawable.ic_baseline_play)
    val nowPlayTime = ObservableField("00:00")
    val playTime = ObservableField("00:00")
    val progress = ObservableInt(0)
    val maxProgress = ObservableInt(0)

    private var file: String? = null
    private var mediaPlayer: MediaPlayer? = null


    /**
     * seekBar Progress 사용자 터치 제스쳐 관련 Listener
     */
    val changeListener = object : SeekBar.OnSeekBarChangeListener {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            if (p2) {
                progress.set(p1)
                if (mediaPlayer != null) {
                    mediaPlayer!!.seekTo(p1)
                } else {
                    file?.let { f ->
                        assignMediaPlayer(f)
                        mediaPlayer!!.seekTo(p1)
                    }
                }
                if (lyricsFlag.get()) {
                    // 전체 가사 보기 화면일 경우
                } else {
                    // 음악 재생 화면일 경우
                    nowLyrics.set("")
                    nextLyrics.set("")
                }
                musicPlaying()
            }
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
        }
    }

    companion object {
        private val TAG = MainViewModel::class.java.name
    }

    init {
        // Splash 관련
        viewModelScope.launch {
            // 2초 delay 이후
            delay(2000)
            // Splash 제거를 위해 flag 를 true 로 set
            splashFlag.set(true)
        }

        requestFloApi()
    }

    override fun onCleared() {
        // MediaPlayer 해제
        mediaPlayer?.release()
        mediaPlayer = null

        // CompositeDisposable 해제
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()

        super.onCleared()
    }

    /****************************************************
     * API 통신 관련 메서드
     ****************************************************/

    /**
     * 곡 정보를 가져오기 위해 API 통신
     */
    private fun requestFloApi() {
        CoroutineScope(Dispatchers.Main).launch {
            compositeDisposable.add(
                apiRepository.getFloStream()
                    .onErrorComplete {
                        Log.d(TAG, "${it.printStackTrace()}")
                        true
                    }
                    .subscribe { flo ->

                        singer.set(flo.singer)
                        album.set(flo.album)
                        title.set(flo.title)
                        image.set(flo.image)
                        file = flo.file
                        flo.lyrics.let { ly ->
                            ly.split("\n").map { s ->
                                val time = s.split("]")[0].replace("[", "")
                                val lyric = s.split("]")[1]

                                lyrics.add(Lyrics(time.timeToInt(), lyric, false, WHITE))
                            }
                        }

                        // playBar 의 노래 총 재생 시간 설정
                        val min = flo.duration / 60
                        val sec = flo.duration % 60
                        playTime.set("${String.format("%02d", min)}:${String.format("%02d", sec)}")
                    }
            )
        }
    }

    /****************************************************
     * 공용 메서드
     ****************************************************/

    /**
     * MediaPlayer 할당 및 시작
     */
    private fun assignMediaPlayer(file: String) {
        mediaPlayer = resourceProvider.assignMediaPlayer(file)
        mediaPlayer!!.start()
        // seekBar 의 max progress 설정
        maxProgress.set(mediaPlayer!!.duration)
    }

    /**
     * 현재 가사, 재생바, 재생 시간 표시
     */
    private fun musicPlaying() {
        viewModelScope.launch {
            mediaPlayer?.let { mp ->
                while (mp.isPlaying) {
                    val time = mp.currentPosition.convertTime()

                    val key = binarySearch(time)

                    if (key > -1) {
                        nowLyricsIndex.set(key)
                        if (lyricsFlag.get()) {
                            // 전체 가사 보기 화면일 경우
                            lyrics.filter { it.highlighting }.forEach {
                                it.highlighting = false
                                it.color = WHITE
                            }
                            lyrics[key] = lyrics[nowLyricsIndex.get()].let {
                                Lyrics(it.time, it.lyric, true, HIGHLIGHT)
                            }
                        } else {
                            // 음악 재생 화면일 경우
                            nowLyrics.set(lyrics[key].lyric)
                            if (key + 1 < lyrics.size) nextLyrics.set(lyrics[key + 1].lyric)
                            else nextLyrics.set("")
                        }
                    }

                    // 재생 시간 표시
                    nowPlayTime.set(mp.currentPosition.timeToStringMS())
                    // 재생바 움직임
                    progress.set(mp.currentPosition)

                    // 0.1초마다 반복
                    delay(100)
                }
            }
        }
    }

    /**
     * 현재 가사 이진 탐색
     */
    private fun binarySearch(key: Int): Int {
        var low = 0
        var mid: Int
        var high = lyrics.size - 1

        while (low <= high) {
            mid = low + (high - low) / 2

            if (lyrics[mid].time == key)
                return mid
            else if (lyrics[mid].time > key)
                high = mid - 1
            else
                low = mid + 1
        }
        return -1
    }

    /****************************************************
     * 음악 재생 화면 관련 메서드
     ****************************************************/

    /**
     * 재생 버튼 클릭 시
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun playButtonOnClicked() {
        // mediaPlayer 가 null 이 아닐 경우
        mediaPlayer?.apply {
            when (isPlaying) {
                true -> {
                    // mediaPlayer 가 play 중일 때는, pause()
                    pause()
                    resId.set(R.drawable.ic_baseline_play)
                }
                false -> {
                    // mediaPlayer 가 play 중이 아닐 때는, start()
                    start()
                    resId.set(R.drawable.ic_baseline_pause)
                }
            }
        }
        // mediaPlayer 가 null 인 경우
            ?: run {
                file?.let { f ->
                    // mediaPlayer 를 할당받아서 start()
                    assignMediaPlayer(f)
                    resId.set(R.drawable.ic_baseline_pause)
                }
            }

        // 현재 가사 표시
        musicPlaying()

    }

    /****************************************************
     * 음악 재생 화면 관련 메서드
     ****************************************************/

    /**
     * 음악 재생 화면에서 현재 가사 클릭 시
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun nowLyricsClicked() {
        lyricsFlag.set(true)

        lyrics.filter { it.highlighting }.forEach {
            it.highlighting = false
            it.color = WHITE
        }
        // 전체 가사 보기 화면에서 현재 가사가 highlighting 될 수 있도록
        if (nowLyricsIndex.get() > -1) {
            lyrics[nowLyricsIndex.get()] =
                lyrics[nowLyricsIndex.get()].let {
                    Lyrics(it.time, it.lyric, true, HIGHLIGHT)
                }
        }
    }

    /****************************************************
     * 전체 가사 보기 화면 관련 메서드
     ****************************************************/

    /**
     * 닫기 버튼 클릭 시
     */
    fun closeButtonClicked() {
        lyricsFlag.set(false)

        if (nowLyricsIndex.get() > -1) {
            nowLyrics.set(lyrics[nowLyricsIndex.get()].lyric)
            if (nowLyricsIndex.get() + 1 < lyrics.size) nextLyrics.set(lyrics[nowLyricsIndex.get() + 1].lyric)
            else nextLyrics.set("")
        }
    }

    /**
     * 가사 검색 버튼 클릭 시
     */
    fun searchButtonClicked(view: View) {
        isSearchModeOn.set((view as ToggleButton).isChecked)

        if (view.isChecked) view.setTextColor(Color.BLACK)
        else view.setTextColor(Color.WHITE)
    }

    /**
     * 전체 가사 보기 화면에서 가사 클릭 시
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun oneLyricClicked(lyrics: Lyrics) {
        if (isSearchModeOn.get()) {
            val moveLyrics = lyrics.time

            if (mediaPlayer == null) {
                file?.let { f ->
                    assignMediaPlayer(f)
                    mediaPlayer!!.seekTo(moveLyrics)
                }
            } else {
                mediaPlayer!!.start()
                mediaPlayer!!.seekTo(moveLyrics)
            }

            resId.set(R.drawable.ic_baseline_pause)
            musicPlaying()
        } else {
            lyricsFlag.set(false)
        }
    }
}