package com.yusufyildiz.kullanicikayit.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yusufyildiz.kullanicikayit.R
import com.yusufyildiz.kullanicikayit.adapter.RecyclerAdapter
import com.yusufyildiz.kullanicikayit.databinding.ActivityManagerBinding
import com.yusufyildiz.kullanicikayit.model.User

class ManagerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityManagerBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var usersList : ArrayList<User>
    private lateinit var recyclerAdapter: RecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManagerBinding.inflate(layoutInflater)
        var view = binding.root

        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore
        usersList = ArrayList<User>()

        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = RecyclerAdapter(usersList)
        binding.recyclerView.adapter = recyclerAdapter


    }

    fun getData()
    {
        db.collection("Users").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->

            if(error!=null)
            {
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()
            }
            else
            {
                if(value!=null)
                {
                    if(!value.isEmpty)
                    {
                        val documents = value.documents
                        usersList.clear()

                        for(document in documents)
                        {
                            val name = document.get("name") as String
                            val surname = document.get("surname") as String
                            val phone = document.get("phone") as String
                            val station = document.get("stationAddress") as String
                            val stationDetails = document.get("detailsAddress") as String
                            val workAddress = document.get("workAddress") as String
                            val homeAddress = document.get("homeAddress") as String
                            val startWork = document.get("startWorkTime") as String
                            val finishWork = document.get("finishWorkTime")as String

                            val user = User(name,surname,phone,station,stationDetails,workAddress,homeAddress,startWork,finishWork)
                            usersList.add(user)

                        }

                        recyclerAdapter.notifyDataSetChanged()

                    }
                }
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.log_out_from_manager_account,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if(item.itemId == R.id.logOutFromManagerAccount)
        {
            auth.signOut()
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }


}