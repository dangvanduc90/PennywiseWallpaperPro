package com.pennywisewallpaper.artpennywisewallpapershd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.pennywisewallpaper.artpennywisewallpapershd.Utils.ParseImageHome;
import com.pennywisewallpaper.artpennywisewallpapershd.broadcast.ConnectivityReceiver;
import com.pennywisewallpaper.artpennywisewallpapershd.broadcast.MyApplication;
import com.pennywisewallpaper.artpennywisewallpapershd.object.ObjectImage;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static com.pennywisewallpaper.artpennywisewallpapershd.Utils.CommonVL.ASSEST_URI;
import static com.pennywisewallpaper.artpennywisewallpapershd.Utils.CommonVL.FILE_FOLDE;
import static com.pennywisewallpaper.artpennywisewallpapershd.Utils.CommonVL.FOLDE_NAME;
import static com.pennywisewallpaper.artpennywisewallpapershd.Utils.CommonVL.JPG;
import static com.pennywisewallpaper.artpennywisewallpapershd.Utils.CommonVL.OBJECT_IMAGE;
import static com.pennywisewallpaper.artpennywisewallpapershd.Utils.CommonVL.WALL_PAPER_URL;

public class PreviewImageActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ImageView ivPreviewImage;
    private String path;
    private String linkOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);
        ObjectImage objectImage = (ObjectImage) getIntent().getExtras().getSerializable(OBJECT_IMAGE);
        final String imageSource = objectImage.getImgSourc();

        initView();
        
        if (objectImage.isInternet()) {
            new ParseImageHome(this, new ParseImageHome.LoadImage() {
                @Override
                public void loadDataImg(String httpImg) {
                    linkOnline = httpImg;
                    Glide.with(PreviewImageActivity.this).load(httpImg).centerCrop().into(ivPreviewImage);
                }
            }).execute(WALL_PAPER_URL + objectImage.getImgSourc());
        } else {
            path = imageSource;
            Glide.with(this).load(ASSEST_URI + imageSource).centerCrop().into(ivPreviewImage);
        }

        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
        final FloatingActionsMenu multipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);

        actionA.setTitle("Set As");
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(path)) {
                    setWallpaperImg(path, false);
                } else {
                    if (checkConnection()) {
                        isDownloadFile();
                    }
                }
            }
        });
    }

    private void setWallpaperImg(String pathImg, boolean isOnline) {
        try {
            Bitmap bitmap = null;
            if (isOnline){
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bitmap = BitmapFactory.decodeFile(pathImg,bmOptions);
            } else {
                bitmap = BitmapFactory.decodeStream(getAssets().open(pathImg));
            }
            Uri uri = getImageUri(PreviewImageActivity.this, bitmap);
            setBackgroundFromAssetsFolder(PreviewImageActivity.this, uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void isDownloadFile() {
        File mFile = new File(FILE_FOLDE + FOLDE_NAME +
                File.separator + System.currentTimeMillis() + FOLDE_NAME + JPG);
        Ion.with(PreviewImageActivity.this)
            .load(linkOnline)
            .write(mFile)
            .setCallback(new FutureCallback<File>() {
                @Override
                public void onCompleted(Exception e, File file) {
                    if (e == null) {
                        path = file.getAbsolutePath();
                        setWallpaperImg(path, true);
                    } else {
                        Toast.makeText(PreviewImageActivity.this,
                                "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
    }

    // Method to manually check connection status
    private boolean checkConnection() {
        return ConnectivityReceiver.isConnected();
    }


    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
        } else {
            message = "Sorry! Not connected to internet";
        }

        Toast.makeText(PreviewImageActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(PreviewImageActivity.this);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void initView() {
        ivPreviewImage = findViewById(R.id.ivPreviewImage);
        File mFile = new File(FILE_FOLDE + FOLDE_NAME);
        if (!mFile.exists()) {
            mFile.mkdirs();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 0, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

//    public static void setBackgroundFromAssetsFolder(Activity mContext, String imageSource, int flag){
//        DisplayMetrics metrics = new DisplayMetrics();
//        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        // get the height and width of screen
//        int height = metrics.heightPixels;
//        int width = metrics.widthPixels;
//
//        WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
//        try {
//            Bitmap bitmap = BitmapFactory.decodeStream(mContext.getAssets().open(imageSource));
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                wallpaperManager.setBitmap(bitmap, null, true, flag);
//            }
//
//            wallpaperManager.suggestDesiredDimensions(width, height);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void setBackgroundFromAssetsFolder(Context mContext, Uri uri) {
        Log.d("uri", uri.toString());
        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("mimeType", "image/jpeg");
        mContext.startActivity(Intent.createChooser(intent, "Set as:"));
    }
}
