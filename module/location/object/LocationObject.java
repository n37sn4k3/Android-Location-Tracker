package com.location.tracker.module.location.object;

import io.realm.RealmObject;

public class LocationObject extends RealmObject { // Realm object used in "LocationService"
    private String mProvider;
    private double mLatitude;
    private double mLongitude;
    private long mTime;
    private long mElapsedRealtimeNanos;
    /*private Bundle mExtras;*/
    private float mAccuracy;
    private float mVerticalAccuracyMeters;
    private double mAltitude;
    private float mBearing;
    private float mBearingAccuracyDegrees;
    private float mSpeed;
    private float mSpeedAccuracyMetersPerSecond;

    public String getProvider() {
        return mProvider;
    }
    public void setProvider(String provider) {
        this.mProvider = provider;
    }

    public double getLatitude() {
        return mLatitude;
    }
    public void setLatitude(double latitude) {
        this.mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }
    public void setLongitude(double longitude) {
        this.mLongitude = longitude;
    }

    public long getTime() {
        return mTime;
    }
    public void setTime(long time) {
        this.mTime = time;
    }

    public long getElapsedRealtimeNanos() {
        return mElapsedRealtimeNanos;
    }
    public void setElapsedRealtimeNanos(long elapsedRealtimeNanos) {
        this.mElapsedRealtimeNanos = elapsedRealtimeNanos;
    }

    /*public Bundle getExtras() {
        return mExtras;
    }
    public void setExtras(Bundle extras) {
        this.mExtras = extras;
    }*/

    public float getAccuracy() {
        return mAccuracy;
    }
    public void setAccuracy(float accuracy) {
        this.mAccuracy = accuracy;
    }

    public float getVerticalAccuracyMeters() {
        return mVerticalAccuracyMeters;
    }
    public void setVerticalAccuracyMeters(float verticalAccuracyMeters) {
        this.mVerticalAccuracyMeters = verticalAccuracyMeters;
    }

    public double getAltitude() {
        return mAltitude;
    }
    public void setAltitude(double altitude) {
        this.mAltitude = altitude;
    }

    public float getBearing() {
        return mBearing;
    }
    public void setBearing(float bearing) {
        this.mBearing = bearing;
    }

    public float getBearingAccuracyDegrees() {
        return mBearingAccuracyDegrees;
    }
    public void setBearingAccuracyDegrees(float bearingAccuracyDegrees) {
        this.mBearingAccuracyDegrees = bearingAccuracyDegrees;
    }

    public float getSpeed() {
        return mSpeed;
    }
    public void setSpeed(float speed) {
        this.mSpeed = speed;
    }

    public float getSpeedAccuracyMetersPerSecond() {
        return mSpeedAccuracyMetersPerSecond;
    }
    public void setSpeedAccuracyMetersPerSecond(float speedAccuracyMetersPerSecond) {
        this.mSpeedAccuracyMetersPerSecond = speedAccuracyMetersPerSecond;
    }
}
