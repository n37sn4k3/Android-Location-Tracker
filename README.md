# Android-Location-Tracker
Android module which tracks device location using a bound background service with a partial wake lock and saves the updates into a Realm database.

## Manifest requirement(s)
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- Module "Location" -->
    <service android:name=".module.location.service.LocationService"
        android:label="@string/service"
        android:description="@string/additional_service" />
    <!-- - Module "Location" -->
</manifest>

## License
This project is released under the The GNU General Public License v3.0. See "LICENSE" file for further information.
