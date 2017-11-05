package com.libdialog.dialograte;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.libdialog.dialograte.dialog.DialogBottomIos;
import com.libdialog.dialograte.dialog.DialogRate;
import com.libdialog.dialograte.dialog.DialogRateA;
import com.libdialog.dialograte.dialog.DialogRateSmiley;
import com.libdialog.dialograte.dialog.DialogSimpleRating;
import com.libdialog.dialograte.dialog.DialogSmileyCompast;
import com.libdialog.dialograte.methor.Util;


public class DataDailogShow {

    public static void initRundialog(final Activity mActivity) {
        Util mUtil = new Util(mActivity);
        if (!mUtil.getShowDialog()) {
            new DialogRate(mActivity, mActivity, mUtil, new DialogRate.setOnClickDialog() {
                @Override
                public void ClickDialog() {
                    mActivity.finish();
                }

                @Override
                public void ShareApps() {
                    shareapps(mActivity);
                }
            }).show();
        } else {
            mActivity.finish();
        }
    }

    public static void initRundialogA(final Activity mActivity) {
        Util mUtil = new Util(mActivity);
        if (!mUtil.getShowDialog()) {
            new DialogRateA(mActivity, mActivity, mUtil, new DialogRateA.setOnClickDialog() {
                @Override
                public void ClickDialog() {
                    mActivity.finish();
                }
            }).show();
        } else {
            mActivity.finish();
        }
    }

    public static void initRundialogDataA(final Activity mActivity) {
        Util mUtil = new Util(mActivity);
        new DialogRateA(mActivity, mActivity, mUtil, new DialogRateA.setOnClickDialog() {
            @Override
            public void ClickDialog() {
                mActivity.finish();
            }
        }).show();
    }

    public static void shareapps(Activity mActivity) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Thank you for supporting developers!");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Util.APPS_DATA + mActivity.getPackageName());
        mActivity.startActivity(Intent.createChooser(sharingIntent, ""));
    }

    //show dialog

    public static void showDialogBottomIos(FragmentManager manager, final Activity mContext) {
        DialogBottomIos bottomIos = new DialogBottomIos(mContext, new DialogBottomIos.setOnClickDialog() {
            @Override
            public void ClickDialog() {
                mContext.finish();
            }

            @Override
            public void ShareApps() {
                shareapps(mContext);
            }
        });
        bottomIos.createDialogBottom(manager);
    }

    public static void initRundialogData(final Activity mActivity) {
        Util mUtil = new Util(mActivity);
        new DialogRate(mActivity, mActivity, mUtil, new DialogRate.setOnClickDialog() {
            @Override
            public void ClickDialog() {
                mActivity.finish();
            }

            @Override
            public void ShareApps() {
                shareapps(mActivity);
            }
        }).show();
    }

    public static void showDialogRateSmiley(final Activity mActivity) {
        Util mUtil = new Util(mActivity);
        new DialogRateSmiley(mActivity, mActivity, mUtil, new DialogRateSmiley.setOnClickDialog() {
            @Override
            public void ClickDialog() {
                mActivity.finish();
            }

            @Override
            public void ShareApps() {
                shareapps(mActivity);
            }
        }).show();
    }

    public static void showDialogSimpleRating(final Activity mActivity) {
        Util mUtil = new Util(mActivity);
        new DialogSimpleRating(mActivity, mActivity, mUtil, new DialogSimpleRating.setOnClickDialog() {
            @Override
            public void ClickDialog() {
                mActivity.finish();
            }

            @Override
            public void ShareApps() {
                shareapps(mActivity);
            }
        }).show();
    }

    public static void showDialogSmileyCompass(final Activity mActivity){
        Util mUtil = new Util(mActivity);
        new DialogSmileyCompast(mActivity, mActivity, mUtil, new DialogSmileyCompast.setOnClickDialog() {
            @Override
            public void ClickDialog() {
                mActivity.finish();
            }

            @Override
            public void ShareApps() {
                shareapps(mActivity);
            }
        }).show();

    }

}
