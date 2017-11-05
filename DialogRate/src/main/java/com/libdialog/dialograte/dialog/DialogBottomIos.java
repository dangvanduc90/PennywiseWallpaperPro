package com.libdialog.dialograte.dialog;

import android.app.Activity;
import android.support.v4.app.FragmentManager;

import com.kcode.bottomlib.BottomDialog;
import com.libdialog.dialograte.R;
import com.libdialog.dialograte.methor.Util;

/**
 * Created by toannt on 18/10/2017.
 */

public class DialogBottomIos {

    private setOnClickDialog clickDialog;
    private Activity mContext;
    private Util mUtil;

    public DialogBottomIos(Activity mContext, setOnClickDialog clickDialog) {
        this.mContext = mContext;
        this.clickDialog = clickDialog;
        mUtil = new Util(mContext);
    }

    public void createDialogBottom(FragmentManager manager) {
        BottomDialog dialog = BottomDialog.newInstance(mContext.getString(R.string.rate),
                new String[]{mContext.getString(R.string.dialog_rate),
                        mContext.getString(R.string.dialog_share),
                        mContext.getString(R.string.dialog_late)});
        dialog.show(manager, "dialog");
        dialog.setListener(new BottomDialog.OnClickListener() {
            @Override
            public void click(int position) {

                switch (position) {
                    case 0:
                        mUtil.launchMarket(mContext);
                        mUtil.setShowDialog(true);
                        clickDialog.ClickDialog();
                        break;
                    case 1:
                        clickDialog.ShareApps();
                        break;
                    case 2:
                        clickDialog.ClickDialog();
                        break;
                }

            }
        });
    }

    public interface setOnClickDialog {
        void ClickDialog();

        void ShareApps();
    }

}
