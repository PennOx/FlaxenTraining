package tk.pankajb.apitest.viewModels;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import tk.pankajb.apitest.interfaces.DeleteUserListener;
import tk.pankajb.apitest.interfaces.ImageUploadListener;
import tk.pankajb.apitest.interfaces.UsersFetchListener;
import tk.pankajb.apitest.models.User;
import tk.pankajb.apitest.repositories.MainRepository;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<List<User>> usersListFromApi;
    private final MutableLiveData<List<User>> usersListFromRoom;
    private final MainRepository repo;
    private final MutableLiveData<String> message;
    private final MutableLiveData<Boolean> userAdded;

    private final MutableLiveData<Uri> imageUri;
    private final MutableLiveData<Boolean> imageUploaded;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repo = MainRepository.getInstance(application.getApplicationContext());
        usersListFromApi = new MutableLiveData<>();
        usersListFromRoom = new MutableLiveData<>();
        message = new MutableLiveData<>();
        userAdded = new MutableLiveData<>();
        imageUri = new MutableLiveData<>();
        imageUploaded = new MutableLiveData<>();
        fetchUsersFromApi();
        fetchUsersFromRoom();
    }

    private void fetchUsersFromRoom() {
        repo.fetchUsersFromRoom(new UsersFetchListener(){
            @Override
            public void onSuccess(List<User> userList) {
                usersListFromRoom.postValue(userList);
            }

            @Override
            public void onFailed(String e) {
                message.postValue(e);
            }
        });
    }

    private void fetchUsersFromApi() {
        repo.fetchUsersFromApi(new UsersFetchListener() {

            @Override
            public void onSuccess(List<User> userList) {
                usersListFromApi.postValue(userList);
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

    public void addUserToApi(User u) {

        repo.addUserToApi(u, () -> userAdded.postValue(true));
    }

    public LiveData<List<User>> getUsersFromApi() {
        return usersListFromApi;
    }

    public LiveData<List<User>> getUsersFromRoom(){
        return usersListFromRoom;
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

    public void addUserToRoom(User user) {
        repo.addUserToRoom(user, () -> userAdded.postValue(true));
    }

    public void deleteRoomUser(int i) {
        repo.deleteRoomUser(usersListFromRoom.getValue().get(i),new DeleteUserListener(){
            @Override
            public void onDeleted() {
                fetchUsersFromRoom();
            }
        });
    }
}
