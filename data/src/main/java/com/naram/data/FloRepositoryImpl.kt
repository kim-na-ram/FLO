package com.naram.data

import com.naram.data.ds.FloRemoteSource
import com.naram.domain.model.FloRepo
import com.naram.domain.repo.FloRepository
import javax.inject.Inject

class FloRepositoryImpl @Inject constructor(
    private val floRemoteSource: FloRemoteSource
) : FloRepository {

    override suspend fun getRepos(): FloRepo {
        return floRemoteSource.getRepos()
    }
}