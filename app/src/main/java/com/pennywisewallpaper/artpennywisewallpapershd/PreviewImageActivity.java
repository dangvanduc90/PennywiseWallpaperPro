package com.pennywisewallpaper.artpennywisewallpapershd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.pennywisewallpaper.artpennywisewallpapershd.Utils.CommonVL.ASSEST_URI;
import static com.pennywisewallpaper.artpennywisewallpapershd.Utils.CommonVL.IMAGE_SOURCE;

public class PreviewImageActivity extends AppCompatActivity {

    ImageView ivPreviewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);
        Intent intent = getIntent();
        final String imageSource = intent.getStringExtra(IMAGE_SOURCE);

        initView();

        Glide.with(this).load(ASSEST_URI+imageSource).centerCrop().into(ivPreviewImage);

        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
        final FloatingActionsMenu multipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);

        actionA.setTitle("Set As");
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open(imageSource));
                    Uri uri = getImageUri(PreviewImageActivity.this, bitmap);
                    setBackgroundFromAssetsFolder(PreviewImageActivity.this, uri);
                    Toast.makeText(PreviewImageActivity.this, "LockScreen was set successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        ivPreviewImage = findViewById(R.id.ivPreviewImage);
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

    public void setBackgroundFromAssetsFolder(Context mContext, Uri uri){
        Log.d("uri", uri.toString());
        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("mimeType", "image/jpeg");
        mContext.startActivity(Intent.createChooser(intent, "Set as:"));
    }
}
