package tk.pankajb.apitest.usersApi;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import tk.pankajb.apitest.R;
import tk.pankajb.apitest.databinding.ActivityAddUserToApiBinding;
import tk.pankajb.apitest.models.User;
import tk.pankajb.apitest.viewModels.MainViewModel;

public class AddUserActivityToApi extends AppCompatActivity {

    private ActivityAddUserToApiBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_user_to_api);
        binding.setUser(new User());
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MainViewModel(getApplication());
            }
        }).get(MainViewModel.class);

        binding.addUserSubmitButton.setOnClickListener(this::submit);

        viewModel.getMessage().observe(this, s -> Toast.makeText(AddUserActivityToApi.this, s, Toast.LENGTH_SHORT).show());

        viewModel.isUserAdded().observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(AddUserActivityToApi.this, "User added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void submit(View view) {
        User u = binding.getUser();

        if (u.getName().isEmpty()) {
            binding.addUserNameInputLayout.setError("Name required");
        } else if (u.getAge().isEmpty()) {
            binding.addUserAgeInputLayout.setError("Age required");
        } else if (u.getGender().isEmpty()) {
            binding.addUserGenderInputLayout.setError("Gender required");
        } else if (u.getMobile().isEmpty()) {
            binding.addUserMobileInputLayout.setError("Mobile number required");
        } else {
            viewModel.addUserToApi(u);
        }
    }
}