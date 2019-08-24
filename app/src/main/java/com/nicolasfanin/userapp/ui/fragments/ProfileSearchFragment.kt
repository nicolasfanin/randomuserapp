package com.nicolasfanin.userapp.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nicolasfanin.userapp.R

class ProfileSearchFragment : Fragment() {

    companion object {
        fun newInstance(): ProfileSearchFragment {
            return ProfileSearchFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_search, container, false)
    }
}