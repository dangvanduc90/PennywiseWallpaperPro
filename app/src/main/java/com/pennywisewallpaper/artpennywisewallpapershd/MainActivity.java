package com.pennywisewallpaper.artpennywisewallpapershd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightGridView;
import com.pennywisewallpaper.artpennywisewallpapershd.adapter.GridViewAdapter;
import com.pennywisewallpaper.artpennywisewallpapershd.object.ObjectImage;

import java.util.ArrayList;

import static com.pennywisewallpaper.artpennywisewallpapershd.Utils.CommonVL.IMAGE_SOURCE;


public class MainActivity extends Activity {

    GridViewAdapter mAdapter;
    private ExpandableHeightGridView mGridView;
    ArrayList<ObjectImage> mStringsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStringsImage = new ArrayList<>();
        for (int i = 1; i <= 99; i++) {
            mStringsImage.add(new ObjectImage(i + ".jpg.tbn",i + ".jpg"));
        }
        mAdapter = new GridViewAdapter(this, R.layout.row_gridview, mStringsImage);
        mGridView = (ExpandableHeightGridView) findViewById(R.id.gv_img);
        mGridView.setAdapter(mAdapter);
        mGridView.setExpanded(true);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ObjectImage mObjectImage = (ObjectImage) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(MainActivity.this, PreviewImageActivity.class);
                intent.putExtra(IMAGE_SOURCE, mObjectImage.getImgSourc());
                startActivity(intent);
            }
        });
    }
}
