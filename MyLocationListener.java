package com.example.ajinkyarode.chatapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import java.util.Random;

public class MyLocationListener extends Service {
    public LocationManager mlocManager;
    public LocationListener mlocListener;

    @Override
    public void onCreate() {
        super.onCreate();
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocation1();
        Toast.makeText(getApplicationContext(), "HI", Toast.LENGTH_SHORT).show();
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class MyLocation1 implements LocationListener {

        public double lo=0;
        public double la=0;
        private double new_lat=0.0;
        private double new_lon=0.0;
        private String radius=PeerDetails.ctr1;

        @Override
        public void onLocationChanged(Location loc) {
            la=loc.getLatitude();
            lo=loc.getLongitude();
            String Text = "Current location is: \n" + "Latitude = " + loc.getLatitude() + "\nLongitude = " + loc.getLongitude();
            Toast.makeText(getApplicationContext(), Text, Toast.LENGTH_SHORT).show();
            String lat= String.valueOf(la);
            String lon= String.valueOf(lo);
            int rad=0;
            /*if(radius.equals("NA")) {
                rad = 0;
            } else {
                rad = Integer.parseInt(radius);
            }*/

            String getCoord=getLocation(lo,la,rad);
            String[] coords=getCoord.split(":");
            new_lat=Double.parseDouble(coords[0]);
            new_lon=Double.parseDouble(coords[1]);
            if(new_lat<la || new_lon <lo)
            {
                /*String Text3 = "Current location is: \n" + "Latitude = " + loc.getLatitude() + "Longitude = " + loc.getLongitude();
                Toast.makeText(getApplicationContext(), Text3, Toast.LENGTH_SHORT).show();*/
            }
            else
            {
                String Text3 = "Current location is: \n" + "Latitude = " + loc.getLatitude() + "Longitude = " + loc.getLongitude();
                String Text1="Out of the boundary. Exiting...";
                String TXT= Text3 +"\n"+ Text1;
                Toast.makeText(getApplicationContext(), TXT, Toast.LENGTH_SHORT).show();

                //System.exit(0);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public String getLocation(double lon, double lat, int radius) {
            Random random = new Random();

            // Convert radius from meters to degrees
            double radiusInDegrees = radius / 111000f;
            double u = random.nextDouble();
            double v = random.nextDouble();
            double w = radiusInDegrees * Math.sqrt(u);
            double t = 2 * Math.PI * v;
            double x = w * Math.cos(t);
            double y = w * Math.sin(t);

            // Adjust the x-coordinate for the shrinking of the east-west distances
            double new_x = x / Math.cos(lat);
            double foundLongitude = new_x + lon;
            double foundLatitude = y + lat;
            String locations= String.valueOf(foundLatitude +":"+ foundLongitude);
            return locations;
        }
    }
}/* End of Class MyLocationListener */