package com.example.onlinestoreappkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_main_btnLogin.setOnClickListener {
            val loginURl="http://192.168.43.61/OnlineStoreApp/login_app_user.php?email=" +activity_main_edtEmail.text.toString() +
                    "&pass=" +activity_main_edtLoginPass.text.toString()

            val requestQueue:RequestQueue= Volley.newRequestQueue(this@MainActivity)
            val stringRequest = StringRequest(Request.Method.GET ,loginURl ,Response.Listener { response ->

                if(response.equals("The user does exists")){

                    Person.email = activity_main_edtEmail.text.toString()
                    Toast.makeText(this@MainActivity,response,Toast.LENGTH_SHORT).show()
                    val homeIntent = Intent(this@MainActivity,HomeScreen::class.java)
                    startActivity(homeIntent)
                }else{
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Message")
                    dialogBuilder.setMessage(response)
                    dialogBuilder.create().show()
                }

            }, Response.ErrorListener { error ->

                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Error")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()
            })

            requestQueue.add(stringRequest)
        }

        activity_main_btnSignUp.setOnClickListener {
            var signUpIntent = Intent(this@MainActivity,SignUpActivity::class.java)
            startActivity(signUpIntent)


        }
    }
}