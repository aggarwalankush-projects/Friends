package com.example.anku.friends.Exceptions;

import android.net.Uri;

public class UnknownUri extends IllegalArgumentException {
    public UnknownUri(Uri uri) {
        super("Unknown Uri: " + uri);
    }
}
