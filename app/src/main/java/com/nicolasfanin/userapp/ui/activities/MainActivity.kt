package com.nicolasfanin.userapp.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.nicolasfanin.userapp.R
import com.nicolasfanin.userapp.ui.data.model.User
import com.nicolasfanin.userapp.ui.fragments.ProfileDetailsFragment
import com.nicolasfanin.userapp.ui.fragments.ProfileSearchFragment

class MainActivity : AppCompatActivity(), ProfileSearchFragment.ProfileSearchListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ProfileSearchFragment.newInstance(), "profileList")
                .commit()
        }
    }

    override fun navigateToProfileDetails(user: User) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ProfileDetailsFragment.newInstance(user), "profileDetailsList")
            .addToBackStack("profileDetailsList")
            .commit()
    }
}
