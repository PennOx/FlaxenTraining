package tk.pankajb.apitest.interfaces;

import java.util.List;

import tk.pankajb.apitest.models.User;

public interface UsersFetchListener {

    public void onSuccess(List<User> userList);

    public void onFailed(String e);
}
