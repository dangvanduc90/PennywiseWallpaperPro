package com.module.libservermodule.server;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.module.libservermodule.methor.CommonData;

public class MainConfig {

    private DatabaseReference mReference;
    private Context mContext;

    public MainConfig(Context mContext) {
        this.mContext = mContext;
        mReference = FirebaseDatabase.getInstance().getReference();
    }

    public void getLinkConFig(final getDataValue value) {
        if (mReference == null) {
            mReference = FirebaseDatabase.getInstance().getReference();
        }
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mReference.child(CommonData.NAME_DATA_TABLE).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            try {
                                value.valueData(dataSnapshot);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        removeFirebase();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if (databaseError != null) {
                            removeFirebase();
                            try {
                                showToast("Does not receive connection. Please recheck your network connection!", Toast.LENGTH_LONG);
                            } catch (Exception e) {
                                value.valueData(null);
                            }
                        }
                    }
                });
            }
        });
    }

    public void removeFirebase() {
        if (mReference != null) {
            mReference.removeValue();
            mReference = null;
        }
    }

    private void showToast(String titleToast, int timeToast) {
        Toast.makeText(mContext, timeToast, timeToast).show();
    }

    public interface getDataValue {
        void valueData(DataSnapshot dataSnapshot);
    }

}
