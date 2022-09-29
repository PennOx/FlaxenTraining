package tk.pankajb.apitest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import tk.pankajb.apitest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.mainAddUser.setOnClickListener(this::addUser);
        binding.mainViewUsers.setOnClickListener(this::viewUsers);
        binding.mainUploadImage.setOnClickListener(this::uploadImage);
        binding.mainMapLocation.setOnClickListener(this::mapLocation);

    }

    private void mapLocation(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    private void uploadImage(View view) {
        Intent intent = new Intent(this, UploadImageActivity.class);
        startActivity(intent);
    }

    private void viewUsers(View view) {
        Intent intent = new Intent(this, ViewUsersActivity.class);
        startActivity(intent);
    }

    private void addUser(View view) {
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }
}