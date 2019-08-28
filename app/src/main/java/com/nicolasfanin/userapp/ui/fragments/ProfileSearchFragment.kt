package com.nicolasfanin.userapp.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.Toast
import com.nicolasfanin.userapp.R
import com.nicolasfanin.userapp.ui.activities.MainActivity
import com.nicolasfanin.userapp.ui.adapters.UserAdapter
import com.nicolasfanin.userapp.ui.data.UserRepositoryProvider
import com.nicolasfanin.userapp.ui.data.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile_search.*

class ProfileSearchFragment : Fragment() {

    companion object {
        fun newInstance(): ProfileSearchFragment {
            return ProfileSearchFragment()
        }
    }

    private lateinit var listener: ProfileSearchListener
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: List<User>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_search, container, false)

        val searchToolbar = view.findViewById<Toolbar>(R.id.search_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(searchToolbar)

        userRecyclerView = view.findViewById(R.id.user_recycler_view)

        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = (activity as ProfileSearchListener)
        loadData()
    }

    //TODO: this should be placed in a FragmentPresenter
    private fun loadData() {
        val repository = UserRepositoryProvider.provideUserRepository()

        repository.getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                Log.d("Result", "There are ${result.results.get(0)} Java developers in Lagos")
                userList = result.results
                updateUi()
                hideProgressBar()
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun updateUi() {
        val itemOnClick: (Int) -> Unit = { position ->
            userRecyclerView.adapter!!.notifyDataSetChanged()
            Toast.makeText(this.context,"$position. item clicked.",Toast.LENGTH_SHORT).show()
            listener.navigateToProfileDetails(userList.get(position))
        }

        userRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = UserAdapter(userList, itemOnClick)
        }
    }

    private fun hideProgressBar() {
        progress_bar.visibility = View.GONE
        userRecyclerView.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
        userRecyclerView.visibility = View.INVISIBLE

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

    interface ProfileSearchListener {

        fun navigateToProfileDetails(user: User)

    }
}