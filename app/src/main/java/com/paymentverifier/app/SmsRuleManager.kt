package com.paymentverifier.app
import android.util.Log
import com.paymentverifier.app.data.api.*
object SmsRuleManager { @Volatile private var rules:List<SmsProviderRule> = emptyList(); suspend fun refresh(){try{val r=com.paymentverifier.app.data.api.ApiClient.create().getSmsRules("Bearer ${DeviceConfig.API_TOKEN}",DeviceConfig.DEVICE_ID);if(r.isSuccessful)rules=r.body()?.providers?:emptyList()}catch(e:Exception){Log.e("PaymentVerifier","SMS rules refresh failed",e)}} fun detectProvider(sender:String?):String?=sender?.trim()?.let{s->rules.firstOrNull{rule->rule.senderIds.any{it.equals(s,true)}}?.provider} }
