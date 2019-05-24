package io.aaronspooky.biometricauthentication

import android.annotation.TargetApi
import android.hardware.biometrics.BiometricPrompt
import android.os.Build

@TargetApi(Build.VERSION_CODES.P)
class BiometricCallbackV28(private val callback: BiometricCallback): BiometricPrompt.AuthenticationCallback() {

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        callback.onFailure(errString.toString())
        super.onAuthenticationError(errorCode, errString)
    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
        callback.onSuccess()
        super.onAuthenticationSucceeded(result)
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        callback.onFailure(helpString.toString())
        super.onAuthenticationHelp(helpCode, helpString)
    }

    override fun onAuthenticationFailed() {
        callback.onFailure("Unknown error")
        super.onAuthenticationFailed()
    }
}