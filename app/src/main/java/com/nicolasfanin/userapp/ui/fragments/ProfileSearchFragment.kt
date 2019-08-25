package com.nicolasfanin.userapp.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import com.nicolasfanin.userapp.R
import com.nicolasfanin.userapp.ui.activities.MainActivity
import com.nicolasfanin.userapp.ui.adapters.UserAdapter
import com.nicolasfanin.userapp.ui.data.UserRepositoryProvider
import com.nicolasfanin.userapp.ui.data.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile_search.*

class ProfileSearchFragment : Fragment() {

    private lateinit var userList: List<User>

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
        val repository = UserRepositoryProvider.provideUserRepository()

        repository.getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result ->
                Log.d("Result", "There are ${result.results.get(0)} Java developers in Lagos")
                userList = result.results
                updateUi()
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun updateUi() {
        user_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = UserAdapter(userList)
        }
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