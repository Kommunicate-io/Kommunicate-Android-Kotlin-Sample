package io.kommunicate.sampleKT

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import io.kommunicate.Kommunicate
import io.kommunicate.users.KMUser

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            if (KMUser.isLoggedIn(this@SplashScreenActivity)) {
                Kommunicate.openConversation(this@SplashScreenActivity, null)
            } else {
                val intent = Intent(
                    this@SplashScreenActivity, LoginActivity::class.java
                )
                this@SplashScreenActivity.startActivity(intent)
            }
            this@SplashScreenActivity.finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}
