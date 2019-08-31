package com.nicolasfanin.userapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import com.nicolasfanin.userapp.R
import com.nicolasfanin.userapp.ui.activities.MainActivity
import com.nicolasfanin.userapp.ui.fragments.adapters.UserAdapter
import com.nicolasfanin.userapp.data.UserRepositoryProvider
import com.nicolasfanin.userapp.data.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile_search.*
import android.view.View.OnAttachStateChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicolasfanin.userapp.ui.fragments.adapters.FavouriteUserAdapter

class ProfileSearchFragment : Fragment() {

    companion object {
        fun newInstance(): ProfileSearchFragment {
            return ProfileSearchFragment()
        }
    }

    private lateinit var listener: ProfileSearchListener
    private lateinit var favouriteUserRecyclerView: RecyclerView
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: List<User>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_search, container, false)

        val searchToolbar = view.findViewById<Toolbar>(R.id.search_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(searchToolbar)

        favouriteUserRecyclerView = view.findViewById(R.id.favourite_user_recycler_view)
        userRecyclerView = view.findViewById(R.id.user_recycler_view)

        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = (activity as ProfileSearchListener)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val repository = UserRepositoryProvider.provideUserRepository()

        //TODO: this should be placed in a FragmentPresenter
        repository.getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                userList = result.results
                if (userList.isNotEmpty()) {
                    processUserList();
                    updateUi(userList)
                    hideProgressBar()
                }
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun updateUi(userList: List<User>) {
        //Favourite user section
        favouriteUserRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            val favouriteUserAdapter = FavouriteUserAdapter(context)
            adapter = favouriteUserAdapter
            (activity as MainActivity).favouriteUserViewModel.allUsers.observe(
                (activity as MainActivity),
                Observer { user -> user?.let { favouriteUserAdapter.setUsers(it) } })
        }


        // User List section
        val itemOnClick: (Int) -> Unit = { position ->
            userRecyclerView.adapter!!.notifyDataSetChanged()
            listener.navigateToProfileDetails(userList[position])
        }

        userRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = UserAdapter(userList, itemOnClick)
        }
    }

    private fun processUserList() {
        for (user: User in userList) {
            user.completeUserName = """${user.name!!.title} ${user.name.first} ${user.name.last}"""
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
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                //TODO: add recomendations
                Log.d("SEARCH", newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                var filterList = userList.filter { it.completeUserName!!.contains(query) }

                updateUi(if (filterList.isEmpty()) userList else filterList)
                return false
            }
        })

        searchView.addOnAttachStateChangeListener(object : OnAttachStateChangeListener {

            override fun onViewDetachedFromWindow(arg0: View) {
                updateUi(userList)
            }

            override fun onViewAttachedToWindow(arg0: View) {
                // no operation
            }
        })
    }

    interface ProfileSearchListener {

        fun navigateToProfileDetails(user: User)

    }
}
