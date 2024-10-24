package com.firman.mybroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {

    companion object {
        private val TAG = SmsReceiver::class.java.simpleName
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (message in messages) {
                val senderNum = message.originatingAddress
                val body = message.messageBody
                Log.d(TAG, "sendernum : $senderNum; message; $message")
                val showSmsIntent = Intent(context, SmsReceiverActivity::class.java)
                showSmsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_NO, senderNum)
                showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_MESSAGE, body)
                context.startActivity(showSmsIntent)
            }
        }
    }
}