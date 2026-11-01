package com.paymentverifier.app.data.api
import com.google.gson.annotations.SerializedName
data class DeviceKeyRequest(@SerializedName("public_key") val publicKey:String)
data class DeviceKeyResponse(val success:Boolean?=null,val status:String?=null)
data class PendingVerificationResponse(val success:Boolean?=null,val pending:Boolean?=null,@SerializedName("request_id") val requestId:String?=null,val payload:String?=null,@SerializedName("expires_at") val expiresAt:Long?=null)
data class VerificationResultRequest(@SerializedName("request_id") val requestId:String,val approved:Boolean,val signature:String,@SerializedName("reason_code") val reasonCode:String?=null)
data class VerificationResultResponse(val success:Boolean?=null,val status:String?=null)
data class SmsRulesResponse(val success:Boolean?=null,@SerializedName("store_id") val storeId:Int?=null,val providers:List<SmsProviderRule> = emptyList(),@SerializedName("server_time") val serverTime:Long?=null)
data class SmsProviderRule(val provider:String,@SerializedName("sender_ids") val senderIds:List<String> = emptyList())
