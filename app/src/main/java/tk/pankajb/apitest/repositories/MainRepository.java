package tk.pankajb.apitest.repositories;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import tk.pankajb.apitest.api.APIClient;
import tk.pankajb.apitest.api.APIInterface;
import tk.pankajb.apitest.interfaces.AddUserListener;
import tk.pankajb.apitest.interfaces.ImageUploadListener;
import tk.pankajb.apitest.interfaces.UsersFetchListener;
import tk.pankajb.apitest.models.ImageUploadResponse;
import tk.pankajb.apitest.models.LocationCoordinates;
import tk.pankajb.apitest.models.User;
import tk.pankajb.apitest.models.UserResponse;

public class MainRepository {

    private static final MainRepository instance = new MainRepository();
    private final APIInterface apiInterface;

    private final MutableLiveData<LocationCoordinates> coordinatesLiveData;

    private MainRepository() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        coordinatesLiveData = new MutableLiveData<>();
    }

    public static MainRepository getInstance() {
        return instance;
    }

    public void fetchUsers(UsersFetchListener listener) {

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

    public void addUser(User u, AddUserListener addUserListener) {

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
}
