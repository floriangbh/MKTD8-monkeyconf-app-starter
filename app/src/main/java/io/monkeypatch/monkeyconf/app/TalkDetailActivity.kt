package io.monkeypatch.monkeyconf.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*

class TalkDetailActivity : AppCompatActivity(), TalkDetailView {

    private val TAG = "TalkDetailActivity"

    private val presenter = TalkDetailPresenter(this)

    override fun displayError(e: Exception) {
        Toast.makeText(this, "Error displaying talk", Toast.LENGTH_LONG)
        Log.e(TAG, "Error displaying talk", e)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        setContentView(R.layout.activity_detail)

        favoriteBtn.setOnClickListener {
            presenter.markFavorite()
        }

        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onResume() {
        super.onResume()

        val id = intent.getStringExtra("talkId")
        presenter.loadDetails(id)
    }

    override fun displayTalk(talk: Talk, favorite: Boolean) {
        supportActionBar?.title = talk.title
        speakersTextView.text = talk.speakerList()
        hourTextView.text = talk.timeSlot()
        roomTextView.text = talk.roomDetail()

        descriptionTextView.text = talk.description
        favoriteBtn.setImageResource(if (favorite) R.drawable.ic_star_on else R.drawable.ic_star_off)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> false
        }
}