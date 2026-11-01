package com.paymentverifier.app.data.api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ApiClient { private const val BASE_URL="https://192.168.31.121/payment-verifier-test/wp-json/payment-verifier/v1/"; fun create(deviceId:String="",apiToken:String=""):PaymentVerifierApi=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(PaymentVerifierApi::class.java) }
