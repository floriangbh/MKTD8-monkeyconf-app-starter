package io.monkeypatch.monkeyconf.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_talk.view.*

class TalkListActivity : AppCompatActivity(), TalkListView {
    private val TAG = "TalkListActivity"

    override fun displayError(e: Exception) {
        Toast.makeText(this, "Error loading talks", Toast.LENGTH_LONG)
        Log.e(TAG, "Error loading talks", e)
    }

    val presenter = TalkListPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TalkListActivity)
            setHasFixedSize(true)
        }

        showLoading(false)

        presenter.onCreate()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as? SearchView
        searchView?.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { filterUpdated(it) }
                    return true
                }
            })
        }
        return true
    }

    override fun showLoading(loading: Boolean) {
        progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun filterUpdated(text: String) {
        presenter.filter(text)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadTalks()
    }

    fun View.dismissKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showTalks(talks: List<Talk>) {
        recyclerView.swapAdapter(TalkListAdapter(
            talks,
            this::openTalk),
            false
        )
    }

    private fun openTalk(talkId: String) {
        startActivity(
            Intent(this, TalkDetailActivity::class.java).putExtra("talkId", talkId)
        )
    }
}


class TalkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun displayTalk(summary: Talk, action: (String) -> Unit) {
        itemView.talkHourTextView.text =  "${summary.startTime.take(5)}\n${summary.endTime.take(5)}"
        itemView.talkTitleTextView.text = summary.title
        itemView.talkSubTitleTextView.text = summary.description.take(200)
        itemView.setOnClickListener { action(summary.id) }
    }
}

class TalkListAdapter(val talks: List<Talk>, val action: (String) -> Unit) : RecyclerView.Adapter<TalkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): TalkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_talk, parent, false)
        return TalkViewHolder(view)
    }

    override fun getItemCount() = talks.size

    override fun onBindViewHolder(viewHolder: TalkViewHolder, pos: Int) {
        viewHolder.displayTalk(talks[pos], action)
    }

}