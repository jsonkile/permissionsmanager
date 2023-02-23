# Permissions Manager
Lightweight kotlin lib to handle runtime permissions on android using Google's [guidelines](https://developer.android.com/guide/topics/permissions/overview).


## Sample
```
class MainActivity : ComponentActivity() {
    private lateinit var permissionsManager: PermissionsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionsManager = PermissionsManager(this) //initialize in onCreate()

        ...

        permissionsManager.run(
            permission = android.Manifest.permission.POST_NOTIFICATIONS,
            rationaleDialogCallBack = { dialogAcceptAction ->
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("Missing permission")
                alertDialogBuilder.setMessage("This app needs your permission to continue.")
                alertDialogBuilder.setPositiveButton("Yes") { _, _ -> dialogAcceptAction() }
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
```

Add the action that requires the permission in the **permissionGrantedCallback** lambda. The rationale dialog is used to explain to the user why they should consider giving your app permission.

Note: Do not forget to add the permission to your manifest file as well.

```
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

## Misc
Check permission status
```
...
permissionsManager.checkIfPermissionIsGranted(android.Manifest.permission.POST_NOTIFICATIONS)
...
```

That's it...
