package com.libdialog.dialograte.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.libdialog.dialograte.R;
import com.libdialog.dialograte.methor.Util;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

public class DialogSimpleRating extends Dialog {

    private Activity mActivity;
    private Button btnRate;
    private Button btnLate;
    private Button btnShare;
    private Util mUtil;
    private setOnClickDialog clickDialog;
    private ScaleRatingBar mRatingBar;
    private float rate;

    public DialogSimpleRating(Context context, Activity mActivity, Util mUtil, setOnClickDialog clickDialog) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_3;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_simple_rating);
        this.mActivity = mActivity;
        this.clickDialog = clickDialog;
        this.mUtil = mUtil;
        initDialog(context);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    private void initDialog(Context mContext) {
        btnRate = (Button) findViewById(R.id.btn_rate);
        btnLate = (Button) findViewById(R.id.btn_late);
        btnShare = (Button) findViewById(R.id.btn_share);
        mRatingBar = (ScaleRatingBar) findViewById(R.id.ratingBar);
        mUtil = new Util(mContext);

        mRatingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {
                rate = v;
            }
        });

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rate >= 3) {
                    mUtil.launchMarket(mActivity);
                    mUtil.setShowDialog(true);
                    dismiss();
                    clickDialog.ClickDialog();
                } else {
                    dismiss();
                }

            }
        });
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
    }

    public interface setOnClickDialog {
        void ClickDialog();

        void ShareApps();
    }

}
