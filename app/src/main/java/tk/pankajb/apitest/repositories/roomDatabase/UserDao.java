package tk.pankajb.apitest.repositories.roomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tk.pankajb.apitest.models.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

   @Insert
    void insertUser(User user);

   @Update
    void updateUser(User user);

   @Delete
    void deleteUser(User user);

   @Query("SELECT * FROM user WHERE id=:id")
    User getUserById(String id);

}
