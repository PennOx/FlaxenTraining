package tk.pankajb.apitest.roomTest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import tk.pankajb.apitest.R;
import tk.pankajb.apitest.databinding.ActivityRoomAddUserBinding;
import tk.pankajb.apitest.models.User;
import tk.pankajb.apitest.viewModels.MainViewModel;

public class RoomAddUserActivity extends AppCompatActivity {

    private ActivityRoomAddUserBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room_add_user);
        binding.setUser(new User());

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MainViewModel(getApplication());
            }
        }).get(MainViewModel.class);

        binding.addUserRoomSubmitButton.setOnClickListener(this::addUser);

        viewModel.isUserAdded().observe(this, aBoolean -> {
            if (aBoolean){
                Toast.makeText(RoomAddUserActivity.this, "User added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void addUser(View view) {
        viewModel.addUserToRoom(binding.getUser());
    }
}