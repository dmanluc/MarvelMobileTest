package dev.dmanluc.openbankmobiletest.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dev.dmanluc.openbankmobiletest.R
import dev.dmanluc.openbankmobiletest.presentation.characters.CharactersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MarvelActivity : AppCompatActivity() {

    private val viewModel: CharactersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        setContentView(R.layout.activity_main)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
            show()
        }

        viewModel.characterListLiveData.observe(this) {
            println(it)
        }
    }

}