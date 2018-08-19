package com.example.akash.rxkotlinsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.MainThread
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.recyclerView

class MainActivity : AppCompatActivity() {

  var data: Array<UserItem?>? = null
  var adapter: FriendsAdapter? = null
  var apiService: ApiService? = null
  var disposable : CompositeDisposable= CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    initViews()
    apiService= ApiClient.client.create(ApiService::class.java)
  }

  override fun onResume() {
    super.onResume()
    fetchData()
  }

  private fun fetchData(){
    apiService!!.getMyFriends()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap { friends -> getUserItemObservable(friends) }
        .subscribeWith(object : DisposableSingleObserver<Array<UserItem?>>() {
          override fun onSuccess(userItemArray: Array<UserItem?>) {
            adapter!!.updateData(userItemArray)
          }

          override fun onError(e: Throwable) {
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT)
                .show()
          }
        })
  }

  private fun getUserItemObservable(friends: Friends) : Single<Array<UserItem?>>{
    val userItemArray = arrayOfNulls<UserItem>(friends.user!!.size)
    for(i in 0 until friends.user.size )
      userItemArray[i]= friends.user[i]

    return Single.create { emitter -> if (!emitter.isDisposed){
      emitter.onSuccess(userItemArray)
    } }
  }

  private fun initViews(){
    adapter= FriendsAdapter(null);
    recyclerView.layoutManager = LinearLayoutManager(this );
    recyclerView.hasFixedSize()
    recyclerView.adapter = adapter
  }

  override fun onDestroy() {
    super.onDestroy()
    disposable.dispose()
  }
}
