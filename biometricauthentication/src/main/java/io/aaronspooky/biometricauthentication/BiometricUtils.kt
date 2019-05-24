package io.aaronspooky.biometricauthentication

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat

object BiometricUtils {

    /**
     * Biometric prompt is only available for SDK 28 and above
     */
    fun isBiometricPromptEnable(): Boolean {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
    }

    /**
     * Fingerprint authentication is only supported from Android 6.0
     */
    fun isSDKVersionSupported(): Boolean {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    }

    /**
     * Check if the mobile has a fingerprint sensors
     */
    fun isHardwareSupported(context: Context): Boolean {
        val fingerPrintManager = FingerprintManagerCompat.from(context)
        return fingerPrintManager.isHardwareDetected
    }

    /**
     * Check if the user has configured the fingerprint authentication
     */
    fun isFingerprintAvailable(context: Context): Boolean {
        val fingerPrintManager = FingerprintManagerCompat.from(context)
        return fingerPrintManager.hasEnrolledFingerprints()
    }

    /**
     * Check if the fingerprint permission is granted
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun isPermissionGranted(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED
    }
}