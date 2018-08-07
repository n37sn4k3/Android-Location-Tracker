package com.location.tracker.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;

import com.location.tracker.R;

public class AppUtil {
    private static final String TAG = AppUtil.class.getName();

    // Wake lock
    @SuppressLint("WakelockTimeout")
    @RequiresPermission(Manifest.permission.WAKE_LOCK)
    public static void acquireWakeLock(@NonNull PowerManager.WakeLock wakeLock, @Nullable Long timeout) {
        /*if (wakeLock == null) {
            LogUtil.w(TAG, "Cannot acquire wake lock");

            return;
        }*/

        LogUtil.d(TAG, "Trying to acquire wake lock...");
        try {
            if (timeout == null) {
                LogUtil.d(TAG, "Acquiring wake lock (no timeout)");

                wakeLock.acquire();
            } else {
                LogUtil.d(TAG, "Acquiring wake lock (with timeout)");

                wakeLock.acquire(timeout);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "Exception while trying to acquire wake lock");

            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        try {
            if (wakeLock.isHeld()) {
                LogUtil.d(TAG, "Wake lock acquired");

                return;
            } else {
                LogUtil.d(TAG, "Wake lock not acquired");

                return;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        LogUtil.d(TAG, "Wake lock not acquired");
    }

    @RequiresPermission(Manifest.permission.WAKE_LOCK)
    public static void releaseWakeLock(@NonNull PowerManager.WakeLock wakeLock) {
        /*if (wakeLock == null) {
            LogUtil.w(TAG, "Cannot release wake lock");

            return;
        }*/

        LogUtil.d(TAG, "Trying to release wake lock...");
        try {
            wakeLock.release();
        } catch (Exception e) {
            LogUtil.e(TAG, "Exception while trying to release wake lock");

            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        try {
            if (wakeLock.isHeld()) {
                LogUtil.d(TAG, "Wake lock not released");

                return;
            } else {
                LogUtil.d(TAG, "Wake lock released");

                return;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        LogUtil.d(TAG, "Wake lock not released");
    }
    // - Wake lock
}
