package com.naram.data.ds

import com.naram.data.api.FloService
import com.naram.data.model.FloRepoRes
import javax.inject.Inject

class FloRemoteSourceImpl @Inject constructor(
    private val floService: FloService
) : FloRemoteSource {
    override suspend fun getRepos(): FloRepoRes {
        return floService.getRepos()
    }
}