package com.sec.shares.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sec.shares.R;
import com.sec.shares.db.GpBean;
import com.sec.shares.inter.OnItemClickListener;

import java.util.List;

public class GpListAdapter extends RecyclerView.Adapter<GpListAdapter.ViewHolder> {

    private List<GpBean> mCityList;

    public List<GpBean> getCityList() {
        return mCityList;
    }

    public void setCityList(List<GpBean> mCityList) {
        this.mCityList = mCityList;
        notifyDataSetChanged();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    private OnItemClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        view.setOnClickListener(view1 -> {
            if (mRecyclerView != null) {
                // 根据 RecyclerView 获得当前 View 的位置
                int position = mRecyclerView.getChildAdapterPosition(view1);
                onItemClickListener.onClick(view1, position);
            }
        });
        view.setOnLongClickListener(v -> {
            int position = mRecyclerView.getChildAdapterPosition(v);
            onItemLongClickListener.onClick(v, position);
            return false;
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GpBean city = mCityList.get(position);
        holder.cityName.setText(city.getMc());
    }

    @Override
    public int getItemCount() {
        if (mCityList != null) {
            return mCityList.size();
        } else {
            return 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;

        public ViewHolder(View view) {
            super(view);
            cityName = view.findViewById(R.id.mTvDetail);
        }
    }

    private RecyclerView mRecyclerView;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }
}
