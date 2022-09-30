package tk.pankajb.apitest.usersApi;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import tk.pankajb.apitest.R;
import tk.pankajb.apitest.adapters.UsersRecyclerAdapter;
import tk.pankajb.apitest.databinding.ActivityViewUsersFromApiBinding;
import tk.pankajb.apitest.viewModels.MainViewModel;

public class ViewUsersFromApiActivity extends AppCompatActivity {

    private ActivityViewUsersFromApiBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_users_from_api);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MainViewModel(getApplication());
            }
        }).get(MainViewModel.class);

        binding.viewUsersApiRecycler.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getUsersFromApi().observe(this, users -> {
            UsersRecyclerAdapter adapter = new UsersRecyclerAdapter(users);
            binding.viewUsersApiRecycler.setAdapter(adapter);
        });

        viewModel.getMessage().observe(this, s -> Toast.makeText(ViewUsersFromApiActivity.this, s, Toast.LENGTH_SHORT).show());

    }
}