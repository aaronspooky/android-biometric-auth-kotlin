package io.aaronspooky.biometricauthentication

import android.content.Context
import android.graphics.Color
import android.support.design.widget.BottomSheetDialog
import android.support.v4.os.CancellationSignal
import android.widget.Button
import android.widget.TextView

class BiometricDialog(context: Context): BottomSheetDialog(context, R.style.Theme_Design_BottomSheetDialog) {

    // region Builder

    private lateinit var title: TextView
    private lateinit var subtitle: TextView
    private lateinit var description: TextView
    private lateinit var negativeButton: Button
    private lateinit var cancellationSignal: CancellationSignal
    private lateinit var callback: BiometricCallback
    private lateinit var messageTextView: TextView

    init {
        showDialog()
    }

    fun setTitle(title: String) {
        this.title.text = title
    }

    fun setSubtitle(subtitle: String) {
        this.subtitle.text = subtitle
    }

    fun setDescription(description: String) {
        this.description.text = description
    }

    fun setNegativeText(negativeText: String) {
        this.negativeButton.text = negativeText
    }

    fun setCallback(callback: BiometricCallback) {
        this.callback = callback
    }

    fun setCancellationSignal(cancellationSignal: CancellationSignal) {
        this.cancellationSignal = cancellationSignal
    }

    fun setMessageText(message: String) {
        this.messageTextView.text = message
        this.messageTextView.setTextColor(Color.RED)
    }

    private fun showDialog() {
        initDialog()
        this.initUIComponents()
        this.didTapCancelButton()
    }

    // endregion

    private fun initUIComponents() {
        this.title = findViewById(R.id.fragment_biometric_fragment_dialog_tv_title)!!
        this.subtitle = findViewById(R.id.fragment_biometric_fragment_dialog_tv_subtitle)!!
        this.description = findViewById(R.id.fragment_biometric_fragment_dialog_tv_description)!!
        this.negativeButton = findViewById(R.id.fragment_biometric_fragment_dialog_btn_negative)!!
        this.messageTextView = findViewById(R.id.fragment_biometric_fragment_dialog_tv_message)!!
    }

    private fun didTapCancelButton() {
        negativeButton.setOnClickListener {
            dismiss()
            cancellationSignal.cancel()
        }
    }

    private fun initDialog() {
        val dialog = layoutInflater.inflate(R.layout.dialog_biometric_fragment, null)
        dialog.setOnClickListener(null)
        setCancelable(false)
        setContentView(dialog)
    }
}