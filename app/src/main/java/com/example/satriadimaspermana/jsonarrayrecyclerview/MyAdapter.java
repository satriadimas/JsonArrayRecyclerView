package com.example.satriadimaspermana.jsonarrayrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Satria Dimas Permana on 7/15/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RootObjectViewHolder> {

    private List<RootObject> RootObjectList;
    public OnClickItem itemClick;
    Context c;

    public MyAdapter(OnClickItem itemClick) {
        this.itemClick = itemClick;
        RootObjectList = new ArrayList<>();
    }

    public MyAdapter(){
        RootObjectList = new ArrayList<>();
    }
    public interface OnClickItem{
        public void OnClick(RootObject RootObject);
    }

    private void add(RootObject item) {
        RootObjectList.add(item);
        notifyItemInserted(RootObjectList.size() - 1);
    }

    public void addAll(List<RootObject> RootObjectList) {
        for (RootObject RootObject : RootObjectList) {
            add(RootObject);
        }
    }

    public void remove(RootObject item) {
        int position = RootObjectList.indexOf(item);
        if (position > -1) {
            RootObjectList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public RootObject getItem(int position){
        return RootObjectList.get(position);
    }

    @Override
    public RootObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cardview, parent, false);
        RootObjectViewHolder RootObjectViewHolder = new RootObjectViewHolder(view);
        c = parent.getContext();
        return RootObjectViewHolder;
    }

    @Override
    public void onBindViewHolder(RootObjectViewHolder holder, int position) {
        final RootObject object = RootObjectList.get(position);
        Picasso.with(c).load(object.cover_url).into(holder.RootObjectImage);
        holder.RootObjectTitle.setText(object.title);
        holder.RootObjectDescription.setText(object.description);
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                itemClick.OnClick(RootObject);
            }
        });
    }

    @Override
    public int getItemCount() {
        return RootObjectList.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    static class RootObjectViewHolder extends RecyclerView.ViewHolder {

        TextView RootObjectTitle;
        TextView RootObjectDescription;
        ImageView RootObjectImage;
        View v;

        public RootObjectViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            RootObjectImage = (ImageView) itemView.findViewById(R.id.image);
            RootObjectTitle = (TextView) itemView.findViewById(R.id.title);
            RootObjectDescription = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
