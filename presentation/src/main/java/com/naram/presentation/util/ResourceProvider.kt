package com.naram.presentation.util

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * ResourceProvider
 * - viewModel 에서 context 를 사용해야 하는 경우 ResourceProvider 에서 처리
 */
class ResourceProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    /**
     * mediaPlayer 를 생성하고 반환
     */
    fun assignMediaPlayer(file: String): MediaPlayer {
        return MediaPlayer().apply {
            setDataSource(context, Uri.parse(file))
            // dataSource 설정 후 prepare() 호출 필수
            prepare()
        }
    }
}