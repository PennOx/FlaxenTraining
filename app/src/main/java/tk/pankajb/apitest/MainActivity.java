package tk.pankajb.apitest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import tk.pankajb.apitest.firebaseTest.FirebaseCRUDMainActivity;
import tk.pankajb.apitest.imageApi.UploadImageActivity;
import tk.pankajb.apitest.roomTest.RoomDatabaseCRUDActivity;
import tk.pankajb.apitest.usersApi.AddUserActivityToApi;
import tk.pankajb.apitest.usersApi.UsersApiMainActivity;
import tk.pankajb.apitest.usersApi.ViewUsersFromApiActivity;
import tk.pankajb.apitest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getSupportActionBar().setTitle("Main Activity");

       binding.mainUsersApi.setOnClickListener(this::usersApi);
        binding.mainUploadImage.setOnClickListener(this::uploadImage);
        binding.mainMapLocation.setOnClickListener(this::mapLocation);
        binding.mainRoomCrud.setOnClickListener(this::roomCRUD);
//        binding.mainFirebaseCrud.setOnClickListener(this::firebaseCRUD);

    }

    private void usersApi(View view) {
        Intent intent = new Intent(this, UsersApiMainActivity.class);
        startActivity(intent);
    }

    private void firebaseCRUD(View view) {
        Intent intent = new Intent(this, FirebaseCRUDMainActivity.class);
        startActivity(intent);
    }

    private void roomCRUD(View view) {
        Intent intent = new Intent(this, RoomDatabaseCRUDActivity.class);
        startActivity(intent);
    }

    private void mapLocation(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    private void uploadImage(View view) {
        Intent intent = new Intent(this, UploadImageActivity.class);
        startActivity(intent);
    }

}