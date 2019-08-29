package com.nicolasfanin.userapp.ui.fragments.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.nicolasfanin.userapp.R
import com.nicolasfanin.userapp.data.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_item.view.*

class UserAdapter(private val userList: List<User>, private val listener: (Int) -> Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: User = userList[position]
        holder.bind(user,listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = userList.size

    class UserViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.user_item, parent, false)) {

        private var userNameTextView: TextView? = null
        private var subtitleTextView: TextView? = null

        init {
            userNameTextView = itemView.findViewById(R.id.name_text_view)
            subtitleTextView = itemView.findViewById(R.id.subtitle_text_view)
        }

        fun bind(user: User, itemClickListener:(Int)->Unit) {
            userNameTextView?.text = """${user.name!!.title} ${user.name!!.first} ${user.name.last}"""
            subtitleTextView?.text = user.gender

            Picasso.get()
                .load(user.picture!!.thumbnail)
                .into(itemView.avatar_view)

            itemView.setOnClickListener{itemClickListener(adapterPosition)}
        }
    }
}
