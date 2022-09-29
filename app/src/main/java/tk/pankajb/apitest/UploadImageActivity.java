package tk.pankajb.apitest;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import java.io.IOException;

import tk.pankajb.apitest.databinding.ActivityUploadImageBinding;
import tk.pankajb.apitest.halpers.ImageEncoder;
import tk.pankajb.apitest.viewModels.MainViewModel;

public class UploadImageActivity extends AppCompatActivity {

    private ActivityUploadImageBinding binding;
    private MainViewModel viewModel;

    private ActivityResultLauncher<Intent> activityResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_image);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MainViewModel();
            }
        }).get(MainViewModel.class);

        viewModel.getMessage().observe(this,
                s -> Toast.makeText(UploadImageActivity.this, s, Toast.LENGTH_SHORT).show());

        viewModel.isImageUploaded().observe(this,
                aBoolean -> {
                    if (aBoolean) {
                        Toast.makeText(UploadImageActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                        viewModel.resetImageUpload();
                    }
                });

        viewModel.getImage().observe(this,
                uri -> Glide.with(UploadImageActivity.this).load(uri).into(binding.uploadImageImageview));

        activityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == RESULT_OK) {

                        viewModel.setImage(result.getData().getData());
                    }

                    if (result.getResultCode() == RESULT_CANCELED) {
                        viewModel.setImage(null);
                        Toast.makeText(UploadImageActivity.this, "User cancelled", Toast.LENGTH_SHORT).show();
                    }

                });

        binding.uploadSelectImageButton.setOnClickListener(this::selectImage);
        binding.uploadUploadImageButton.setOnClickListener(this::uploadImage);

    }

    private void uploadImage(View view) {

        if (viewModel.getImage().getValue() == null) {
            Toast.makeText(this, "Please select Image", Toast.LENGTH_SHORT).show();
        } else {
            try {

                viewModel.uploadImage(ImageEncoder.encodeImage(getContentResolver(), viewModel.getImage().getValue()));

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void selectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResult.launch(intent);
    }
}