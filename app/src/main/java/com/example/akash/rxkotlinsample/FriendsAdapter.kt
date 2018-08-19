package com.example.akash.rxkotlinsample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_layout.view.delete
import kotlinx.android.synthetic.main.item_layout.view.email
import kotlinx.android.synthetic.main.item_layout.view.name

class FriendsAdapter(private var data : Array<UserItem?>? ) : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>(){

  override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FriendsViewHolder {
    val view= LayoutInflater.from(p0.context).inflate(R.layout.item_layout, p0, false);
    view.isFocusable = true
    return FriendsViewHolder(view)
  }

  override fun onBindViewHolder(p0: FriendsViewHolder, p1: Int) {
    var userItem = data!![p1]

    p0.name.setText(userItem!!.name)
    p0.email.setText(userItem!!.email)
  }

  override fun getItemCount(): Int {
    return if (data == null) 0 else data!!.size
  }


  inner class FriendsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
      val name= itemView.name
      val email= itemView.email
      val delete= itemView.delete
  }

   fun updateData(userItemArray : Array<UserItem?>?){
    data = userItemArray
    notifyDataSetChanged()
  }

}