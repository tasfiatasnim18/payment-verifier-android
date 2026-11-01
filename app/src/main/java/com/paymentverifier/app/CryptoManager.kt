package com.paymentverifier.app
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.*
import javax.crypto.Cipher
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource
import java.security.spec.MGF1ParameterSpec
object CryptoManager {
 private const val ALIAS="pv_device_key"; private const val KS="AndroidKeyStore"
 private fun ks()=KeyStore.getInstance(KS).apply{load(null)}
 private fun ensure(){val k=ks();if(k.containsAlias(ALIAS))return;val g=KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA,KS);g.initialize(KeyGenParameterSpec.Builder(ALIAS,KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_DECRYPT).setKeySize(2048).setDigests(KeyProperties.DIGEST_SHA256,KeyProperties.DIGEST_SHA1).setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP).build());g.generateKeyPair()}
 fun publicKeyBase64():String{ensure();return Base64.encodeToString(ks().getCertificate(ALIAS).publicKey.encoded,Base64.NO_WRAP)}
 fun decrypt(v:String):String{ensure();val c=Cipher.getInstance("RSA/ECB/OAEPPadding");c.init(Cipher.DECRYPT_MODE,ks().getKey(ALIAS,null),OAEPParameterSpec("SHA-1","MGF1",MGF1ParameterSpec.SHA1,PSource.PSpecified.DEFAULT));return String(c.doFinal(Base64.decode(v,Base64.NO_WRAP)),StandardCharsets.UTF_8)}
 fun sign(v:String):String{ensure();val s=Signature.getInstance("SHA256withRSA");s.initSign(ks().getKey(ALIAS,null) as PrivateKey);s.update(v.toByteArray(StandardCharsets.UTF_8));return Base64.encodeToString(s.sign(),Base64.NO_WRAP)}
}
