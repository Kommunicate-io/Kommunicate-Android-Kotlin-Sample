package io.kommunicate.sampleKT

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import io.kommunicate.activities.KMConversationActivity
import io.kommunicate.users.KMUser

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent = Intent(
                this@SplashScreenActivity,
                if (KMUser.isLoggedIn(this@SplashScreenActivity)) KMConversationActivity::class.java else LoginActivity::class.java
            )
            this@SplashScreenActivity.startActivity(intent)
            this@SplashScreenActivity.finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}
