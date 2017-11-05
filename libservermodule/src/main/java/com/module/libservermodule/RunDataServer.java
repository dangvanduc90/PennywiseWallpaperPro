package com.module.libservermodule;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.module.libservermodule.methor.CommonData;
import com.module.libservermodule.methor.SharedPreMng;
import com.module.libservermodule.methor.UtilData;
import com.module.libservermodule.server.ItemData;
import com.module.libservermodule.server.MainConfig;

public class RunDataServer {

    public static void getServer(final Context mContext, final getDataLinkServer linkServer) {
        final SharedPreMng sharedPreMng = new SharedPreMng(mContext);
        final String linkHome = sharedPreMng.getLinkDataA(mContext, CommonData.LINK_HOME);
        if (TextUtils.isEmpty(linkHome)) {
            if (UtilData.networkconection(mContext)) {
                MainConfig mainConfig = new MainConfig(mContext);
                mainConfig.getLinkConFig(new MainConfig.getDataValue() {
                    @Override
                    public void valueData(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            ItemData data = dataSnapshot.getValue(ItemData.class);
                            String linkHome = data.linkHome;
                            if (linkHome.equals("null")) {
                                Toast.makeText(mContext, mContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
                            } else {
                                linkServer.getData(linkHome);
                                sharedPreMng.setLinkDataA(mContext, CommonData.LINK_HOME, linkHome);
                            }
                        } else {
                            Toast.makeText(mContext, mContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                Toast.makeText(mContext, mContext.getString(R.string.no_network), Toast.LENGTH_LONG).show();
            }
        } else {
            linkServer.getData(linkHome);
        }
    }

    public interface getDataLinkServer {
        void getData(String linkHome);
    }

}
