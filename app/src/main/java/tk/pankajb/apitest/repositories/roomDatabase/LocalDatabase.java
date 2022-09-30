package tk.pankajb.apitest.repositories.roomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tk.pankajb.apitest.models.User;

@Database(entities = {User.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static volatile LocalDatabase instance;
    private static final int THREAD_COUNT = 5;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(THREAD_COUNT);

    public static LocalDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LocalDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "localDatabase").build();
                }
            }
        }

        return instance;
    }

}
