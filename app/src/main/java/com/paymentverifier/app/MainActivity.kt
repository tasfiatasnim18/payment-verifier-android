package com.paymentverifier.app
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
class MainActivity:ComponentActivity(){private val launcher=registerForActivityResult(ActivityResultContracts.RequestPermission()){ };override fun onCreate(b:Bundle?){super.onCreate(b);if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS)!=PackageManager.PERMISSION_GRANTED)launcher.launch(Manifest.permission.RECEIVE_SMS);lifecycleScope.launch{SmsRuleManager.refresh();SecureVerificationManager.initialize();while(true){SecureVerificationManager.poll();delay(3000)}};setContent{Column(Modifier.fillMaxSize().padding(24.dp)){Text("Payment Verifier",style=MaterialTheme.typography.headlineLarge);Spacer(Modifier.height(16.dp));Text("Secure local verification is active.");Spacer(Modifier.height(8.dp));Text("Only incoming payment SMS broadcasts from configured senders are inspected. No SMS body, sender number, amount, reference or transaction ID is uploaded.")}}}}
