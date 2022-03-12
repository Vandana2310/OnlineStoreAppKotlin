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
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        sign_up_layout_btnSignup.setOnClickListener {

            if(sign_up_layout_edtPass.text.toString().equals(sign_up_layout_edtConfirmPass.text.toString())){

                //registration process
                val  SignUpURL ="http://192.168.43.61/OnlineStoreApp/join_new_user.php?email="+ sign_up_layout_edtEmail.text.toString() +
                 "&username=" + sign_up_layout_edtUsername.text.toString() + "&pass=" +sign_up_layout_edtPass.text.toString()

                val requestQueue:RequestQueue =Volley.newRequestQueue(this@SignUpActivity)
                val stringRequest =StringRequest(Request.Method.GET,SignUpURL ,Response.Listener { response -> 

                    if(response.equals("A user with this email already exists")){
                        val dialogBuilder =AlertDialog.Builder(this)
                        dialogBuilder.setTitle("SignUp Failed")
                        dialogBuilder.setMessage(response)
                        dialogBuilder.create().show()

                    }else{
//                        val dialogBuilder =AlertDialog.Builder(this)
//                        dialogBuilder.setTitle("SignUp Successful")
//                        dialogBuilder.setMessage(response)
//                        dialogBuilder.create().show()

                        Person.email =sign_up_layout_edtEmail.text.toString()
                        Toast.makeText(this@SignUpActivity,response, Toast.LENGTH_SHORT).show()

                        val homeIntent = Intent(this@SignUpActivity,HomeScreen::class.java)
                        startActivity(homeIntent)
                    }
                    
                }, Response.ErrorListener { error ->
                    val dialogBuilder =AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Error")
                    dialogBuilder.setMessage(error.message)
                    dialogBuilder.create().show()

                })

                requestQueue.add(stringRequest)

            }else{

                val dialogBuilder =AlertDialog.Builder(this)
                dialogBuilder.setTitle("Error")
                dialogBuilder.setMessage("Password Mismatch")
                dialogBuilder.create().show()
            }
        }

        sign_up_layout_btnLogin.setOnClickListener {
            finish()
        }
    }
}