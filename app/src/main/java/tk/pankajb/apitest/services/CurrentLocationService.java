package tk.pankajb.apitest.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.TimeUnit;

import tk.pankajb.apitest.models.LocationCoordinates;
import tk.pankajb.apitest.repositories.MainRepository;

public class CurrentLocationService extends Service {

    private MainRepository repo;

    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private final static int INTERVAL = 20000;
    Handler m_handler = new Handler();

    Runnable m_handlerTask = new Runnable() {
        @Override
        public void run() {
            m_handler.postDelayed(m_handlerTask, INTERVAL);

            if (ActivityCompat.checkSelfPermission(CurrentLocationService.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(CurrentLocationService.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                        .addOnCompleteListener(task -> {
                            Toast.makeText(CurrentLocationService.this, "Updating location", Toast.LENGTH_SHORT).show();
                            Log.i("LocationUpdate", "Updating location");
                        });
            }
        }
    };


    public CurrentLocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        repo = MainRepository.getInstance(getApplicationContext());


        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(TimeUnit.MICROSECONDS.toMillis(0));
        locationRequest.setFastestInterval(TimeUnit.SECONDS.toMillis(10));
        locationRequest.setMaxWaitTime(TimeUnit.SECONDS.toMillis(30));

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                LocationCoordinates coordinates = new LocationCoordinates(locationResult.getLastLocation().getLatitude(),
                        locationResult.getLastLocation().getLongitude());
                repo.setCoordinates(coordinates);
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
        Log.i("LocationUpdate", "Service started");

        m_handlerTask.run();
        startMyOwnForeground();

        return START_STICKY;

    }

    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "tk.pankajb.apitest";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        locationProviderClient.removeLocationUpdates(locationCallback).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                m_handler.removeCallbacks(m_handlerTask);
                stopForeground(Service.STOP_FOREGROUND_REMOVE);
                Toast.makeText(CurrentLocationService.this, "Service stopped", Toast.LENGTH_SHORT).show();
                Log.i("LocationUpdate", "Service stopped");
            } else {
                Toast.makeText(CurrentLocationService.this, "Failed to stop service", Toast.LENGTH_SHORT).show();
                Log.i("LocationUpdate", "Service failed to stop");
            }
        });


    }
}