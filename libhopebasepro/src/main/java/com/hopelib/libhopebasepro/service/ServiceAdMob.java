package com.hopelib.libhopebasepro.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.hopelib.libhopebasepro.RunAppQA;
import com.hopelib.libhopebasepro.broadcast.ConnectivityReceiver;
import com.hopelib.libhopebasepro.broadcast.ScreenReceiver;
import com.hopelib.libhopebasepro.utilmethor.CommonVL;
import com.hopelib.libhopebasepro.utilmethor.DataCM;
import com.hopelib.libhopebasepro.utilmethor.SharedPreferMngService;
import com.hopelib.libhopebasepro.utilmethor.Util;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener;

import java.util.Random;

public class ServiceAdMob extends Service implements ConnectivityReceiver.ConnectivityReceiverListener {

    private BroadcastReceiver mReceiver;
    private SharedPreferMngService spMngService;
    private String[] arrPacketName = {"com.instagram.android", "com.facebook.orca", "com.google.android.youtube",
            "com.google.android.gm", "com.android.chrome", "com.google.android.apps.maps", "com.google.android.keep",
            "com.facebook.katana", "com.android.vending", "com.skype.raider", "com.samsung.android.incallui",
            "com.google.android.apps.youtube.kids", "com.facebook.mlite", "com.facebook.lite", "com.facebook.Mentions"};
    private InterstitialAd interstitialAd;
    private AdRequest adRequest;
    private StartAppAd startAppAd;
    private String permiss;
    private Handler mHandler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
        spMngService = new SharedPreferMngService(this);
        ramdomAdsShow(true, DataCM.RAMDOM_ADS, DataCM.MIN_RD);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            int numberQA = intent.getIntExtra(DataCM.INT_QA, -1);
            if (checkConnection()) {
                permiss = null;
                try {
                    permiss = Util.retriveNewApp(this);
                } catch (Exception e) {
                    permiss = null;
                }
                switch (numberQA) {
                    case 0:
                        showAdsAdmonbFull(true);
                        break;
                    case 1:
                        if (!TextUtils.isEmpty(permiss)) {
                            boolean checkShowAds = checkOutAppsMode(permiss);
                            if (checkShowAds) {
                                ramdomAdsShow(false, DataCM.RAMDOM_ADS, DataCM.MIN_RD);
                            }
                        } else {
                            ramdomAdsShow(false, DataCM.RAMDOM_ADS_MAX, DataCM.MIN_RD);
                        }
                        break;
                    case 2:
                        adsStartApps();
                        break;
                    case 3:
                        showAdsAdmonbFull(false);
                        break;
                    default:
                        break;
                }
            }
        }
        setConnectivityListener(this);
        return START_STICKY;
    }

    private boolean checkConnection() {
        return ConnectivityReceiver.isConnected(this);
    }

    //random Ads
    private void ramdomAdsShow(boolean checkRd, int maxRd, int minRd) {
        if (!checkRd) {
            int ramdomNumber = spMngService.getRadomAds(ServiceAdMob.this);
            if (ramdomNumber == 0) {
                ramdomNumber = -1;
                spMngService.setRadomAds(ServiceAdMob.this, ramdomNumber);
                showAdsAdmonbFull(false);
                spMngService.setRadomAds(ServiceAdMob.this, getRandom(maxRd, minRd));
            } else if (ramdomNumber < 0) {
                spMngService.setRadomAds(ServiceAdMob.this, getRandom(maxRd, minRd));
            } else if (ramdomNumber > 0) {
                ramdomNumber = ramdomNumber - 1;
                spMngService.setRadomAds(ServiceAdMob.this, ramdomNumber);
            }
        } else {
            int adsMode = getRandom(maxRd, minRd);
            spMngService.setRadomAds(ServiceAdMob.this, adsMode);
        }
    }

    private int getRandom(int maxRd, int minRd) {
        Random rdAds = new Random();
        int adsMode = rdAds.nextInt(maxRd) + minRd;
        return adsMode;
    }

    private void showAdsAdmonbFull(final boolean checkStopService) {
        if (interstitialAd == null) {
            removeAdsStartApps();
            MobileAds.initialize(this, CommonVL.id_apps_ads);
            interstitialAd = new InterstitialAd(this);
            interstitialAd.setAdUnitId(CommonVL.ad_unit_full);
            adRequest = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest);
            interstitialAd.setAdListener(new AdListener() {

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                }

                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    interstitialAd = null;
                    if (checkStopService) {
                        RunAppQA.stopQAData(ServiceAdMob.this);
                    }
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Log.i("toannt", "onAdFailedToLoad " + i);
                    try {
                        interstitialAd = null;
                        if (Util.networkconection(ServiceAdMob.this)) {
                            adsStartApps();
                        } else {
                            if (interstitialAd != null) {
                                interstitialAd.getAdListener().onAdClosed();
                                interstitialAd = null;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    try {
                        removeAdsStartApps();
                        permiss = Util.retriveNewApp(ServiceAdMob.this);
                        boolean checkShowAds = checkOutAppsMode(permiss);
                        if (checkShowAds && checkConnection()) {
                            interstitialAd.show();
                        } else {
                            if (mHandler == null) {
                                mHandler = new Handler();
                            }
                            mHandler.postDelayed(mRunnable, DataCM.TIME_DELAYED_ADS);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (Util.networkconection(ServiceAdMob.this)) {
                            adsStartApps();
                        }
                        interstitialAd = null;
                    }
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                    try {
                        if (interstitialAd != null) {
                            interstitialAd.getAdListener().onAdClosed();
                            interstitialAd = null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        interstitialAd = null;
                    }
                }
            });
        }
    }

    //show Ads StartApps
    private void adsStartApps() {
        if (startAppAd == null) {
            startAppAd = new StartAppAd(this);
            StartAppSDK.init(this, CommonVL.STARTAPP_ID, true);
            startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC);
        }
        startAppAd.showAd(new AdDisplayListener() {
            @Override
            public void adHidden(Ad ad) {
                removeAdsStartApps();
            }

            @Override
            public void adDisplayed(Ad ad) {

            }

            @Override
            public void adClicked(Ad ad) {
                removeAdsStartApps();
            }

            @Override
            public void adNotDisplayed(Ad ad) {
                removeAdsStartApps();
            }
        });
    }

    //remove Ads StartApps
    private void removeAdsStartApps() {
        try {
            if (startAppAd != null) {
                startAppAd.close();
            }
            startAppAd = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        String permiss = null;
        try {
            permiss = Util.retriveNewApp(this);
        } catch (Exception e) {
            permiss = null;
        }
        if (isConnected) {
            if (!TextUtils.isEmpty(permiss)) {
                boolean checkShowAds = checkOutAppsMode(permiss);
                if (checkShowAds) {
                    ramdomAdsShow(false, DataCM.RAMDOM_ADS, DataCM.MIN_RD);
                }
            } else {
                ramdomAdsShow(false, DataCM.RAMDOM_ADS_MAX, DataCM.MIN_RD);
            }
        }
    }

    private void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    //delay ads mode
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            permiss = Util.retriveNewApp(ServiceAdMob.this);
            boolean checkShowAds = checkOutAppsMode(permiss);
            if (interstitialAd != null) {
                if (checkShowAds && checkConnection()) {
                    interstitialAd.show();
                } else {
                    mHandler.postDelayed(mRunnable, DataCM.TIME_DELAYED_ADS);
                }
            } else {
                mHandler.removeCallbacks(mRunnable);
            }
        }
    };

    //check apps
    private boolean checkOutAppsMode(String appsPesmiss) {
        for (String itemApps : arrPacketName) {
            if (appsPesmiss.contains(itemApps)) {
                return false;
            }
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }

        try {
            if (interstitialAd != null) {
                interstitialAd.getAdListener().onAdClosed();
                interstitialAd = null;
            }
            mHandler.removeCallbacks(mRunnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        removeAdsStartApps();

    }
}
