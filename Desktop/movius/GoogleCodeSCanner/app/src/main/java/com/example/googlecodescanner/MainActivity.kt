package com.example.googlecodescanner

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.googlecodescanner.ui.theme.GoogleCodeSCannerTheme
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gmsScannerOptions = configureScannerOption()
        val instance = getBarcodeScannerInstance(gmsScannerOptions)
        setContent {
            GoogleCodeSCannerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android") {
                        initiateScanner(instance)
                    }
                }
            }
        }
    }

    private fun configureScannerOption(): GmsBarcodeScannerOptions {
        return GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC
            )
            .build()
    }

    private fun getBarcodeScannerInstance(gmsBarcodeScannerOptions: GmsBarcodeScannerOptions): GmsBarcodeScanner {
        return GmsBarcodeScanning.getClient(this, gmsBarcodeScannerOptions)
    }


    private fun initiateScanner(gmsBarcodeScanner: GmsBarcodeScanner) {
        gmsBarcodeScanner.startScan()
            .addOnSuccessListener { barcode ->
                // Task completed successfully
                val result: String? = barcode.rawValue
                Log.d(TAG, "initiateScanner: $result")

                when (barcode.valueType) {
                    Barcode.TYPE_URL -> {
                        Log.d(TAG, "initiateScanner: ${barcode.valueType}")
                    }

                    else -> {
                        Log.d(TAG, "initiateScanner: ${barcode.valueType}")
                    }
                }

                Log.d(TAG, "initiateScanner: Display value ${barcode.displayValue}")
                Log.d(TAG, "initiateScanner: Display value ${barcode.format}")

            }
            .addOnCanceledListener {
                // Task canceled
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
            }
    }

}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )

        OutlinedButton(onClick = { onClick() }) {
            Text(text = "Scan your code")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoogleCodeSCannerTheme {
        Greeting("Android")
    }
}