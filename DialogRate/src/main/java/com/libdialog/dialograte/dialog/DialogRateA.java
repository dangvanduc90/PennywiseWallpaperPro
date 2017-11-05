package com.libdialog.dialograte.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.libdialog.dialograte.R;
import com.libdialog.dialograte.methor.Util;

public class DialogRateA extends Dialog implements View.OnClickListener {

    private Button btnrate, btnlate;
    private Activity mActivity;
    private Util mUtil;
    private setOnClickDialog clickDialog;

    public DialogRateA(Context context, Activity mActivity, Util mUtil, setOnClickDialog clickDialog) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_3;
        setContentView(R.layout.dialog_rate);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        initViews();
        this.mActivity = mActivity;
        this.clickDialog = clickDialog;
        this.mUtil = mUtil;
    }

    private void initViews() {
        btnrate = (Button) findViewById(R.id.btn_rate);
        btnlate = (Button) findViewById(R.id.btn_late);
        btnrate.setOnClickListener(this);
        btnlate.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_rate) {
            mUtil.launchMarket(mActivity);
            mUtil.setShowDialog(true);
            dismiss();
            clickDialog.ClickDialog();
        } else if (i == R.id.btn_late) {
            clickDialog.ClickDialog();
            dismiss();
        }
    }

    public interface setOnClickDialog {
        void ClickDialog();
    }

}
