package tk.pankajb.apitest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import tk.pankajb.apitest.databinding.ActivityMapBinding;
import tk.pankajb.apitest.models.LocationCoordinates;
import tk.pankajb.apitest.repositories.MainRepository;
import tk.pankajb.apitest.services.CurrentLocationService;

public class MapActivity extends AppCompatActivity {

    private ActivityMapBinding binding;

    private final int LOCATION_REQUEST = 202;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Current location service");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        binding.setLocationCoordinates(new LocationCoordinates());

        binding.mapLocationStartServiceButton.setOnClickListener(this::startLocationService);
        binding.mapLocationStopServiceButton.setOnClickListener(this::stopLocationService);

        MainRepository.getInstance(getApplicationContext()).getCoordinates().observe(this,
                locationCoordinates -> binding.setLocationCoordinates(locationCoordinates));

    }

    private void stopLocationService(View view) {
        Intent intent = new Intent(this, CurrentLocationService.class);
        stopService(intent);
    }

    private void startLocationService(View view) {
        if (isLocationPermissionGranted()) {
            Intent intent = new Intent(this, CurrentLocationService.class);
            startService(intent);
        } else {
            Toast.makeText(this, "Location and Network permission needed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isLocationPermissionGranted() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST
            );
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOCATION_REQUEST) {
            if (resultCode == RESULT_OK) {

                startLocationService(null);

            }
        }
    }
}