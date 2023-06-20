package com.lafimsize.googlesigntest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lafimsize.googlesigntest.databinding.FragmentNotSignedBinding
import com.lafimsize.googlesigntest.databinding.FragmentProfileBinding

class ProfileFragment: Fragment(){

    private var _fragmentBinding:FragmentProfileBinding?=null
    val fragmentBinding:FragmentProfileBinding?
        get()=_fragmentBinding

    private val auth=Firebase.auth
    private val currentUser=auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentBinding= FragmentProfileBinding.inflate(layoutInflater,container,false)

        return _fragmentBinding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragmentBinding?.run {
            usrName.text=currentUser?.displayName

            Glide.with(this@ProfileFragment)
                .load(currentUser?.photoUrl)
                .into(usrImage)

            logoutBtn.setOnClickListener {
                auth.signOut()
                activity?.finish()
                startActivity(activity?.intent)
            }

        }




    }


}