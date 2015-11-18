package com.example.anku.friends;


import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;


public class SearchActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<Friend>> {

    private FriendsCustomAdapter adapter;
    private static int LOADER_ID = 2;
    private ContentResolver contentResolver;
    private List<Friend> friends;

    private ListView listView;
    private EditText searchEditText;
    private Button searchFriendButton;
    private String matchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        listView = (ListView) findViewById(R.id.searchResultsList);
        searchEditText = (EditText) findViewById(R.id.searchName);
        searchFriendButton = (Button) findViewById(R.id.searchButton);
        contentResolver=getContentResolver();
        adapter = new FriendsCustomAdapter(SearchActivity.this, getSupportFragmentManager());
        listView.setAdapter(adapter);

        searchFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchText=searchEditText.getText().toString();
                getSupportLoaderManager().initLoader(LOADER_ID++, null, SearchActivity.this);
            }
        });

    }

    @Override
    public Loader<List<Friend>> onCreateLoader(int id, Bundle args) {
        return new FriendsSearchListLoader(SearchActivity.this, this.contentResolver, matchText);

    }

    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> data) {
        adapter.setData(data);
        friends=data;
    }

    @Override
    public void onLoaderReset(Loader<List<Friend>> loader) {
        adapter.setData(null);
    }
}
