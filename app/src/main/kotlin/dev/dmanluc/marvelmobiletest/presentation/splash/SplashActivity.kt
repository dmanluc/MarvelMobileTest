package dev.dmanluc.marvelmobiletest.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.dmanluc.marvelmobiletest.presentation.MarvelActivity

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Initial app activity to show splash screen
 *
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MarvelActivity::class.java))
        finish()
    }

}