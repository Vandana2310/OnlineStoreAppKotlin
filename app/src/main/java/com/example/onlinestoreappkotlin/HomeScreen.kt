package com.example.onlinestoreappkotlin

import android.app.VoiceInteractor
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val brandsURL= "http://192.168.43.61/OnlineStoreApp/fetch_brands.php"
        var brandList = ArrayList<String>()
        var requestQueue = Volley.newRequestQueue(this@HomeScreen)
        var jsonArrayRequest =JsonArrayRequest(Request.Method.GET ,brandsURL,null,Response.Listener { response ->

            for(jsonobject in 0.until(response.length())){
                brandList.add(response.getJSONObject(jsonobject).getString("brand"))

            }

            var brandsListAdapter =ArrayAdapter(this@HomeScreen,R.layout.brand_item_text_view,brandList)
            brandsListView.adapter =brandsListAdapter


        }, Response.ErrorListener { error ->

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Error")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()
        })
        requestQueue.add(jsonArrayRequest)


     brandsListView.setOnItemClickListener { adapterView, view, i, l ->

         val tappedBrand =brandList.get(i)

         val intent = Intent(this@HomeScreen,FetchEProductsActivity::class.java)
         intent.putExtra("BRAND",tappedBrand)
         startActivity(intent)
     }

    }
}