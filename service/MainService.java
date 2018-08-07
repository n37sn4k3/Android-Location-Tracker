package com.location.tracker.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.location.tracker.R;
import com.location.tracker.module.location.service.LocationService;
import com.location.tracker.module.location.util.LocationUtil;
import com.location.tracker.util.AppUtil;
import com.location.tracker.util.LogUtil;

public class MainService extends Service {
    private static final String TAG = MainService.class.getSimpleName();

    private NotificationManager mNotificationManager = null;

    private PowerManager mPowerManager = null;
    private PowerManager.WakeLock mWakeLock = null;

    // Service(s) connection(s)
    private ServiceConnection mLocationServiceConnection = null;
    // - Service(s) connection(s)

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        LogUtil.d(TAG, "\"" + TAG + "\": Service start command");

        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG, "\"" + TAG + "\": Service create");

        // Foreground notification (start)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }

            if (mNotificationManager != null) {
                try {
                    mNotificationManager.createNotificationChannel(new NotificationChannel(getText(R.string.service).toString(), getText(R.string.service).toString(), NotificationManager.IMPORTANCE_NONE));
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                } finally {
                    try {
                        startForeground(1, new Notification.Builder(this, getText(R.string.service).toString()).build());
                    } catch (Exception e) {
                        LogUtil.e(TAG, e.getMessage());
                        LogUtil.e(TAG, e.toString());

                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    stopSelf();
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }
            }
        }
        // - Foreground notification (start)

        try {
            mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        if (mPowerManager != null) {
            try {
                mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getString(R.string.app_name));
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }

            if (mWakeLock != null) {
                AppUtil.acquireWakeLock(mWakeLock, null);
            }
        }

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
                                        locationService.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5 * 60 * 1000L, 60);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "\"" + TAG + "\": Service destroy");

        // Service(s)
        if (LocationUtil.hasLocationFeature(this)) {
            if (mLocationServiceConnection != null) {
                try {
                    unbindService(mLocationServiceConnection);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }

                mLocationServiceConnection = null;
            }

            try {
                stopService(new Intent(this, LocationService.class));
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        }
        // - Service(s)

        if (mPowerManager != null) {
            if (mWakeLock != null) {
                AppUtil.releaseWakeLock(mWakeLock);

                mWakeLock = null;
            }

            mPowerManager = null;
        }

        // Foreground notification (stop)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mNotificationManager != null) {
                try {
                    stopForeground(true);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }

                mNotificationManager = null;
            }
        }
        // - Foreground notification (stop)
    }
}
