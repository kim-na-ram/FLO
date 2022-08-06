package com.naram.flo.data.remote.api

import com.naram.flo.data.remote.model.Flo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val service: ApiService
) {
    private val getFloResult = BehaviorSubject.create<Flo>()

    suspend fun getFloStream(): Observable<Flo> {
        requestFlo()
        return getFloResult.ofType(Flo::class.java)
    }

    private suspend fun requestFlo(): Boolean {
        return try {
            val response = service.getFlo()
            getFloResult.onNext(response)
            true
        } catch (exception: HttpException) {
            getFloResult.onError(exception)
            false
        } catch (exception: IOException) {
            getFloResult.onError(exception)
            false
        }
    }
}