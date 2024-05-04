package com.example.volleyexample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.squareup.picasso.Picasso
import com.android.volley.toolbox.Volley


class MainActivity : AppCompatActivity() {
    lateinit var courseNameTV: TextView
    lateinit var courseDescTV: TextView
    lateinit var courseReqTV: TextView
    lateinit var courseIV: ImageView
    lateinit var visitCourseBtn: Button
    lateinit var loadingPB: ProgressBar

    private var url = "https://www.jsonkeeper.com/b/8RFY"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        val requestQueue = Volley.newRequestQueue(applicationContext)

        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            loadingPB.visibility = View.GONE
            val courseName: String = response.getString("courseName")
            val courseLink: String = response.getString("courseLink")
            val courseImg: String = response.getString("courseimg")
            val courseDesc: String = response.getString("courseDesc")
            val coursePreq: String = response.getString("Prerequisites")

            try{
            courseReqTV.text = coursePreq
            courseDescTV.text = courseDesc
            courseNameTV.text = courseName

            Picasso.get().load(courseImg).into(courseIV)
            visitCourseBtn.visibility = View.VISIBLE

            visitCourseBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(courseLink))
                startActivity(intent)
            }

        } catch (e: Exception) {
                e.localizedMessage?.let { showToastMsg(it) }
            e.printStackTrace()
        }
        },{error->
            Log.e("TAG", "RESPONSE IS $error")
           showToastMsg("Fail to get response: $error")
            loadingPB.visibility = View.GONE
        })
        requestQueue.add(request)
    }

    private fun initViews() {
        courseNameTV = findViewById(R.id.idTVCourseName)
        courseDescTV = findViewById(R.id.idTVDesc)
        courseReqTV = findViewById(R.id.idTVPreq)
        courseIV = findViewById(R.id.idIVCourse)
        visitCourseBtn = findViewById(R.id.idBtnVisitCourse)
        loadingPB = findViewById(R.id.idLoadingPB)
    }

    private fun showToastMsg(msg:String){
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT)
            .show()
    }




}