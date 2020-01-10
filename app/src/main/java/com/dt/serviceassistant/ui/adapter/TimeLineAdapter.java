package com.dt.serviceassistant.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.bean.TimeLineBean;
import com.dt.serviceassistant.utils.VectorDrawableUtils;
import com.tsienlibrary.ui.widget.TimelineView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianghejie on 15/11/26.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {

    private ArrayList<TimeLineBean> mTimeLineList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public TimeLineAdapter(Context context, ArrayList<TimeLineBean> timeLineList) {
        this.mTimeLineList = timeLineList;
        this.mContext = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        View view = View.inflate(viewGroup.getContext(), R.layout.item_timeline, null);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_timeline, viewGroup, false);
        return new TimeLineViewHolder(view, viewType);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(TimeLineViewHolder viewHolder, final int position) {
        if (mTimeLineList.get(position).getStatus() == TimeLineBean.OrderStatus.INACTIVE) {
            setMarker(viewHolder, R.drawable.ic_marker_inactive, R.color.text_color9E9E9E);
        } else if (mTimeLineList.get(position).getStatus() == TimeLineBean.OrderStatus.ACTIVE) {
            setMarker(viewHolder, R.drawable.ic_marker_active, R.color.text_color9E9E9E);
        } else {
            setMarker(viewHolder, R.drawable.ic_marker, R.color.text_color9E9E9E);
        }

        viewHolder.tvMsg.setText(mTimeLineList.get(position).getMessage());
        viewHolder.tvDate.setText(mTimeLineList.get(position).getDate());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mTimeLineList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMsg;
        public TextView tvDate;
        public TimelineView timelineView;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tv_timeline_message);
            tvDate = itemView.findViewById(R.id.tv_timeline_date);
            timelineView = itemView.findViewById(R.id.timeLineView);
            timelineView.initLine(viewType);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private void setMarker(TimeLineViewHolder holder, int drawableResId, int colorFilter) {
        holder.timelineView.setMarker(VectorDrawableUtils.getDrawable(holder.itemView.getContext(), drawableResId, ContextCompat.getColor(holder.itemView.getContext(), colorFilter)));
    }
}





















