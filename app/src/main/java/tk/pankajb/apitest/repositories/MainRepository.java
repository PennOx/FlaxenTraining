package tk.pankajb.apitest.repositories;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import tk.pankajb.apitest.api.APIClient;
import tk.pankajb.apitest.api.APIInterface;
import tk.pankajb.apitest.interfaces.AddUserListener;
import tk.pankajb.apitest.interfaces.DeleteUserListener;
import tk.pankajb.apitest.interfaces.ImageUploadListener;
import tk.pankajb.apitest.interfaces.UsersFetchListener;
import tk.pankajb.apitest.models.ImageUploadResponse;
import tk.pankajb.apitest.models.LocationCoordinates;
import tk.pankajb.apitest.models.User;
import tk.pankajb.apitest.models.UserResponse;
import tk.pankajb.apitest.repositories.roomDatabase.LocalDatabase;

public class MainRepository {

    private static MainRepository instance;
    private final APIInterface apiInterface;
    private final LocalDatabase localDatabase;

    private final MutableLiveData<LocationCoordinates> coordinatesLiveData;

    private MainRepository(Context context) {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        localDatabase = LocalDatabase.getInstance(context);

        coordinatesLiveData = new MutableLiveData<>();
    }

    public static MainRepository getInstance(Context context) {
        if (instance== null){
            instance = new MainRepository(context);
        }
        return instance;
    }

    public void fetchUsersFromApi(UsersFetchListener listener) {

        Call<UserResponse> call = apiInterface.getUsers();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                listener.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                listener.onFailed(t.getMessage());
            }
        });
    }

    public void addUserToApi(User u, AddUserListener addUserListener) {

        Call<UserResponse> call = apiInterface.createUser(u.getName(), u.getAge(), u.getGender(), u.getMobile());
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                addUserListener.onSuccess();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }

    public void uploadImage(String id, String desc, String encodedString, ImageUploadListener listener) {
        Call<ImageUploadResponse> call = apiInterface.uploadImage(id, desc, encodedString);

        call.enqueue(new Callback<ImageUploadResponse>() {

            @Override
            public void onResponse(Call<ImageUploadResponse> call, retrofit2.Response<ImageUploadResponse> response) {
                Log.i("ImageUploadLog", response.body().getMessage());
                if (response.body().getStatusCode() == 200) {
                    listener.onSuccess();
                } else {
                    listener.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                listener.onFailed(t.getMessage());
            }
        });
    }

    public void setCoordinates(LocationCoordinates coordinates) {
        coordinatesLiveData.postValue(coordinates);
    }

    public LiveData<LocationCoordinates> getCoordinates() {
        return coordinatesLiveData;
    }

    public void addUserToRoom(User user, AddUserListener addUserListener) {

        LocalDatabase.databaseWriteExecutor.execute(() -> {
            localDatabase.userDao().insertUser(user);
            addUserListener.onSuccess();
        });
    }

    public void fetchUsersFromRoom(UsersFetchListener listener) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            List<User> list = localDatabase.userDao().getAllUsers();
            listener.onSuccess(list);
        });
    }

    public void deleteRoomUser(User user, DeleteUserListener deleteUserListener) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            localDatabase.userDao().deleteUser(user);
            deleteUserListener.onDeleted();
        });
    }
}
