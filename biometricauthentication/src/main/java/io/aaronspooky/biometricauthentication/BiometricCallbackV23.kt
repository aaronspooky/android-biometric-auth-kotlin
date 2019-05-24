package io.aaronspooky.biometricauthentication

import android.support.v4.hardware.fingerprint.FingerprintManagerCompat

class BiometricCallbackV23(private val callback: BiometricCallback, private val dialog: BiometricDialog): FingerprintManagerCompat.AuthenticationCallback() {

    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
        callback.onFailure(errString.toString())
        dialog.setMessageText(errString.toString())
    }

    override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
        callback.onSuccess()
        dialog.dismiss()
    }

    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
        callback.onFailure(helpString.toString())
        dialog.setMessageText(helpString.toString())
    }

    override fun onAuthenticationFailed() {
        callback.onFailure(BiometricsApiError.NOT_RECOGNIZED.error)
        dialog.setMessageText(BiometricsApiError.NOT_RECOGNIZED.error)
    }
}