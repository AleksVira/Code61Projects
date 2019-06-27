package ru.virarnd.userslistrestapicode61.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.virarnd.userslistrestapicode61.data.ReqresService
import ru.virarnd.userslistrestapicode61.data.User
import ru.virarnd.userslistrestapicode61.data.UsersListResponse
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity(), InfiniteScrollListener.OnLoadMoreListener {

    lateinit var recyclerAdapter: CustomAdapter
    lateinit var infiniteScrollListener: InfiniteScrollListener
    private val reqresService: ReqresService = ReqresService.create()
    private var refreshingInProcess: Boolean
    private var loadingInProcess: Boolean
    private var nextPageToLoad: Long
    private var totalPages: Long
    private val layoutManager: LinearLayoutManager = LinearLayoutManager(this)
    private var call: Call<UsersListResponse>? = null
    private var recyclerState: Parcelable?
    private var usersInstance: ArrayList<User> = arrayListOf()

    init {
        refreshingInProcess = false
        loadingInProcess = false
        recyclerState = null
        nextPageToLoad = 1L
        totalPages = Long.MAX_VALUE
    }

    companion object {
        const val LIST_STATE_KEY = "List State Key"
        const val NEXT_PAGE_KEY = "Next Page To Load"
        const val REFRESHING = "Refreshing state"
        const val RECYCLER_STATE_KEY = "Recycler State Key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ru.virarnd.userslistrestapicode61.R.layout.activity_main)
        if (savedInstanceState != null) {
            loadState(savedInstanceState)
            prepareView()
            recyclerAdapter.addUsers(usersInstance)
            (recycler_view.layoutManager as LinearLayoutManager).onRestoreInstanceState(recyclerState)
        } else {
            prepareView()
            onLoadMore()
//        recyclerAdapter.initAdapter(mutableListOf())
        }


    }

    private fun prepareView() {
        with(swipe_refresh_layout) {
            isEnabled = refreshingInProcess
//            isEnabled = false
            setOnRefreshListener { swipeDown() }
            setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN)
        }
        infiniteScrollListener = InfiniteScrollListener(layoutManager, this)
        infiniteScrollListener.setLoaded()
        recyclerAdapter = CustomAdapter { user: User -> userClicked(user) }
        recycler_view.addItemDecoration(RecipeListDecorator(8))
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = recyclerAdapter
        recycler_view.addOnScrollListener(infiniteScrollListener)
    }

    inner class UsersListResponseCallback : Callback<UsersListResponse> {
        override fun onFailure(call: Call<UsersListResponse>, t: Throwable) {
            turnOffWaitIndicators()
            Toast.makeText(applicationContext, "Pull to refresh list!", Toast.LENGTH_LONG).show()
            Timber.d { "Failure!" }
            swipe_refresh_layout.isEnabled = true
        }

        override fun onResponse(call: Call<UsersListResponse>, response: Response<UsersListResponse>) {
            turnOffWaitIndicators()
            val serverResponse = response.body()
            totalPages = serverResponse?.totalPages ?: Long.MAX_VALUE
            val userDataList = response.body()?.usersDataList
            if (userDataList.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "No more data!", Toast.LENGTH_SHORT).show()
                return
            }
            val newUsersList = userDataList.toMutableList()
            recyclerAdapter.addUsers(newUsersList)
            Timber.d { "response ${response.body()?.usersDataList?.get(1)?.email}" }
//            turnOffWaitIndicators()
            nextPageToLoad++
            infiniteScrollListener.setLoaded()
        }

        private fun turnOffWaitIndicators() {
            loadingInProcess = false
            refreshingInProcess = false
            recyclerAdapter.removeLastNullUser()
            if (swipe_refresh_layout.isRefreshing) {
                swipe_refresh_layout.isRefreshing = false
                swipe_refresh_layout.isEnabled = false
            }
        }
    }

    private fun swipeDown() {
        Timber.d { "Swiped!!!" }
        if (!refreshingInProcess) {
            refreshingInProcess = true
            Timber.d { "Trying to refresh" }
            onLoadMore()
        }
    }

    private fun userClicked(user: User) {
        Timber.d { "Users id clicked = ${user.id}" }
    }

    override fun onLoadMore() {
        if (loadingInProcess) {
            return
        }
//        Timber.d { "CurrentPage = $nextPageToLoad, TotalPages = $totalPages" }
        if (nextPageToLoad > totalPages) {
            Toast.makeText(applicationContext, "No more data!", Toast.LENGTH_SHORT).show()
            return
        }
        Timber.d { "Want load more!" }
        recyclerAdapter.addNullUser()
        loadingInProcess = true
        call = reqresService.getUsers(nextPageToLoad)
        call!!.enqueue(UsersListResponseCallback())
    }

    override fun onPause() {
        super.onPause()
        if (call != null) {
            call!!.cancel()
            recyclerAdapter.removeLastNullUser()
        }
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        with(outState!!) {
            putParcelableArrayList(LIST_STATE_KEY, recyclerAdapter.usersList.toCollection(ArrayList()))
            putParcelable(RECYCLER_STATE_KEY, recycler_view.layoutManager?.onSaveInstanceState())
            putLong(NEXT_PAGE_KEY, nextPageToLoad)
            putBoolean(REFRESHING, refreshingInProcess)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            loadState(savedInstanceState)
        }
    }

    private fun loadState(savedInstanceState: Bundle) {
        recyclerState = savedInstanceState.getParcelable(RECYCLER_STATE_KEY)
        usersInstance = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY)
        nextPageToLoad = savedInstanceState.getLong(NEXT_PAGE_KEY)
        refreshingInProcess = savedInstanceState.getBoolean(REFRESHING)
    }


    override fun onDestroy() {
        super.onDestroy()
        call?.cancel()
    }
}