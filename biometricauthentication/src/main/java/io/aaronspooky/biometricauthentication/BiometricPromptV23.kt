package io.aaronspooky.biometricauthentication

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.os.CancellationSignal
import java.lang.Exception
import java.lang.RuntimeException
import java.security.KeyStore
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator

@TargetApi(Build.VERSION_CODES.M)
class BiometricPromptV23(private val context: Context) {

    private val KEY_NAME = UUID.randomUUID().toString()
    private lateinit var cipher: Cipher
    private lateinit var keyStore: KeyStore
    private lateinit var keyGenerator: KeyGenerator
    private lateinit var cryptoObject: FingerprintManagerCompat.CryptoObject

    private lateinit var callback: BiometricCallback
    private lateinit var cancellationSignal: CancellationSignal
    private var dialog: BiometricDialog = BiometricDialog(context)

    // region Builder

    fun withTitle(title: String): BiometricPromptV23 {
        dialog.setTitle(title)
        return this
    }

    fun setSubtitle(subtitle: String): BiometricPromptV23 {
        dialog.setSubtitle(subtitle)
        return this
    }

    fun setDescription(description: String): BiometricPromptV23 {
        dialog.setDescription(description)
        return this
    }

    fun setNegativeTextButton(negativeText: String): BiometricPromptV23 {
        dialog.setNegativeText(negativeText)
        return this
    }

    fun setCallback(callback: BiometricCallback): BiometricPromptV23 {
        dialog.setCallback(callback)
        this.callback = callback
        return this
    }

    fun setCancellationSignal(cancellationSignal: CancellationSignal): BiometricPromptV23 {
        dialog.setCancellationSignal(cancellationSignal)
        this.cancellationSignal = cancellationSignal
        return this
    }

    fun build(): BiometricPromptV23 {
        dialog.show()
        return this
    }

    fun authenticate() {
        generateKey()

        if (initCipher()) {
            cryptoObject = FingerprintManagerCompat.CryptoObject(cipher)
            val fingerprintManagerCompat = FingerprintManagerCompat.from(context)
            fingerprintManagerCompat.authenticate(cryptoObject, 0, cancellationSignal, BiometricCallbackV23(callback, dialog), null)
        }
    }

    // endregion

    private fun generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyGenerator.init(KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT.or(KeyProperties.PURPOSE_DECRYPT))
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setUserAuthenticationRequired(true)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .build())
            keyGenerator.generateKey()
        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }

    private fun initCipher(): Boolean {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7)
        } catch (e: Exception) {
            throw RuntimeException("Failed to get cipher", e)
        }

        try {
            keyStore.load(null)
            val key = keyStore.getKey(KEY_NAME, null)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            return true
        } catch (e: Exception) {
            RuntimeException("Failed to init cipher", e)
            return false
        }
    }
}