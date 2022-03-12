package com.example.onlinestoreappkotlin

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_eproducts.*

class FetchEProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_eproducts)

        val selectedBrand =intent.getStringExtra("BRAND")
        txtBrandName.text="Product of $selectedBrand"

        var productsList= ArrayList<EProduct>()

        val productURL ="http://192.168.43.61/OnlineStoreApp/fetch_eproducts.php?brand=$selectedBrand"
        val requestQueue =Volley.newRequestQueue(this@FetchEProductsActivity)
        val jsonArrayRequest= JsonArrayRequest(Request.Method.GET,productURL,null,Response.Listener
        { response ->

          for(productJsonArrayindex in 0 until(response.length())){

              productsList.add(EProduct(response.getJSONObject(productJsonArrayindex).getInt("id"),
                  response.getJSONObject(productJsonArrayindex).getString("name")
                  ,response.getJSONObject(productJsonArrayindex).getInt("price"),
                  response.getJSONObject(productJsonArrayindex).getString("picture")))
          }

            val productAdapter = EProductAdapter(this@FetchEProductsActivity,productsList)
            productsRecyclerView.layoutManager = LinearLayoutManager(this@FetchEProductsActivity)
            productsRecyclerView.adapter = productAdapter

        }, Response.ErrorListener { error ->

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Error")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()

        })
        requestQueue.add(jsonArrayRequest)
    }
}