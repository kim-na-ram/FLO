package com.naram.flo.data.local.model

data class Lyrics(
    val time: Int,
    val lyric: String,
    var highlighting: Boolean,
    var color: String
)
