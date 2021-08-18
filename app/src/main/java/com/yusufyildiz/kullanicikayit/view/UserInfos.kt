package com.yusufyildiz.kullanicikayit.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.internal.InternalTokenProvider
import com.google.firebase.ktx.Firebase
import com.yusufyildiz.kullanicikayit.R
import com.yusufyildiz.kullanicikayit.databinding.ActivityUserInfosBinding

class UserInfos : AppCompatActivity() {

    private lateinit var binding : ActivityUserInfosBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db: FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore


    }

    fun goMap(view: View)
    {
        val intent = Intent(this, LocationOfService::class.java)
        startActivity(intent)
    }
    fun clear(view: View)
    {
        binding.nameText.setText("")
        binding.surnameText.setText("")
        binding.telText.setText("")
        binding.homeAddressText.setText("")
        binding.workAddressText.setText("")
        binding.workStartTimeText.setText("")
        binding.workFinishTimeText.setText("")
        binding.detailsAddressText.setText("")
    }

    fun save(view: View)
    {

        val name = binding.nameText.text.toString()
        val surname = binding.surnameText.text.toString()
        val intent = intent
        val stationAddress = intent.getStringExtra("stationAddress")
        val phone = binding.telText.text.toString()
        val homeAddress = binding.homeAddressText.text.toString()
        val workAddress = binding.workAddressText.text.toString()
        val startWorkTime = binding.workStartTimeText.text.toString()
        val finishWorkTime = binding.workFinishTimeText.text.toString()
        val detailsAddress = binding.detailsAddressText.text.toString()

        if(name.equals("")
            || surname.equals("")
            || phone.equals("")
            || homeAddress.equals("")
            || workAddress.equals("")
            || startWorkTime.equals("")
            ||finishWorkTime.equals("")
            || detailsAddress.equals(""))
        {
            Toast.makeText(this,"Boş Bıraktığınız Yerler Var !!!",Toast.LENGTH_LONG).show()
        }

        else
        {

            val postMap = hashMapOf<String,Any>()
            postMap.put("name",name)
            postMap.put("surname",surname)
            postMap.put("stationAddress",stationAddress!!)
            postMap.put("phone",phone)
            postMap.put("homeAddress",homeAddress)
            postMap.put("workAddress",workAddress)
            postMap.put("startWorkTime",startWorkTime)
            postMap.put("finishWorkTime",finishWorkTime)
            postMap.put("detailsAddress",detailsAddress)
            postMap.put("date",Timestamp.now())

            db.collection("Users").add(postMap).addOnSuccessListener {
                Toast.makeText(this,"Kaydedildi",Toast.LENGTH_LONG).show()

            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }





    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.log_out,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.logOut)
        {
            auth.signOut()
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}