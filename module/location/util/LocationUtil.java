package com.location.tracker.module.location.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;

public class LocationUtil {
    private static final String TAG = LocationUtil.class.getName();

    public static boolean hasLocationFeature(@NonNull Context context) {
        // Returning result
        boolean result = false;
        // - Returning result

        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION)) {
            result = true;
        }

        return result;
    }

    public static boolean isProviderAvailable(@NonNull Context context, @NonNull String provider) {
        // Returning result
        boolean result = false;
        // - Returning result

        switch (provider) {
            case LocationManager.NETWORK_PROVIDER:
                if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_NETWORK)) {
                    result = true;
                }

                break;

            case LocationManager.GPS_PROVIDER:
                if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)) {
                    result = true;
                }

                break;

            case LocationManager.PASSIVE_PROVIDER:
                result = true;

                break;
        }

        return result;
    }
}
