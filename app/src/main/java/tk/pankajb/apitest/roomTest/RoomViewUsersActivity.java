package tk.pankajb.apitest.roomTest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import tk.pankajb.apitest.R;
import tk.pankajb.apitest.adapters.UsersRecyclerAdapter;
import tk.pankajb.apitest.databinding.ActivityRoomViewUsersBinding;
import tk.pankajb.apitest.models.User;
import tk.pankajb.apitest.viewModels.MainViewModel;

public class RoomViewUsersActivity extends AppCompatActivity {

    private ActivityRoomViewUsersBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room_view_users);

        binding.viewUsersRoomApiRecycler.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MainViewModel(getApplication());
            }
        }).get(MainViewModel.class);

        viewModel.getUsersFromRoom().observe(this,
                userList -> {
                    UsersRecyclerAdapter adapter = new UsersRecyclerAdapter(userList);
                    binding.viewUsersRoomApiRecycler.setAdapter(adapter);
        });

        viewModel.getMessage().observe(this,
                s -> Toast.makeText(RoomViewUsersActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show());


    }
}