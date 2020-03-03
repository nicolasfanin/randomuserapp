package com.nicolasfanin.userapp.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.nicolasfanin.userapp.R
import com.nicolasfanin.userapp.data.model.User
import com.nicolasfanin.userapp.data.model.UserData
import com.nicolasfanin.userapp.data.viewModel.FavouriteUserViewModel
import com.nicolasfanin.userapp.ui.fragments.ProfileDetailsFragment
import com.nicolasfanin.userapp.ui.fragments.ProfileSearchFragment

class MainActivity : AppCompatActivity(), ProfileSearchFragment.ProfileSearchListener,
    ProfileDetailsFragment.ProfileDetailsListener {

    lateinit var favouriteUserViewModel: FavouriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeComponents()

        if (savedInstanceState == null) {
            navigateToSeachFragment()
        }
    }

    private fun initializeComponents() {
        favouriteUserViewModel = ViewModelProviders.of(this).get(FavouriteUserViewModel::class.java)
    }

    fun navigateToSeachFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ProfileSearchFragment.newInstance(), "profileList")
            .commit()
    }

    override fun navigateToProfileDetails(user: User) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ProfileDetailsFragment.newInstance(user))
            .addToBackStack(null)
            .commit()
    }

    override fun addNewContact(userData: UserData) {
        var displayName = """${userData.user.name!!.first} ${userData.user.name!!.last}"""
        var phoneNumber = userData.user.phone

        var addContactIntent = Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI)
        addContactIntent.type = ContactsContract.Contacts.CONTENT_TYPE

        addContactIntent
            .putExtra(ContactsContract.Intents.Insert.NAME, displayName)
            .putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber)

        startActivityForResult(addContactIntent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Added Contact", Toast.LENGTH_SHORT).show()
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled Added Contact", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
