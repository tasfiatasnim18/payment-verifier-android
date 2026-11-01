package com.paymentverifier.app
import android.content.*
import android.provider.Telephony
import kotlinx.coroutines.*
class SmsReceiver:BroadcastReceiver(){override fun onReceive(c:Context,i:Intent){if(i.action!=Telephony.Sms.Intents.SMS_RECEIVED_ACTION)return;val go=goAsync();CoroutineScope(SupervisorJob()+Dispatchers.IO).launch{try{SecureVerificationManager.poll();for(s in Telephony.Sms.Intents.getMessagesFromIntent(i)){val p=SmsParser.parse(s.originatingAddress,s.messageBody.orEmpty(),SmsRuleManager.detectProvider(s.originatingAddress)?:continue)?:continue;if(SecureVerificationManager.matches(p.senderPhone,p.transactionId))SecureVerificationManager.submit(true,"matched")}}finally{go.finish()}}}}
