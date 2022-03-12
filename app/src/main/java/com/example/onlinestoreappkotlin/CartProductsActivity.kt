package com.example.onlinestoreappkotlin

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart_products.*

class CartProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_products)

        var cartsProductsUrl ="http://192.168.43.61/OnlineStoreApp/fetch_temprory_order.php?email=${Person.email}"
        var cartProductList = ArrayList<String>()
        var requestQueue = Volley.newRequestQueue(this@CartProductsActivity)
        var jsonArrayRequest = JsonArrayRequest(Request.Method.GET,cartsProductsUrl,null,Response.Listener { response->

            for(joIndex in 0.until(response.length()) ){
                cartProductList.add("${response.getJSONObject(joIndex).getInt("id")}\n" +
                        " ${response.getJSONObject(joIndex).getString("name")}\n" +
                        "${response.getJSONObject(joIndex).getInt("price")}" +
                        "\n${response.getJSONObject(joIndex).getString("email")}" +
                        "\n${response.getJSONObject(joIndex).getInt("amount")}")
            }
            var  cartProductAdapter = ArrayAdapter(this@CartProductsActivity,android.R.layout.simple_list_item_1,cartProductList)
            cartProductListView.adapter = cartProductAdapter

        },Response.ErrorListener { error ->

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Error")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()


        })

        requestQueue.add(jsonArrayRequest)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.cart_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item?.itemId == R.id.continueShoppingItem){
            var intent = Intent(this,HomeScreen::class.java)
            startActivity(intent)

        }else if(item?.itemId== R.id.declineOrderitem){
            var deleteUrl= "http://192.168.43.61/OnlineStoreApp/decline_order.php?email=${Person.email}"
            var requestQueue = Volley.newRequestQueue(this@CartProductsActivity)
            var stringRequest = StringRequest(Request.Method.GET,deleteUrl,Response.Listener { response ->

                var intent = Intent(this,HomeScreen::class.java)
                startActivity(intent)

            },Response.ErrorListener { error ->


            })

         requestQueue.add(stringRequest)
        }
        return super.onOptionsItemSelected(item)
    }
}