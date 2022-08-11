package com.naram.presentation.activity.main.model

data class Lyrics(
    val time: Int,
    val lyric: String,
    var highlighting: Boolean,
    var color: String
)
