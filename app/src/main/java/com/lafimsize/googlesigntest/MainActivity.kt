package com.lafimsize.googlesigntest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.lafimsize.googlesigntest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private var _binding:ActivityMainBinding?=null
    val binding:ActivityMainBinding?
        get() = _binding


    private lateinit var myFragment:NotSignedFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        _binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        myFragment = NotSignedFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView2, myFragment)
            .commit()

        println(myFragment.fragmentBinding)


        _binding?.fragmentContainerView2?.setOnClickListener {

            println("buradayım")
            println(myFragment.fragmentBinding)



        }

    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        println("şuradayım")


        myFragment.fragmentBinding?.run {
            signYourGoogleBtn.setOnClickListener {



            }
        }


    }

}