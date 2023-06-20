package com.lafimsize.googlesigntest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lafimsize.googlesigntest.databinding.FragmentNotSignedBinding

class NotSignedFragment: Fragment(){

    private var _fragmentBinding:FragmentNotSignedBinding?=null
    val fragmentBinding:FragmentNotSignedBinding?
        get()=_fragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentBinding= FragmentNotSignedBinding.inflate(layoutInflater,container,false)

        return _fragmentBinding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentBinding=null
    }


}