package com.example.anku.friends;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class FriendsSearchListLoader extends AsyncTaskLoader<List<Friend>> {

    private static final String TAG = FriendsSearchListLoader.class.getSimpleName();
    private List<Friend> friends;
    private ContentResolver contentResolver;
    private Cursor cursor;
    private String filterText;

    public FriendsSearchListLoader(Context context, ContentResolver contentResolver, String filterText) {
        super(context);
        this.contentResolver = contentResolver;
        this.filterText = filterText;
    }


    @Override
    public List<Friend> loadInBackground() {
        String[] projection = {
                BaseColumns._ID,
                FriendsContract.FriendsColumns.FRIENDS_NAME,
                FriendsContract.FriendsColumns.FRIENDS_EMAIL,
                FriendsContract.FriendsColumns.FRIENDS_PHONE
        };

        List<Friend> entries = new ArrayList<>();
        String selection = FriendsContract.FriendsColumns.FRIENDS_NAME + " LIKE '" + filterText + "%'";
        cursor = contentResolver.query(FriendsContract.URI_TABLE, projection, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int _id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
                    String name = cursor.getString(cursor.getColumnIndex(FriendsContract.FriendsColumns.FRIENDS_NAME));
                    String email = cursor.getString(cursor.getColumnIndex(FriendsContract.FriendsColumns.FRIENDS_EMAIL));
                    String phone = cursor.getString(cursor.getColumnIndex(FriendsContract.FriendsColumns.FRIENDS_PHONE));
                    Friend friend = new Friend(_id, name, phone, email);
                    entries.add(friend);
                } while (cursor.moveToNext());
            }
        }
        return entries;
    }

    @Override
    public void deliverResult(List<Friend> newFriends) {
        if (isReset())
            if (newFriends != null)
                cursor.close();

        List<Friend> oldFriends = friends;
        friends = newFriends;
        if (isStarted())
            super.deliverResult(friends);
        if (oldFriends != null && oldFriends != friends)
            cursor.close();
    }

    @Override
    protected void onStartLoading() {
        if(friends!=null)
            deliverResult(friends);
        if(takeContentChanged()||friends==null)
            forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if(cursor!=null)
            cursor.close();
        friends=null;
    }

    @Override
    public void onCanceled(List<Friend> data) {
        super.onCanceled(data);
        if(cursor!=null)
            cursor.close();
    }
}
