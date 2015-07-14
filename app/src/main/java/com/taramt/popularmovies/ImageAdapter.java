package com.taramt.popularmovies;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * Created by praveengarimella on 14/07/15.
 */
public class ImageAdapter extends BaseAdapter{

    private final String baseURL = "http://image.tmdb.org/t/p/w185";
    private Context mContext;
    private ArrayList<PopularMovie> list = null;

    public ImageAdapter(Context c, ArrayList<PopularMovie> list) {
        if(list == null) list = new ArrayList<PopularMovie>();
        this.list = list;
        mContext = c;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        if (view == null) {
            view = new SquaredImageView(mContext);
            view.setScaleType(CENTER_CROP);
        }
        PopularMovie movie = (PopularMovie)getItem(position);
        Log.v("ImageAdapter", "Image URL:" + movie.getPoster());
        Picasso.with(mContext).load(baseURL + movie.getPoster()).into(view);
        return view;
    }

    //** An image view which always remains square with respect to its width. */
    final class SquaredImageView extends ImageView {
        public SquaredImageView(Context context) {
            super(context);
        }

        public SquaredImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
        }
    }

}
