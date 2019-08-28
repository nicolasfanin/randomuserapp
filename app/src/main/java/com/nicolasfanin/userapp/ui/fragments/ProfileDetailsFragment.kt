package com.nicolasfanin.userapp.ui.fragments

import android.app.Activity
import android.content.ContentProviderOperation
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nicolasfanin.userapp.R
import com.nicolasfanin.userapp.ui.data.model.User
import com.nicolasfanin.userapp.ui.data.model.UserData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile_details.*
import kotlinx.android.synthetic.main.user_detail_layout.*


class ProfileDetailsFragment : Fragment() {

    private var EMPTY = ""
    private var ERROR_INPUT_EMPTY = "Please fill all fields"
    private var SAVED = "Saved!"
    private var myPreferences = "myPrefs"
    private var NAME = "name"
    private var PHONE_NUMBER = "phoneNumber"
    private var EMAIL = "email"

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
        save_contact_floating_button.setOnClickListener {
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

    private fun saveContactAsFavourite() {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putString(
            NAME,
            """${userData.user.name!!.title}
                    |${userData.user.name!!.first}
                    |${userData.user.name!!.last}""".trimMargin()
        )
        editor.putString(PHONE_NUMBER, userData.user.phone)
        editor.apply()
    }



    interface ProfileDetailsListener {

        fun addNewContact(userData: UserData)

    }

}
