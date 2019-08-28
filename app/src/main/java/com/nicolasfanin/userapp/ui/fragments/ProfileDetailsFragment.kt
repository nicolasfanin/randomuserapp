package com.nicolasfanin.userapp.ui.fragments

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nicolasfanin.userapp.R
import com.nicolasfanin.userapp.ui.data.model.User
import com.nicolasfanin.userapp.ui.data.model.UserData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile_details.*
import kotlinx.android.synthetic.main.user_detail_layout.*

class ProfileDetailsFragment : Fragment() {

    private lateinit var userData: UserData

    companion object {
        private const val USER_ARGUMENT = "USER"

        fun newInstance(user: User): ProfileDetailsFragment {
            val args = Bundle()
            args.putSerializable(USER_ARGUMENT, UserData(user))
            val profileDetailsFragment = ProfileDetailsFragment()
            profileDetailsFragment.arguments = args
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
        loadData()
    }

    fun loadData() {
        Picasso.get()
            .load(userData.user.picture!!.large)
            .into(expandedImage)

        profile_toolbar.title =
            """${userData.user.name!!.title} ${userData.user.name!!.first} ${userData.user.name!!.last}"""

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            content_data.text = Html.fromHtml(
                contentDescription.toString(), Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            content_data.text = Html.fromHtml(contentDescription.toString())
        }
    }

}
