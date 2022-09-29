package tk.pankajb.apitest;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import tk.pankajb.apitest.adapters.UsersRecyclerAdapter;
import tk.pankajb.apitest.databinding.ActivityViewUsersBinding;
import tk.pankajb.apitest.viewModels.MainViewModel;

public class ViewUsersActivity extends AppCompatActivity {

    private ActivityViewUsersBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_users);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MainViewModel();
            }
        }).get(MainViewModel.class);

        binding.viewUsersRecycler.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getUsers().observe(this, users -> {
            UsersRecyclerAdapter adapter = new UsersRecyclerAdapter(users);
            binding.viewUsersRecycler.setAdapter(adapter);
        });

        viewModel.getMessage().observe(this, s -> Toast.makeText(ViewUsersActivity.this, s, Toast.LENGTH_SHORT).show());

    }
}