package com.imran.hov.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message:String){
    Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
}