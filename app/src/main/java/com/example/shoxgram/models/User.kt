package com.example.shoxgram.models

import java.io.Serializable

class User: Serializable {
    var email: String? = null
    var displayName: String? = null
    var phoneNumber: String? = null
    var photoUrl: String? = null
    var uid: String? = null
    var online: Boolean? = null
    var token: String? = null




    constructor()
    constructor(
        email: String?,
        displayName: String?,
        phoneNumber: String?,
        photoUrl: String?,
        uid: String?,
        online: Boolean?,
        token: String?
    ) {
        this.email = email
        this.displayName = displayName
        this.phoneNumber = phoneNumber
        this.photoUrl = photoUrl
        this.uid = uid
        this.online = online
        this.token = token
    }


}