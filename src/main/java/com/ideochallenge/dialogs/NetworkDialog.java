package com.ideochallenge.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by Maciej Szalek on 2019-10-21.
 */

public class NetworkDialog {

    public static void showNetworkDialog(final Context context){
        final AlertDialog.Builder networkDialog = new AlertDialog.Builder(context);
        networkDialog.setTitle("Disconnected !!!");
        networkDialog.setMessage("To run this application \ninternet connection is required.");
        networkDialog.setCancelable(false);
        networkDialog.setNegativeButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((Activity) context).finish();
            }
        });
        networkDialog.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent goToSettings = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                context.startActivity(goToSettings);
                ((Activity) context).finish();
            }
        });

        AlertDialog dialog = networkDialog.create();
        dialog.show();
    }
}
