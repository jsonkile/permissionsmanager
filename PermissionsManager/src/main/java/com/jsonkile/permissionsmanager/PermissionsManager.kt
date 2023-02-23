package com.jsonkile.permissionsmanager

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat

class PermissionsManager(private val activity: ComponentActivity) {

    private lateinit var onPermissionGrantedAction: () -> Unit
    private lateinit var onPermissionDeniedAction: () -> Unit

    private var requestPermissionLauncher: ActivityResultLauncher<String> =
        activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                onPermissionGrantedAction()
            } else {
                onPermissionDeniedAction()
            }
        }


    fun checkIfPermissionIsGranted(permission: String) = ContextCompat.checkSelfPermission(
        activity, permission
    ) == PackageManager.PERMISSION_GRANTED


    /**
     * @param permission the permission string, also add the permission to manifest file
     * @param rationaleDialogCallBack function to display rationale dialog explaining the reason for the permission
     * @param permissionDeniedCallback function to call when the permission was denied by the user
     * @param permissionGrantedCallback function to call when the permission was granted by the user
     */
    fun run(
        permission: String,
        rationaleDialogCallBack: ((() -> Unit) -> Unit),
        permissionDeniedCallback: (() -> Unit),
        permissionGrantedCallback: () -> Unit
    ) {
        this.onPermissionGrantedAction = permissionGrantedCallback
        this.onPermissionDeniedAction = permissionDeniedCallback

        when {
            ContextCompat.checkSelfPermission(
                activity, permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                permissionGrantedCallback()
            }
            shouldShowRequestPermissionRationale(activity, permission) -> {
                rationaleDialogCallBack { requestPermissionLauncher.launch(permission) }
            }
            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }
}




