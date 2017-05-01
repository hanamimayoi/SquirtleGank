package com.hanami.squirtle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hanami.squirtle.ui.adapter.GalleryViewPagerAdapter;
import com.hanami.squirtle.ui.iVew.IGalleryView;
import com.hanami.squirtle.ui.presenter.impl.GalleryPresenterImpl;
import com.hanami.squirtlegank.R;
import com.hanami.squirtlegank.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class GalleryActivity extends AppCompatActivity implements IGalleryView {

    @BindView(R2.id.gallery_tool_bar)
    Toolbar gallery_tool_bar;

    @BindView(R2.id.gallery_view_pager)
    ViewPager gallery_view_pager;

    @BindView(R2.id.position_and_size)
    TextView position_and_size;


    private Intent mOriginalIntent;

    private GalleryPresenterImpl mPresenterImpl;

    private GalleryViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        mPresenterImpl = new GalleryPresenterImpl(this, this);

        mOriginalIntent = getIntent();

        mPresenterImpl.getImageList(mOriginalIntent);

        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(gallery_tool_bar);

        int position = mOriginalIntent.getIntExtra("position", 1) + 1;
        int size = mOriginalIntent.getIntExtra("size", 1);

        position_and_size.setText(position + "/" + size);
        gallery_tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViewPager(List<String> urls) {

        if (mAdapter == null) {
            mAdapter = new GalleryViewPagerAdapter(GalleryActivity.this, urls);
        }

        gallery_view_pager.setAdapter(mAdapter);
        gallery_view_pager.setCurrentItem(mOriginalIntent.getIntExtra("position", 1));
        gallery_view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("GalleryActivity", "onPageSelected: ----------------------");
                position_and_size.setText((position + 1) + "/" + mAdapter.getCount());

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    Log.d("GalleryActivity", "SCROLL_STATE_DRAGGING: ----------------");
                } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    Log.d("GalleryActivity", "SCROLL_STATE_SETTLING: ----------------");
                } else {
                    Log.d("GalleryActivity", "SCROLL_STATE_IDLE: ----------------");
                }
            }
        });

        //放大的图片在换页的时候进行还原
        gallery_view_pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                Log.d("GalleryActivity", "position: " + position);
                if (position == -1 || position == 1) {
                    PhotoView view = (PhotoView) page.findViewById(R.id.item_gallery_image);
                    PhotoViewAttacher pa = new PhotoViewAttacher(view);
                    pa.getDisplayMatrix().reset();
                }
            }
        });

    }

    @Override
    public void setImageList(List<String> urls) {
        initViewPager(urls);
    }
}
