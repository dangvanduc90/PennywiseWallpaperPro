package com.libdialog.dialograte.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.hsalf.smilerating.SmileRating;
import com.libdialog.dialograte.R;
import com.libdialog.dialograte.methor.Util;

public class DialogRateSmiley extends Dialog {

    private Activity mActivity;
    private Button btnLate;
    private Button btnShare;
    private SmileRating smileRating;
    private Util mUtil;
    private setOnClickDialog clickDialog;

    public DialogRateSmiley(Context context, Activity mActivity, Util mUtil, setOnClickDialog clickDialog) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_3;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_smiley);
        this.mActivity = mActivity;
        this.clickDialog = clickDialog;
        this.mUtil = mUtil;
        initDialog(context);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    private void initDialog(Context mContext) {
        btnLate = (Button) findViewById(R.id.btn_late);
        btnShare = (Button) findViewById(R.id.btn_share);
        smileRating = (SmileRating) findViewById(R.id.smile_rating);
        mUtil = new Util(mContext);

        btnLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDialog.ClickDialog();
                dismiss();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDialog.ShareApps();
                dismiss();
            }
        });

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                if (reselected) {
                    if (smiley >= 2) {
                        mUtil.launchMarket(mActivity);
                        mUtil.setShowDialog(true);
                        dismiss();
                        clickDialog.ClickDialog();
                    } else {
                        dismiss();
                    }
                }
            }
        });
    }

    public interface setOnClickDialog {
        void ClickDialog();

        void ShareApps();
    }

}
