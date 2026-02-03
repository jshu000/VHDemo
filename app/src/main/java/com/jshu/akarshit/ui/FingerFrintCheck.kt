package com.jshu.akarshit.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.jshu.akarshit.R
import com.jshu.akarshit.ui.theme.AkarshitTheme
import java.util.concurrent.Executor


class FingerFrintCheck : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val biometricManager = BiometricManager.from(this)

        setContent {
            AkarshitTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    FingerprintLoginScreen(
                        activity = this, // AppCompatActivity IS a FragmentActivity ✅
                        canAuthenticate =
                            biometricManager.canAuthenticate(
                                BiometricManager.Authenticators.BIOMETRIC_STRONG
                            ) == BiometricManager.BIOMETRIC_SUCCESS,
                        onAuthSuccess = {
                            startActivity(Intent(this, VisitHealthActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FingerprintLoginScreen(
    activity: FragmentActivity,
    canAuthenticate: Boolean,
    onAuthSuccess: () -> Unit
) {
    val executor: Executor = ContextCompat.getMainExecutor(activity)

    val biometricPrompt = remember {
        BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        activity,
                        "Fingerprint Matched, Login Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    onAuthSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        activity,
                        "Fingerprint Not Matched",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        activity,
                        "Error: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    val promptInfo = remember {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Use fingerprint to access the app")
            .setNegativeButtonText("Cancel")
            .build()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.group_48097013__2_),
            contentDescription = "Fingerprint Icon",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (canAuthenticate) {
                biometricPrompt.authenticate(promptInfo)
            } else {
                Toast.makeText(
                    activity,
                    "Biometric authentication not available",
                    Toast.LENGTH_LONG
                ).show()
            }
        }) {
            Text("LOGIN WITH FINGERPRINT")
        }
    }
}
