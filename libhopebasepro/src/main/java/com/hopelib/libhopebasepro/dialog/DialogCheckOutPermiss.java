package com.hopelib.libhopebasepro.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.hopelib.libhopebasepro.R;

public class DialogCheckOutPermiss extends Dialog implements View.OnClickListener {

    private Button btnOk;
    private Button btnCanner;
    private onClickDialogPermissAds mPermissAds;

    public DialogCheckOutPermiss(Context context, onClickDialogPermissAds mPermissAds) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_ads_mode);
        this.mPermissAds = mPermissAds;
        initView();
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    private void initView() {
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnCanner = (Button) findViewById(R.id.btn_canner);

        btnOk.setOnClickListener(this);
        btnCanner.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_ok) {
            mPermissAds.OnClickDialog(true);
        } else if (i == R.id.btn_canner) {
            mPermissAds.OnClickDialog(false);
        }
        dismiss();
    }

    public interface onClickDialogPermissAds {
        void OnClickDialog(boolean isClickPermiss);
    }

}
