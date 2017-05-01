package com.hanami.squirtle.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hanami.squirtlegank.R;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by hanami on 2017/4/23.
 */

public class GalleryViewPagerAdapter extends PagerAdapter {

    private List<String> urls;

    private Context mContext;

    private LayoutInflater mInflater;

    public GalleryViewPagerAdapter(Context context, List<String> urls) {

        this.urls = urls;
        this.mContext = context;

        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Log.d("GalleryActivity", "instantiateItem: --------");

        View view = mInflater.inflate(R.layout.item_gallery_page, container, false);
        PhotoView item_gallery_image = (PhotoView) view.findViewById(R.id.item_gallery_image);
        final ProgressBar item_gallery_progress = (ProgressBar) view.findViewById(R.id.item_gallery_progress);

        //加载图片
        Glide.with(mContext)
                .load(urls.get(position))
                .fitCenter()
                .thumbnail(0.4f)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        item_gallery_progress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(item_gallery_image);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d("GalleryActivity", "destroyItem: ----------------");
        container.removeView((View) object);
    }

}
