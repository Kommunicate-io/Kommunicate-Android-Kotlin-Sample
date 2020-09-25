package io.kommunicate.sampleKT

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.applozic.mobicomkit.api.account.register.RegistrationResponse
import com.applozic.mobicommons.commons.core.utils.Utils
import com.applozic.mobicommons.json.GsonUtils

import io.kommunicate.Kommunicate
import io.kommunicate.callbacks.KMLoginHandler
import io.kommunicate.users.KMUser
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    val APP_ID = BuildConfig.APP_ID
    private val INVALID_APP_ID = "INVALID_APPLICATIONID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        Kommunicate.init(this, APP_ID)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loginAsVisitor = findViewById<Button>(R.id.loginAsVisitor)

        val loading = ProgressDialog(this)
        loading.setCancelable(false)
        loading.setMessage("Logging in, please wait...")

        login.setOnClickListener {
            //Creating Kommunicate User object
            Utils.printLog(this, "ClickTest", "Clicked login button")
            val userId = username.text.toString().trim()
            val password = password.text.toString().trim()

            if (TextUtils.isEmpty(userId)) {
                Toast.makeText(
                    this@LoginActivity,
                    "username field cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val kmUser = KMUser()
                kmUser.userId = userId  //Setting userId(Only mandatory field) to kmUser
                kmUser.password =
                    password //Setting password(Optional, if set always neede in future logins) to kmUser

                loginUser(kmUser, loading)
            }
        }

        loginAsVisitor.setOnClickListener {
            //here we are just getting a Visitor user (Created by Kommunicate SDK using a random userId) and logging into Kommunicate
            loginUser(Kommunicate.getVisitor(), loading)
        }
    }

    //Calls the Kommunicate login function to login the user, provides two callbacks.
    fun loginUser(kmUser: KMUser, loading: ProgressDialog) {
        loading.show()

        Kommunicate.login(this, kmUser, object : KMLoginHandler {
            //If login is successful, the callback comes here
            override fun onSuccess(
                registrationResponse: RegistrationResponse?,
                context: Context?
            ) {
                loading.dismiss()
                Kommunicate.openConversation(this@LoginActivity, null)
            }

            //If login fails, the callback comes here
            override fun onFailure(
                registrationResponse: RegistrationResponse?,
                exception: Exception?
            ) {
                loading.dismiss()

                var errorText = "Some error occurred"
                if (registrationResponse != null) {
                    errorText = GsonUtils.getJsonFromObject(
                        registrationResponse,
                        registrationResponse.javaClass
                    )
                } else if (exception != null) {
                    errorText = GsonUtils.getJsonFromObject(exception, exception.javaClass)
                }

                Toast.makeText(this@LoginActivity, errorText, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

