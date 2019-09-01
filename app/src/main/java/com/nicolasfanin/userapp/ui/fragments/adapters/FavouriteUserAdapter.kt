package com.nicolasfanin.userapp.ui.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nicolasfanin.userapp.R
import com.nicolasfanin.userapp.data.model.FavouriteUser
import com.squareup.picasso.Picasso

class FavouriteUserAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<FavouriteUserAdapter.FavouriteUserViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var favouriteUsersList = emptyList<FavouriteUser>()
    private lateinit var listener: (Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteUserViewHolder {
        val itemView = inflater.inflate(R.layout.favourite_user_item, parent, false)
        return FavouriteUserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavouriteUserViewHolder, position: Int) {
        val currentUser = favouriteUsersList[position]
        holder.favouriteNameView.text = currentUser.completedUserName

        Picasso.get()
            .load(currentUser.pictureThumbnail)
            .into(holder.favouriteAvatarView)

        holder.itemView.setOnClickListener { listener(position) }
    }

    override fun getItemCount() = favouriteUsersList.size

    internal fun setUsers(favouriteUsersList: List<FavouriteUser>, listener: (Int) -> Unit) {
        this.favouriteUsersList = favouriteUsersList
        this.listener = listener
        notifyDataSetChanged()
    }

    inner class FavouriteUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val favouriteAvatarView: ImageView = itemView.findViewById(R.id.favourite_avatar_view)
        val favouriteNameView: TextView = itemView.findViewById(R.id.name_text_view)
    }

}
