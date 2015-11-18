package com.example.anku.friends;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditActivity extends FragmentActivity {

    private final String TAG = EditActivity.class.getSimpleName();
    private TextView tvName, tvPhone, tvEmail;
    private Button saveBtn;
    private ContentResolver contentResolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        tvName = (TextView) findViewById(R.id.friendName);
        tvPhone = (TextView) findViewById(R.id.friendPhone);
        tvEmail = (TextView) findViewById(R.id.friendEmail);

        contentResolver = EditActivity.this.getContentResolver();

        Intent intent = getIntent();
        final String _id = intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_ID);
        String name = intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_NAME);
        String phone = intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_PHONE);
        String email = intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_EMAIL);

        tvName.setText(name);
        tvPhone.setText(phone);
        tvEmail.setText(email);

        saveBtn = (Button) findViewById(R.id.saveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(FriendsContract.FriendsColumns.FRIENDS_NAME, tvName.getText().toString());
                values.put(FriendsContract.FriendsColumns.FRIENDS_EMAIL, tvEmail.getText().toString());
                values.put(FriendsContract.FriendsColumns.FRIENDS_PHONE, tvPhone.getText().toString());
                Uri uri = FriendsContract.Friends.buildFriendUri(_id);
                int recordsUpdated = contentResolver.update(uri, values, null, null);
                Log.d(TAG, "number of records updated is " + recordsUpdated);
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return true;
    }
}
