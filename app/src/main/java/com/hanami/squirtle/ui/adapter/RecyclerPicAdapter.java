package com.hanami.squirtle.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hanami.squirtle.app.MyApplication;
import com.hanami.squirtle.bean.GankEntity;
import com.hanami.squirtle.utils.DisplayUtils;
import com.hanami.squirtlegank.R;
import com.hanami.squirtlegank.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanami on 2017/4/17.
 */

public class RecyclerPicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<GankEntity> list;

    private LayoutInflater mInflater;

    private int screenwidth;

    private OnItemClickListener mOnItemClickListener;

    public RecyclerPicAdapter(Context mContext, List<GankEntity> list) {


        this.mContext = mContext;
        this.list = list;

        mInflater = LayoutInflater.from(mContext);
        screenwidth = DisplayUtils.getScreenWidth(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = mInflater.inflate(R.layout.item_pic_layout, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final MyViewHolder mvh = (MyViewHolder) holder;

        final GankEntity gankEntity = list.get(position);

        Glide.with(mContext)
                .load(gankEntity.getUrl())
                .asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>(screenwidth / 2, screenwidth / 2) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        //根据图片的宽高比调整item布局的高度
                        if (gankEntity.getRealHeight() <= 0) {
                            float height = resource.getHeight();
                            float width = resource.getWidth();
                            Log.d(MyApplication.LOG_TAG, height + ":" + width);
                            float scale = height / width;
                            int realHeight = (int) (screenwidth / 2 * scale);

                            gankEntity.setRealHeight(realHeight);

                        }

                        ViewGroup.LayoutParams lp = mvh.item_pic_root.getLayoutParams();
                        lp.width = screenwidth / 2;
                        lp.height = gankEntity.getRealHeight();

                        mvh.item_pic_root.setLayoutParams(lp);
                        mvh.item_pic_iv.setImageBitmap(resource);

                    }
                });

        //如果设置了回调事件
        if (mOnItemClickListener != null) {
            //因为会复用View，所以要判断一下复用的View是否已经设置了点击监听器
            if (!mvh.item_pic_iv.hasOnClickListeners()) {
                mvh.item_pic_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(getItemCount(), mvh.getAdapterPosition());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.item_pic_root)
        RelativeLayout item_pic_root;

        @BindView(R2.id.item_pic_iv)
        ImageView item_pic_iv;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {

        void onItemClick(int size, int position);
    }
}
