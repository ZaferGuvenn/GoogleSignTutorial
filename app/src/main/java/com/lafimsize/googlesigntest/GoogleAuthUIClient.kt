package com.lafimsize.googlesigntest

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class GoogleAuthUIClient(

    private val context: Context,
    private val oneTapClient: SignInClient

) {

    private val auth=FirebaseAuth.getInstance()

    suspend fun signIn():IntentSender?{

        val result= try {

            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()//burada coroutine kullandık. addOnSuccessListener vs. de kullanabilirdik.


        }catch (e:Exception){

            e.printStackTrace()

            if (e is CancellationException) throw e //özel hata uyarısı

            null
        }

        return result?.pendingIntent?.intentSender

    }

    //Giriş isteği başlat...
    private fun buildSignInRequest():BeginSignInRequest{

        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(

                //options builder
                GoogleIdTokenRequestOptions.Builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(false)//tek hesap olduğunda direkt olarak giriş yapmasın.
            .build()


    }



    //şimdi sign in resultı intent ile alalım. SignInResult kendi oluşturduğumuz sınıf!
    suspend fun signInWithIntent(intent: Intent): SignInResult {

        val credential=oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken=credential.googleIdToken

        val googleCredention=GoogleAuthProvider.getCredential(googleIdToken,null)

        return try {

            val user=auth.signInWithCredential(googleCredention).await().user

            SignInResult(
                userData = user?.run {
                    UserData(
                        userId = uid,
                        userName = displayName,
                        userPicture = photoUrl?.toString()
                    )
                },
                errorMsg = null

            )



        }catch (e:Exception){

            if (e is CancellationException) throw e

            SignInResult(
                userData= null,
                errorMsg= e.message
            )

        }

    }


    //çıkış yapma işlemi
    suspend fun signOut(){

        try {

            oneTapClient.signOut().await()
            auth.signOut()

        }catch (e:Exception){
            e.printStackTrace()
            if (e is CancellationException) throw e
        }


    }


    //giriş yapmış olan kullanıcıyı alan fonksiyonumuzu yazabiliriz şimdi.
    fun getSignedUser():UserData?=auth.currentUser?.run {
        UserData(
            userId = uid,
            userName = displayName,
            userPicture = photoUrl?.toString()
        )
    }




}