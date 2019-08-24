package com.nicolasfanin.userapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nicolasfanin.userapp.R
import com.nicolasfanin.userapp.ui.data.User

class UserAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: User = userList[position]
        holder.bind(user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = userList.size

    class UserViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.user_item, parent, false)) {

        private var avatarView: View? = null
        private var userNameTextView: TextView? = null
        private var subtitleTextView: TextView? = null

        init {
            avatarView = itemView.findViewById(R.id.avatar_view)
            userNameTextView = itemView.findViewById(R.id.name_text_view)
            subtitleTextView = itemView.findViewById(R.id.subtitle_text_view)
        }

        fun bind(user: User) {
            userNameTextView?.text = user.first + " " + user.last
            subtitleTextView?.text = user.gender
        }
    }
}
