package com.naram.data.model

import com.google.gson.annotations.SerializedName
import com.naram.domain.model.FloRepo

data class FloRepoRes(
    @SerializedName("album")
    val _album: String,
    @SerializedName("duration")
    val _duration: Int,
    @SerializedName("file")
    val _file: String,
    @SerializedName("image")
    val _image: String,
    @SerializedName("lyrics")
    val _lyrics: String,
    @SerializedName("singer")
    val _singer: String,
    @SerializedName("title")
    val _title: String
) : FloRepo {
    override val album: String
        get() = _album

    override val duration: Int
        get() = _duration

    override val file: String
        get() = _file

    override val image: String
        get() = _image

    override val lyrics: String
        get() = _lyrics

    override val singer: String
        get() = _singer

    override val title: String
        get() = _title
}