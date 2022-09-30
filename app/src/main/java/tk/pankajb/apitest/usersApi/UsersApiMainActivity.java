package tk.pankajb.apitest.usersApi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import tk.pankajb.apitest.R;
import tk.pankajb.apitest.databinding.ActivityUsersApiMainBinding;

public class UsersApiMainActivity extends AppCompatActivity {

    private ActivityUsersApiMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users_api_main);

        binding.mainAddUser.setOnClickListener(this::addUser);
        binding.mainViewUsers.setOnClickListener(this::viewUsers);
    }

    private void viewUsers(View view) {
        Intent intent = new Intent(this, ViewUsersFromApiActivity.class);
        startActivity(intent);
    }

    private void addUser(View view) {
        Intent intent = new Intent(this, AddUserActivityToApi.class);
        startActivity(intent);
    }
}