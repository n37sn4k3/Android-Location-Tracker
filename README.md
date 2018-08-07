# Android-Location-Tracker
Android module which tracks device location using a bound background service with a partial wake lock and saves the updates into a Realm database.

## Information
Supported provider(s):
- Network
- GPS
- Passive

Track type(s):
- Single update (with criteria or specified provider)
- Periodical updates (with criteria or specified provider)
- Last known location

Start tracking (step(s) to follow):
1. Start "MainService" (will acquire the wake lock and prepare the binding)
2. Start "LocationService"
3. Bind "LocationService"

Stop tracking (step(s) to follow):
1. Unbind "LocationService"
2. Stop "LocationService"
3. Stop "MainService" (will release the wake lock and finalize the binding)

Note(s):
- After the "LocationService" binding connect, you have to request track type with details by calling one of the its methods from the method overloading (from "MainService").

## Manifest requirement(s)
Permission(s) (Also need to be runtime requested on 6.0+ (API 23+))
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    
    <uses-permission android:name="android.permission.WAKE_LOCK" />
</manifest>


Application
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- Service -->
        <service android:name=".service.MainService"
            android:label="@string/service"
            android:description="@string/additional_service" />
            
        <!-- Module "Location" -->
        <service android:name=".module.location.service.LocationService"
            android:label="@string/service"
            android:description="@string/additional_service" />
        <!-- - Module "Location" -->
    <!-- - Service -->
</manifest>

## License
This project is released under the The GNU General Public License v3.0. See "LICENSE" file for further information.
