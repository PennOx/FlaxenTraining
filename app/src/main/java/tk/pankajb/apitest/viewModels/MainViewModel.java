package tk.pankajb.apitest.viewModels;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import tk.pankajb.apitest.interfaces.ImageUploadListener;
import tk.pankajb.apitest.interfaces.UsersFetchListener;
import tk.pankajb.apitest.models.User;
import tk.pankajb.apitest.repositories.MainRepository;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<List<User>> usersList;
    private final MainRepository repo;
    private final MutableLiveData<String> message;
    private final MutableLiveData<Boolean> userAdded;

    private final MutableLiveData<Uri> imageUri;
    private final MutableLiveData<Boolean> imageUploaded;

    public MainViewModel() {
        repo = MainRepository.getInstance();
        usersList = new MutableLiveData<>();
        message = new MutableLiveData<>();
        userAdded = new MutableLiveData<>();
        imageUri = new MutableLiveData<>();
        imageUploaded = new MutableLiveData<>();
        fetchUsers();
    }

    private void fetchUsers() {
        repo.fetchUsers(new UsersFetchListener() {

            @Override
            public void onSuccess(List<User> userList) {
                usersList.postValue(userList);
            }

            @Override
            public void onFailed(String e) {
                message.postValue(e);
            }
        });
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public LiveData<Boolean> isUserAdded() {
        return userAdded;
    }

    public void addUser(User u) {

        repo.addUser(u, () -> userAdded.postValue(true));
    }

    public LiveData<List<User>> getUsers() {
        return usersList;
    }

    public LiveData<Uri> getImage() {
        return imageUri;
    }

    public LiveData<Boolean> isImageUploaded() {
        return imageUploaded;
    }

    public void setImage(Uri imageUri) {
        this.imageUri.setValue(imageUri);
    }

    public void uploadImage(String encodedString) {
        String id = "" + System.currentTimeMillis();
        String desc = "This is image " + id;
        repo.uploadImage(id, desc, encodedString, new ImageUploadListener() {
            @Override
            public void onSuccess() {
                imageUploaded.postValue(true);
            }

            @Override
            public void onFailed(String error) {
                message.postValue(error);
            }


        });
    }

    public void resetImageUpload() {
        imageUploaded.postValue(false);
        imageUri.postValue(null);
    }
}
