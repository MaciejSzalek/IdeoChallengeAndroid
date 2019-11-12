package com.ideochallenge.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.ideochallenge.R;

/**
 * Created by Maciej Szalek on 2019-10-31.
 */

public class TutorialDialog {

    private static boolean visible = true;

    public static void showTutorialDialog(Context context){
        if(visible){
            final AlertDialog.Builder tutorialDialogBuilder = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.tutorial_dialog, null);
            tutorialDialogBuilder.setView(view);
            tutorialDialogBuilder.setCancelable(true);
            final AlertDialog dialog = tutorialDialogBuilder.create();
            dialog.show();

            Button okBtn = view.findViewById(R.id.tutorial_ok_btn);

            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setVisible(false);
                    dialog.dismiss();
                }
            });
        }
    }
    private static void setVisible(boolean visible) {
        TutorialDialog.visible = visible;
    }
}
