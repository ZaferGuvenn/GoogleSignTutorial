package com.lafimsize.googlesigntest

data class SignInResult(

    val userData:UserData?,
    val errorMsg:String?



)

data class UserData(

    val userId:String,
    val userName:String?,
    val userPicture:String?


)
