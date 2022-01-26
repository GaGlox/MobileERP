package com.driveup.erp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.driveup.erp.R;
import com.driveup.erp.model.Furniture;

import java.util.List;

public class FurnitureAdapter extends RecyclerView.Adapter<FurnitureAdapter.FurnitureViewHolder>{
    private Context mContext;
    private List<Furniture> mData;



    public FurnitureAdapter(Context mContext, List<Furniture> mData){
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public FurnitureAdapter.FurnitureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_furniture, parent, false);
        return new FurnitureAdapter.FurnitureViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull FurnitureAdapter.FurnitureViewHolder holder, int position) {

    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    public static class FurnitureViewHolder extends RecyclerView.ViewHolder {
        // Call widget from here
        //TextView code_content;

        public FurnitureViewHolder(View itemView) {
            super(itemView);

            //code_content = (TextView) itemView.findViewById(R.id.cmd_code_content);

        }
    }
}
