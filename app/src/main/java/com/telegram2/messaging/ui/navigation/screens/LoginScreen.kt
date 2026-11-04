package com.telegram2.messaging.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthCredential

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var phone by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var verificationId by remember { mutableStateOf("") }
    var codeSent by remember { mutableStateOf(false) }
    val auth = FirebaseAuth.getInstance()

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        if (!codeSent) {
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone number") })
            Button(onClick = {
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(phone)
                    .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS)
                    .setActivity(activity!!)
                    .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(credential: PhoneAuthCredential) { /* auto signin */ }
                        override fun onVerificationFailed(e: Exception) { /* error */ }
                        override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                            verificationId = id
                            codeSent = true
                        }
                    }).build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            }) { Text("Send Code") }
        } else {
            OutlinedTextField(value = code, onValueChange = { code = it }, label = { Text("Code") })
            Button(onClick = {
                val credential = PhoneAuthProvider.getCredential(verificationId, code)
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) onLoginSuccess()
                }
            }) { Text("Sign In") }
        }
    }
}