package com.example.akash.rxkotlinsample

import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
  @GET(".")
  fun getMyFriends() : Single<Friends>
}