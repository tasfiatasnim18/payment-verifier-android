package com.paymentverifier.app

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.paymentverifier.app.data.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicReference

data class VerificationPayload(val phone: String, @SerializedName("transaction_id") val transactionId: String, @SerializedName("request_id") val requestId: String, @SerializedName("expires_at") val expiresAt: Long)

object SecureVerificationManager {
 private const val TAG="PaymentVerifier"
 private val current=AtomicReference<VerificationPayload?>(null)
 private var registered=false
 private val gson=Gson()
 private val api get()=com.paymentverifier.app.data.api.ApiClient.create()
 private fun auth()="Bearer ${DeviceConfig.API_TOKEN}"
 suspend fun initialize()=withContext(Dispatchers.IO){ try { val r=api.registerKey(auth(),DeviceConfig.DEVICE_ID,DeviceKeyRequest(CryptoManager.publicKeyBase64())); registered=r.isSuccessful && r.body()?.success==true } catch(e:Exception){Log.e(TAG,"Key registration failed",e)} }
 suspend fun poll()=withContext(Dispatchers.IO){ if(!registered) initialize(); try { val r=api.pending(auth(),DeviceConfig.DEVICE_ID); if(!r.isSuccessful)return@withContext; val b=r.body()?:return@withContext; if(b.pending==true&&!b.payload.isNullOrBlank()){val p=gson.fromJson(CryptoManager.decrypt(b.payload),VerificationPayload::class.java); if(p.expiresAt*1000>System.currentTimeMillis())current.set(p)} else if(b.pending==false)current.set(null) } catch(e:Exception){Log.e(TAG,"Pending verification poll failed",e)} }
 fun matches(phone:String?,tx:String)=current.get()?.let{normalize(phone)==normalize(it.phone)&&tx.equals(it.transactionId,true)&&it.expiresAt*1000>System.currentTimeMillis()}==true
 suspend fun submit(approved:Boolean,reason:String?=null)=withContext(Dispatchers.IO){val p=current.get()?:return@withContext; val canonical="${p.requestId}|${if(approved)1 else 0}|${reason.orEmpty()}"; try{val r=api.result(auth(),DeviceConfig.DEVICE_ID,VerificationResultRequest(p.requestId,approved,CryptoManager.sign(canonical),reason));if(r.isSuccessful)current.compareAndSet(p,null)}catch(e:Exception){Log.e(TAG,"Verification result failed",e)}}
 fun hasPending()=current.get()!=null
 private fun normalize(v:String?):String{val d=(v?:"").filter{it.isDigit()};return when{d.startsWith("880")->d.substring(2);d.startsWith("0")->d.substring(1);else->d}}
}
