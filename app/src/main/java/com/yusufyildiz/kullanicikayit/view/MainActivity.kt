package com.yusufyildiz.kullanicikayit.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yusufyildiz.kullanicikayit.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity()
{

    private lateinit var binding : ActivityMainBinding
    private lateinit var auth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        auth = Firebase.auth

        if(auth.currentUser != null)
        {
            val intent = Intent(this,UserInfos::class.java)
            startActivity(intent)
            finish()
        }



    }

    fun signUp(view: View)
    {
        val email = binding.epostText.text.toString()
        val passwordOne = binding.passwordText.text.toString()
        val passwordTwo = binding.passwordText2.text.toString()

        if(!email.equals("") && !passwordOne.equals("") && !passwordTwo.equals(""))
        {
            if(passwordTwo.toString() == passwordOne.toString())
            {

                auth.createUserWithEmailAndPassword(email,passwordTwo).addOnSuccessListener {

                    val intent = Intent(this, UserInfos::class.java)
                    startActivity(intent)
                    finish()

                }.addOnFailureListener{
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
                }



            }
            else
            {
                Toast.makeText(this,"Şifreler Uyuşmuyor!!!",Toast.LENGTH_LONG).show()
            }

        }
        else
        {
            Toast.makeText(this,"Boş Bıraktığınız Yerler Var !!!",Toast.LENGTH_LONG).show()
        }


    }

    fun signIn(view: View)
    {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
        finish()


    }
}