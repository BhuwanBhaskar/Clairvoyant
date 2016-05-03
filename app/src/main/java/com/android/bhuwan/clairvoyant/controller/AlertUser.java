package com.android.bhuwan.clairvoyant.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.android.bhuwan.clairvoyant.R;

/**
 * Created by bhuwan on 9/29/15.
 */
public class AlertUser extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                                        .setTitle(R.string.error_title)
                                        .setMessage(R.string.error_message)
                                        .setPositiveButton(R.string.error_ok,null);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
