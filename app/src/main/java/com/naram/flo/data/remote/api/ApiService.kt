package com.naram.flo.data.remote.api

import com.naram.flo.data.remote.model.Flo
import retrofit2.http.GET

interface ApiService {
    @GET("2020-flo/song.json")
    suspend fun getFlo(): Flo
}