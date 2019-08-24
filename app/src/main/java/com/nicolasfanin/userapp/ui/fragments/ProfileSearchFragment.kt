package com.nicolasfanin.userapp.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.*
import com.nicolasfanin.userapp.R
import com.nicolasfanin.userapp.ui.activities.MainActivity

class ProfileSearchFragment : Fragment() {

    companion object {
        fun newInstance(): ProfileSearchFragment {
            return ProfileSearchFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_profile_search, container, false)

        var searchToolbar = view.findViewById<Toolbar>(R.id.search_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(searchToolbar)


        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu!!.clear()
        inflater?.inflate(R.menu.search_menu, menu)
        val searchView = SearchView((context as MainActivity).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.action_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }
    }
}