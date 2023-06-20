package com.lafimsize.googlesigntest

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity
import com.lafimsize.googlesigntest.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    private var _binding:ActivityMainBinding?=null
    val binding:ActivityMainBinding?
        get() = _binding


    private lateinit var notSignedFragment:NotSignedFragment
    private lateinit var profFragment:ProfileFragment

    private lateinit var viewModel: SignInViewModel

    private lateinit var launcher:ActivityResultLauncher<IntentSenderRequest>

    //burada işimizde kullanacaksak hafızada yer tutsun diyoruz.
    private val googleAuthUIClient by lazy {

        GoogleAuthUIClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        _binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        notSignedFragment = NotSignedFragment()
        profFragment=ProfileFragment()

        viewModel=ViewModelProvider(this)[SignInViewModel::class.java]


        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView2, notSignedFragment)
            .commit()



        _binding?.fragmentContainerView2?.setOnClickListener {



        }

        initLauncher()

        collectFlow()
        checkSignedIn()

    }

    private fun checkSignedIn(){

        if (googleAuthUIClient.getSignedUser()!=null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView2,ProfileFragment())
                .commit()
        }
    }

    private fun collectFlow(){

        lifecycleScope.launch {

            viewModel.state.collect{

                if (it.isSigned){
                    Toast
                        .makeText(this@MainActivity,"Hoş geldiniz!!!",Toast.LENGTH_SHORT)
                        .show()

                    supportFragmentManager.beginTransaction()
                        .add(R.id.fragmentContainerView2,ProfileFragment())
                        .commit()
                }
            }


        }


    }

    private fun initLauncher(){

        launcher=
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()){

                if (it.resultCode== RESULT_OK){

                    lifecycleScope.launch {
                        val signInResult=googleAuthUIClient.signInWithIntent(

                            intent = it.data ?: return@launch
                        )
                        viewModel.onSignInResult(signInResult)
                    }

                }

            }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        notSignedFragment.fragmentBinding?.run {
            signYourGoogleBtn.setOnClickListener {
                lifecycleScope.launch {
                    val signIntentSender=googleAuthUIClient.signIn()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signIntentSender?:return@launch
                        ).build()
                    )
                }
            }
        }


    }

}