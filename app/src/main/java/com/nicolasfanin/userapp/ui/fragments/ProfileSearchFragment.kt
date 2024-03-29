package com.nicolasfanin.userapp.ui.fragments

import android.os.Bundle
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
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicolasfanin.userapp.data.model.ServiceInfo
import com.nicolasfanin.userapp.data.model.UserWrapper
import com.nicolasfanin.userapp.data.repository.UserRepository
import com.nicolasfanin.userapp.ui.fragments.adapters.FavouriteUserAdapter
import java.lang.StringBuilder

/**
 * Add String extension function to format the names.
 * Adds a capital letter to each
 */
fun String.nameFormat() : String {
    val EMPTY_SPACE = " "
    val array = this.split(EMPTY_SPACE)
    val expression = StringBuilder()
    for (a in array) {
        expression.append(a.capitalize()).append(EMPTY_SPACE)
    }
    return expression.toString()
}

class ProfileSearchFragment : Fragment() {

    companion object {
        fun newInstance(): ProfileSearchFragment {
            return ProfileSearchFragment()
        }
    }

    private val SEED = "profileSearch"

    private lateinit var listener: ProfileSearchListener
    private lateinit var favouriteUserRecyclerView: RecyclerView
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: List<User>
    private lateinit var repository: UserRepository
    private lateinit var serviceInfo: ServiceInfo
    private var page: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_search, container, false)

        val searchToolbar = view.findViewById<Toolbar>(R.id.search_toolbar)
        (activity as MainActivity).setSupportActionBar(searchToolbar)

        favouriteUserRecyclerView = view.findViewById(R.id.favourite_user_recycler_view)
        userRecyclerView = view.findViewById(R.id.user_recycler_view)

        setHasOptionsMenu(true)
        repository = UserRepositoryProvider.provideUserRepository()
        loadData()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = (activity as ProfileSearchListener)
    }

    private fun loadData() {
        repository.getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                serviceInfo = result.info
                userList = result.results
                if (userList.isNotEmpty()) {
                    processUserList()
                    updateUi(userList, false)
                    hideProgressBar()
                }
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun updateUi(userList: List<User>, isSearching: Boolean) {
        favouriteUserRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            val favouriteUserAdapter = FavouriteUserAdapter(context)
            adapter = favouriteUserAdapter

            val favouriteItemOnClick: (Int) -> Unit = { position ->
                favouriteUserRecyclerView.adapter!!.notifyDataSetChanged()
                val favUser = (activity as MainActivity).favouriteUserViewModel.allUsers.value!![position]
                listener.navigateToProfileDetails(UserWrapper(favUser).getUser())
            }

            (activity as MainActivity).favouriteUserViewModel.allUsers.observe(
                (activity as MainActivity),
                Observer { user ->
                    user?.let { favouriteUserAdapter.setUsers(it, favouriteItemOnClick) }.also {
                        favouriteUserRecyclerView.visibility =
                            if (shouldShowFavouritesUserSection()) View.VISIBLE else View.GONE
                    }
                })
        }

        // User List section
        val itemOnClick: (Int) -> Unit = { position ->
            userRecyclerView.adapter!!.notifyDataSetChanged()
            listener.navigateToProfileDetails(userList[position])
        }

        val userLinearLayoutManager = LinearLayoutManager(activity)
        userRecyclerView.apply {
            layoutManager = userLinearLayoutManager
            adapter = UserAdapter(userList, itemOnClick)
        }

        chargeMoreItemsWhenScrollToEnd(repository, userLinearLayoutManager, isSearching)

        if (isSearching) {
            hideFavouriteUserSection()
        } else {
            showFavouriteUserSection()
        }
    }

    private fun chargeMoreItemsWhenScrollToEnd(
        repository: UserRepository,
        userLinearLayoutManager: LinearLayoutManager,
        isSearching: Boolean
    ) {
        if (isSearching) {
            userRecyclerView.clearOnScrollListeners()
            return
        }

        userRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isSearching && userLinearLayoutManager.findLastCompletelyVisibleItemPosition() >= userLinearLayoutManager.itemCount - 1) {

                    repository.getPaginationUsers(page, SEED)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            serviceInfo = result.info

                            userList = userList + result.results
                            if (userList.isNotEmpty()) {
                                processUserList()

                                val itemOnClick: (Int) -> Unit = { position ->
                                    userRecyclerView.adapter!!.notifyDataSetChanged()
                                    listener.navigateToProfileDetails(userList[position])
                                }

                                userRecyclerView.apply {
                                    adapter = UserAdapter(userList, itemOnClick)
                                }

                                recyclerView.scrollToPosition(userLinearLayoutManager.itemCount - 2)
                            }
                            page++
                        }, { error ->
                            error.printStackTrace()
                        })
                }
            }
        })
    }

    private fun processUserList() {
        for (user: User in userList) {
            user.completeUserName = "${user.name!!.title} ${user.name.first} ${user.name.last}".nameFormat()
        }
    }

    private fun hideProgressBar() {
        progress_bar.visibility = View.GONE
        favouriteUserRecyclerView.visibility = if (shouldShowFavouritesUserSection()) View.VISIBLE else View.GONE
        userRecyclerView.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
        userRecyclerView.visibility = View.INVISIBLE
        favouriteUserRecyclerView.visibility = View.INVISIBLE
    }

    private fun hideFavouriteUserSection() {
        favouriteUserRecyclerView.visibility = View.GONE
    }

    private fun showFavouriteUserSection() {
        favouriteUserRecyclerView.visibility = if (shouldShowFavouritesUserSection()) View.VISIBLE else View.GONE
    }

    private fun shouldShowFavouritesUserSection(): Boolean {
        if (favouriteUserRecyclerView?.adapter!!.itemCount > 0) return true
        return false
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
                var filterList = userList.filter { it.completeUserName!!.contains(newText, true) }
                updateUi(if (filterList.isEmpty()) userList else filterList, true)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                var filterList = userList.filter { it.completeUserName!!.contains(query, true) }

                updateUi(if (filterList.isEmpty()) userList else filterList, true)
                if (filterList.isEmpty()) {
                    Toast.makeText(context, "No results", Toast.LENGTH_SHORT).show()
                }
                return false
            }
        })

        searchView.queryHint = getString(R.string.search_users_hint)

        searchView.addOnAttachStateChangeListener(object : OnAttachStateChangeListener {

            override fun onViewDetachedFromWindow(arg0: View) {
                updateUi(userList, false)
            }

            override fun onViewAttachedToWindow(arg0: View) {
                updateUi(userList, true)
            }
        })
    }

    interface ProfileSearchListener {

        fun navigateToProfileDetails(user: User)

    }
}
