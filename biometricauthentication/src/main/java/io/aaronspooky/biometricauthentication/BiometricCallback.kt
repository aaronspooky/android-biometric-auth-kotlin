package io.aaronspooky.biometricauthentication

interface BiometricCallback {
    fun onSuccess()
    fun onFailure(error: String)
    fun notSupported(error: String)
}