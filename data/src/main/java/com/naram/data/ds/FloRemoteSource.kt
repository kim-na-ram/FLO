package com.naram.data.ds

import com.naram.data.model.FloRepoRes

interface FloRemoteSource {
    suspend fun getRepos(): FloRepoRes
}