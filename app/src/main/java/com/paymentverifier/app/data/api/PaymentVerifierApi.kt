package com.paymentverifier.app.data.api
import retrofit2.Response
import retrofit2.http.*
interface PaymentVerifierApi {
 @POST("device/register-key") suspend fun registerKey(@Header("Authorization") a:String,@Header("X-PV-Device-ID") d:String,@Body q:DeviceKeyRequest):Response<DeviceKeyResponse>
 @GET("verification/pending") suspend fun pending(@Header("Authorization") a:String,@Header("X-PV-Device-ID") d:String):Response<PendingVerificationResponse>
 @POST("verification/result") suspend fun result(@Header("Authorization") a:String,@Header("X-PV-Device-ID") d:String,@Body q:VerificationResultRequest):Response<VerificationResultResponse>
 @GET("sms-rules") suspend fun getSmsRules(@Header("Authorization") a:String,@Header("X-PV-Device-ID") d:String):Response<SmsRulesResponse>
}
