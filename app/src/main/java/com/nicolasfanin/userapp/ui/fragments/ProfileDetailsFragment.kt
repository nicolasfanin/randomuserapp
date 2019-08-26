package com.nicolasfanin.userapp.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nicolasfanin.userapp.R
import com.nicolasfanin.userapp.ui.data.model.User
import com.nicolasfanin.userapp.ui.data.model.UserData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile_details.*

class ProfileDetailsFragment : Fragment() {

    private lateinit var user: UserData

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
        user = arguments?.getSerializable(USER_ARGUMENT) as UserData
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    fun loadData() {

        Picasso.get()
            .load(user.user.picture!!.large)
            .into(profile_image)
    }

}
