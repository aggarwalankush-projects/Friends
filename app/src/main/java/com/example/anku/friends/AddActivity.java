package com.example.anku.friends;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddActivity extends FragmentActivity {

    private final String TAG = AddActivity.class.getSimpleName();
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

        contentResolver = AddActivity.this.getContentResolver();

        saveBtn = (Button) findViewById(R.id.saveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    ContentValues values = new ContentValues();
                    values.put(FriendsContract.FriendsColumns.FRIENDS_NAME, tvName.getText().toString());
                    values.put(FriendsContract.FriendsColumns.FRIENDS_EMAIL, tvEmail.getText().toString());
                    values.put(FriendsContract.FriendsColumns.FRIENDS_PHONE, tvPhone.getText().toString());
                    Uri returned = contentResolver.insert(FriendsContract.URI_TABLE, values);
                    Log.d(TAG, "record id returned is " + returned.toString());
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(v, "Enter valid data", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    private boolean isValid() {
        if (tvName.getText().toString().isEmpty()
                || tvPhone.getText().toString().isEmpty()
                || tvEmail.getText().toString().isEmpty())
            return false;
        else
            return true;
    }


    private boolean someDataEntered() {
        if (!tvName.getText().toString().isEmpty()
                || !tvPhone.getText().toString().isEmpty()
                || !tvEmail.getText().toString().isEmpty())
            return true;
        else
            return false;
    }


    @Override
    public void onBackPressed() {
        if (someDataEntered()) {
            FriendsDialog dialog = new FriendsDialog();
            Bundle args = new Bundle();
            args.putString(FriendsDialog.DIALOG_TYPE, FriendsDialog.CONFIRM_EXIT);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "confirm-exit");
        } else
            super.onBackPressed();
    }
}
