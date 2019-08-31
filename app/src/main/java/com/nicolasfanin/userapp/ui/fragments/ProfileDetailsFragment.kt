package com.nicolasfanin.userapp.ui.fragments

import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.nicolasfanin.userapp.R
import com.nicolasfanin.userapp.data.model.FavouriteUser
import com.nicolasfanin.userapp.data.model.User
import com.nicolasfanin.userapp.data.model.UserData
import com.nicolasfanin.userapp.data.viewModel.FavouriteUserViewModel
import com.nicolasfanin.userapp.ui.activities.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile_details.*


class ProfileDetailsFragment : Fragment() {

    private var NAME = "name"
    private var PHONE_NUMBER = "phoneNumber"

    private lateinit var listener: ProfileDetailsListener
    private lateinit var userData: UserData

    companion object {
        private const val USER_ARGUMENT = "USER"

        fun newInstance(user: User): ProfileDetailsFragment {
            val arguments = Bundle()
            arguments.putSerializable(USER_ARGUMENT, UserData(user))
            val profileDetailsFragment = ProfileDetailsFragment()
            profileDetailsFragment.arguments = arguments
            return profileDetailsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_details, container, false)
        userData = arguments?.getSerializable(USER_ARGUMENT) as UserData
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = (activity as ProfileDetailsListener)
        loadData()
        nestedScroll.isNestedScrollingEnabled = true

        save_favourite_floating_button.setOnClickListener {
            saveContactAsFavourite()
        }

        save_contact_button.setOnClickListener {
            listener.addNewContact(userData)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(USER_ARGUMENT, userData)
    }

    fun loadData() {
        Picasso.get()
            .load(userData.user.picture!!.large)
            .into(expandedImage)

        profile_toolbar?.title = userData.user.completeUserName

        var contentDescription = StringBuilder()
            .append(getString(R.string.user_details_email, userData.user.email))
            .append(getString(R.string.user_details_user_name, userData.user.login!!.username))
            .append(getString(R.string.user_details_phone, userData.user.phone))
            .append(getString(R.string.user_details_cell, userData.user.cell))
            .append(getString(R.string.user_details_address))
            .append(getString(R.string.user_details_street, userData.user.location!!.street))
            .append(getString(R.string.user_details_city, userData.user.location!!.city))
            .append(getString(R.string.user_details_state, userData.user.location!!.state))
            .append(getString(R.string.user_details_post_code, userData.user.location!!.postcode))
            .append(getString(R.string.new_line))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            content_data_text_view.text = Html.fromHtml(
                contentDescription.toString(), Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            content_data_text_view.text = Html.escapeHtml(contentDescription.toString())
        }
    }

    private fun saveContactAsFavourite() {
        val favouriteUser = FavouriteUser(
            userData.user.id!!.value.toString(),
            userData.user.completeUserName.toString(),
            userData.user.picture!!.medium.toString())

        (activity as MainActivity).favouriteUserViewModel.insert(favouriteUser)
    }


    interface ProfileDetailsListener {

        fun addNewContact(userData: UserData)

    }

}
