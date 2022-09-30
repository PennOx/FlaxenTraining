package tk.pankajb.apitest.roomTest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import tk.pankajb.apitest.R;
import tk.pankajb.apitest.databinding.ActivityRoomDatabaseCrudactivityBinding;

public class RoomDatabaseCRUDActivity extends AppCompatActivity {

    private ActivityRoomDatabaseCrudactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room_database_crudactivity);

        getSupportActionBar().setTitle("Room Database CRUD");

        binding.roomViewUsersButton.setOnClickListener(this::viewUsers);
        binding.roomAddUsersButton.setOnClickListener(this::addUsers);
    }

    private void addUsers(View view) {
        Intent intent = new Intent(this, RoomAddUserActivity.class);
        startActivity(intent);
    }

    private void viewUsers(View view) {
        Intent intent = new Intent(this, RoomViewUsersActivity.class);
        startActivity(intent);
    }
}