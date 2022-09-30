package tk.pankajb.apitest.firebaseTest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import tk.pankajb.apitest.R;

public class FirebaseCRUDMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_crudmain);

        getSupportActionBar().setTitle("Firebase CRUD Home");
    }
}