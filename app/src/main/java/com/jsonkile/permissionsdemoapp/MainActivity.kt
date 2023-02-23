package com.jsonkile.permissionsdemoapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jsonkile.permissionsdemoapp.ui.theme.PermissionsDemoAppTheme
import com.jsonkile.permissionsmanager.PermissionsManager

class MainActivity : ComponentActivity() {

    private lateinit var permissionsManager: PermissionsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionsManager = PermissionsManager(this)

        setContent {
            PermissionsDemoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }

        permissionsManager.run(
            permission = android.Manifest.permission.POST_NOTIFICATIONS,
            rationaleDialogCallBack = {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("Missing permission")
                alertDialogBuilder.setMessage("This app needs your permission to continue.")
                alertDialogBuilder.setPositiveButton("Yes") { _, _ -> it() }
                alertDialogBuilder.setNegativeButton("No") { _, _ -> }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            },
            permissionDeniedCallback = {},
            permissionGrantedCallback = {
                Toast.makeText(this, "Allowed!", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PermissionsDemoAppTheme {
        Greeting("Android")
    }
}