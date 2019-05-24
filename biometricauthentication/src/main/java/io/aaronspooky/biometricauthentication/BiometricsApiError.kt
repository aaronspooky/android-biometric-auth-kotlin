package io.aaronspooky.biometricauthentication

enum class BiometricsApiError(val error: String) {
    SDK_NOT_SUPPORTED("SDK not supported"),
    PERMISSION_NOT_GRANTED("Permission not granted"),
    HARDWARE_NOT_SUPPORTED("Hardware not supported"),
    FINGERPRINT_NOT_AVAILABLE("Fingerprint not available"),
    FINGERPRINT_OPERATION_CANCELED("Fingerprint operation canceled"),
    NOT_RECOGNIZED("Fingerprint not recognized")
}