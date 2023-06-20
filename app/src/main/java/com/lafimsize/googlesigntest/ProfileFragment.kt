package com.lafimsize.googlesigntest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lafimsize.googlesigntest.databinding.FragmentNotSignedBinding
import com.lafimsize.googlesigntest.databinding.FragmentProfileBinding

class ProfileFragment: Fragment(){

    private var _fragmentBinding:FragmentProfileBinding?=null
    val fragmentBinding:FragmentProfileBinding?
        get()=_fragmentBinding

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




    }


}