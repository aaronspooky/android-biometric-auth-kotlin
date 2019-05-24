# Android Biometric Authentication
Authentication using biometrics with Kotlin ❤️

## Getting started

Min SDK Version: 23

For lower versions the ``notSupported`` method is invoked because the ``Fingerprint sensor`` was implemented from Android 6.

Step 1. Add the JitPack repository to your gradle file 

```gradle
allprojects {
  repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
  
```

Step 2. Add the dependency

```gradle
dependencies {
	        implementation 'com.github.aaronspooky:android-biometric-auth-kotlin:0.1'
	}
}
```

## Usage

Step 1. Add ``BiometricCallback`` interface
```kotlin
class MainActivity : AppCompatActivity(), BiometricCallback {
}
```

Step 2. Add the following methods

```kotlin
// region Biometric Callbacks

    override fun onSuccess() {}

    override fun onFailure(error: String) {}

    override fun notSupported(error: String) {}

// endregion
```

Step 3. Add the ``BiometricManager`` builder

```kotlin
BiometricBuilder(this)
                .setTitle("Any text")
                .setSubtitle("Any text")
                .setDescription("Any text")
                .setNegativeButtonText("Any text")
                .authenticate(this)
```
