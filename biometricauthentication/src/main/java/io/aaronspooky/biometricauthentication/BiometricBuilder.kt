package io.aaronspooky.biometricauthentication

import android.annotation.TargetApi
import android.content.Context
import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal

class BiometricBuilder(private val context: Context) {

    // region Builder

    private var title = ""
    private var subtitle = ""
    private var description = ""
    private var negativeText = ""

    fun setTitle(title: String): BiometricBuilder {
        this.title = title
        return this
    }

    fun setSubtitle(subtitle: String): BiometricBuilder {
        this.subtitle = subtitle
        return this
    }

    fun setDescription(description: String): BiometricBuilder {
        this.description = description
        return this
    }

    fun setNegativeButtonText(negativeText: String): BiometricBuilder {
        this.negativeText = negativeText
        return this
    }

    fun authenticate(callback: BiometricCallback) {
        if (!BiometricUtils.isSDKVersionSupported()) {
            callback.notSupported(BiometricsApiError.SDK_NOT_SUPPORTED.error)
            return
        }

        if (!BiometricUtils.isHardwareSupported(context)) {
            callback.notSupported(BiometricsApiError.HARDWARE_NOT_SUPPORTED.error)
            return
        }

        if (!BiometricUtils.isPermissionGranted(context)) {
            callback.onFailure(BiometricsApiError.PERMISSION_NOT_GRANTED.error)
            return
        }

        if (!BiometricUtils.isFingerprintAvailable(context)) {
            callback.onFailure(BiometricsApiError.FINGERPRINT_NOT_AVAILABLE.error)
            return
        }

        if (BiometricUtils.isBiometricPromptEnable()) {
            this.displayBiometricPrompt(callback)
        } else {
            this.displayBiometricPromptV23(callback)
        }
    }

    // endregion

    @TargetApi(Build.VERSION_CODES.P)
    private fun displayBiometricPrompt(callback: BiometricCallback) {
        val biometric = BiometricPrompt.Builder(context)
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)
            .setNegativeButton(negativeText, context.mainExecutor,
                DialogInterface.OnClickListener { dialog, which ->
                    callback.onFailure(BiometricsApiError.FINGERPRINT_OPERATION_CANCELED.error) })
            .build()

        biometric.authenticate(CancellationSignal(), context.mainExecutor, BiometricCallbackV28(callback))
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun displayBiometricPromptV23(callback: BiometricCallback) {
        val biometric = BiometricPromptV23(context)
            .withTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)
            .setNegativeTextButton(negativeText)
            .setCancellationSignal(android.support.v4.os.CancellationSignal())
            .setCallback(callback)
            .build()

        biometric.authenticate()
    }
}