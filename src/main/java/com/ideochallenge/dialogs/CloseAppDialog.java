package com.ideochallenge.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Maciej Szalek on 2019-10-31.
 */

public class CloseAppDialog {

    public static void showCloseAppDialog(final Context context){
        AlertDialog.Builder dialogClose = new AlertDialog.Builder(context);
        dialogClose.setTitle("Exit Ideo Challenge");
        dialogClose.setMessage("Do you really wont to exit application ?");
        dialogClose.setCancelable(true);
        dialogClose.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogClose.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).finish();
            }
        });
        AlertDialog dialog = dialogClose.create();
        dialog.show();
    }
}
