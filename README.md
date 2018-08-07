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

### How to use it (with examples)
Note(s):
- After the "LocationService" binding connect, you have to request track type with details by calling one of the its methods from the method overloading (from "MainService").
- To start requesting periodical location updates you must request them right after the "LocationService" is successfully bound in "onServiceConnected(ComponentName name, IBinder service)" overridden method of the "ServiceConnection" interface only if the required permissions are granted.
- To stop requesting periodical location updates just simply stop "LocationService" or call the its "removeLocationUpdates()" method (second one is not recommended).


```java
// Service(s)
if (LocationUtil.hasLocationFeature(this)) {
    try {
        mLocationServiceConnection = new ServiceConnection() {
            LocationService locationService;

            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                LogUtil.d(TAG, "Service connect");

                LogUtil.i(TAG, "Service connect: \"" + componentName.getShortClassName() + "\"");

                LocationService.LocalBinder localBinder = null;
                try {
                    localBinder = (LocationService.LocalBinder) iBinder;
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }

                if (localBinder != null) {
                    try {
                        locationService = localBinder.getService();
                    } catch (Exception e) {
                        LogUtil.e(TAG, e.getMessage());
                        LogUtil.e(TAG, e.toString());

                        e.printStackTrace();
                    }

                    if (locationService != null) {
                        try {
                            // Service connect relative
                            if (ActivityCompat.checkSelfPermission(MainService.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainService.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                // Request single location update or periodical location updates
                            }
                            // - Service connect relative
                        } catch (Exception e) {
                            LogUtil.e(TAG, e.getMessage());
                            LogUtil.e(TAG, e.toString());

                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                LogUtil.d(TAG, "Service disconnect");

                LogUtil.i(TAG, "Service disconnect: \"" + componentName.getShortClassName() + "\"");

                if (locationService != null) {
                    try {
                        // Service disconnect relative
                        if (ActivityCompat.checkSelfPermission(MainService.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainService.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            locationService.removeUpdates();
                        }
                        // - Service disconnect relative
                    } catch (Exception e) {
                        LogUtil.e(TAG, e.getMessage());
                        LogUtil.e(TAG, e.toString());

                        e.printStackTrace();
                    }
                }
            }
        };
    } catch (Exception e) {
        LogUtil.e(TAG, e.getMessage());
        LogUtil.e(TAG, e.toString());

        e.printStackTrace();
    }

    try {
        startService(new Intent(this, LocationService.class));
    } catch (Exception e) {
        LogUtil.e(TAG, e.getMessage());
        LogUtil.e(TAG, e.toString());

        e.printStackTrace();
    }

    if (mLocationServiceConnection != null) {
        try {
            bindService(new Intent(this, LocationService.class), mLocationServiceConnection, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }
    }
}
// - Service(s)
```

<hr />

<p>Here is an example for requesting single location update with specified provider:</p>
<ul>
  <li>Provider: Network</li>
</ul>

```java
locationService.requestSingleUpdate(LocationManager.NETWORK_PROVIDER);
```

<hr />

<p>Here is an example for requesting periodical location updates with specified provider and time and distance between them:</p>
<ul>
  <li>Provider: GPS</li>
  <li>Minimum time interval between location updates, in milliseconds: 60000 (60 seconds, 1 minute)</li>
  <li>Minimum distance between location updates, in meters: 20</li>
</ul>

```java
locationService.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000L, 20);
```

<hr />

<p>Here is an example for requesting single location update with criteria best provider:</p>
<ul>
  <li>Criteria: Fine accuracy</li>
  <li>Provider: true; "true" for currently enabled location provider, "false" for not. When using "true" as a value, make sure that the device location is enabled.</li>
</ul>

```java
Criteria criteria = new Criteria();
criteria.setAccuracy(Criteria.ACCURACY_FINE);

locationService.requestSingleUpdate(criteria, true);
```

<hr />

<p>Here is an example for requesting periodical location updates with criteria best provider and time and distance between them:</p>
<ul>
  <li>Criteria: Fine accuracy</li>
  <li>Provider: true; "true" for currently enabled location provider, "false" for not. When using "true" as a value, make sure that the device location is enabled.</li>
  <li>Minimum time interval between location updates, in milliseconds: 60000 (60 seconds, 1 minute)</li>
  <li>Minimum distance between location updates, in meters: 20</li>
</ul>

```java
Criteria criteria = new Criteria();
criteria.setAccuracy(Criteria.ACCURACY_FINE);

locationService.requestLocationUpdates(criteria, true, 60000L, 20);
```


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
