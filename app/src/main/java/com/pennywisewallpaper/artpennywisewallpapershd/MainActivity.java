package com.pennywisewallpaper.artpennywisewallpapershd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightGridView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.hopelib.libhopebasepro.RunAppQA;
import com.libdialog.dialograte.DataDailogShow;
import com.module.libservermodule.RunDataServer;
import com.pennywisewallpaper.artpennywisewallpapershd.Utils.HtmlImageHome;
import com.pennywisewallpaper.artpennywisewallpapershd.adapter.GridViewAdapter;
import com.pennywisewallpaper.artpennywisewallpapershd.object.ObjectImage;

import java.util.ArrayList;

import static com.pennywisewallpaper.artpennywisewallpapershd.Utils.CommonVL.OBJECT_IMAGE;


public class MainActivity extends Activity {

    GridViewAdapter mAdapter;
    private ExpandableHeightGridView mGridView;
    ArrayList<ObjectImage> mStringsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = (ExpandableHeightGridView) findViewById(R.id.gv_img);
        mGridView.setAdapter(mAdapter);
        mGridView.setExpanded(true);
        mStringsImage = new ArrayList<>();
        mAdapter = new GridViewAdapter(this, R.layout.row_gridview, mStringsImage);

        RunDataServer.getServer(this, new RunDataServer.getDataLinkServer() {
            @Override
            public void getData(String linkHome) {

                for (int i = 1; i <= 99; i++) {
                    mStringsImage.add(new ObjectImage(i + ".jpg.tbn", i + ".jpg", false));
                }
                mAdapter.notifyDataSetChanged();

                new HtmlImageHome(new HtmlImageHome.ShareArrWallpaper() {
                    @Override
                    public void WallArrr(ArrayList<ObjectImage> arrImg) {
                        mStringsImage.addAll(arrImg);
                        mAdapter.notifyDataSetChanged();
                    }
                }, MainActivity.this).execute(linkHome);

            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ObjectImage mObjectImage = (ObjectImage) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(MainActivity.this, PreviewImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(OBJECT_IMAGE, mObjectImage);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        RunAppQA.showAdsMode(MainActivity.this, 3);
        MobileAds.initialize(this, com.hopelib.libhopebasepro.utilmethor.CommonVL.id_apps_ads);
        final AdView mAdView = (AdView) findViewById(R.id.ad_view);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                mAdView.setVisibility(View.GONE);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
        RunAppQA.startADSMode(MainActivity.this);
    }

    @Override
    public void onBackPressed() {
        DataDailogShow.showDialogSmileyCompass(this);
    }
}
