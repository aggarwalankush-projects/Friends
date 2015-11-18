package com.example.anku.friends;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

public class FriendsListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Friend>> {

    private FriendsCustomAdapter adapter;
    private static final int LOADER_ID = 1;
    private ContentResolver contentResolver;
    private List<Friend> friends;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        contentResolver=getActivity().getContentResolver();
        adapter = new FriendsCustomAdapter(getActivity(), getChildFragmentManager());
        setEmptyText("No Friends");
        setListAdapter(adapter);
        setListShown(false);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Friend>> onCreateLoader(int id, Bundle args) {
        contentResolver=getActivity().getContentResolver();
        return new FriendsListLoader(getActivity(), contentResolver);
    }

    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> friends) {
        adapter.setData(friends);
        this.friends=friends;
        if(isResumed())
            setListShown(true);
        else
            setListShownNoAnimation(true);
    }

    @Override
    public void onLoaderReset(Loader<List<Friend>> loader) {
        adapter.setData(null);
    }
}
