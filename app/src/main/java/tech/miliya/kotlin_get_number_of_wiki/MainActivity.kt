package tech.miliya.kotlin_get_number_of_wiki

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import tech.miliya.kotlin_get_number_of_wiki.api.WikiApiService


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    val wikiApiServe by lazy {
        WikiApiService.create()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var editTextSearch = findViewById<EditText>(R.id.editTextSearch)
        var buttonSearch = findViewById<Button>(R.id.buttonSearch)

        buttonSearch.setOnClickListener { view ->
            val param = editTextSearch.text.toString()
            beginSearch(param)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()

    }

    private fun beginSearch(srsearch: String) {
        var textSearchResult = findViewById<TextView>(R.id.textSearchResult)

        disposable =
                wikiApiServe.hitCountCheck("query", "json", "search", srsearch)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result ->
                                    textSearchResult.text = (result.query.searchinfo.totalhits).toString()
                                },
                                { error ->
                                    Log.w(TAG, error.message)
                                }
                        )
    }

}
