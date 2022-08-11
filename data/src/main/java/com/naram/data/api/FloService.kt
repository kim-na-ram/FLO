package com.naram.data.api

import com.naram.data.model.FloRepoRes
import retrofit2.http.GET

interface FloService {
    @GET("2020-flo/song.json")
    suspend fun getRepos(): FloRepoRes
}