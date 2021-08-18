package com.yusufyildiz.kullanicikayit.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yusufyildiz.kullanicikayit.databinding.ActivitySignInBinding

class SignIn : AppCompatActivity() {

    private lateinit var binding : ActivitySignInBinding
    private lateinit var auth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth


    }

    fun signIn(view: View)
    {
        var email = binding.emailText.text.toString()
        var password = binding.passwordSignInText.text.toString()



        if(!password.equals("") && !email.equals(""))
        {
            if(email == "rahim12345@gmail.com" && password == "123456")
            {
                auth.signInWithEmailAndPassword("rahim12345@gmail.com","123456").addOnSuccessListener {

                    var intent = Intent(this,ManagerActivity::class.java)
                    startActivity(intent)
                    finish()

                }.addOnFailureListener {
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {

                    val intent = Intent(this, UserInfos::class.java)
                    startActivity(intent)
                    finish()

                }.addOnFailureListener {
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }


        }
        else
        {
            Toast.makeText(this,"Kullanıcı adı veya parola hatalı !!!",Toast.LENGTH_LONG).show()

        }
    }


}