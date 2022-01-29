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

    String buying, stock, detail, intitule;

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

        intitule = mData.get(position).getIntitule_m();
        detail = mData.get(position).getDetail_m();
        stock = "" + mData.get(position).getStock_m();
        buying = "" + mData.get(position).getBuying_price_m() + " Fc";

        holder.intitule_fntr.setText(intitule);
        holder.detail_fntr.setText(detail);
        holder.stock_fntr.setText(stock);
        holder.buying_price_fntr.setText(buying);

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    public static class FurnitureViewHolder extends RecyclerView.ViewHolder {
        // Call widget from here
        TextView btn_del, btn_edit, buying_price_fntr, stock_fntr, detail_fntr, intitule_fntr;

        public FurnitureViewHolder(View itemView) {
            super(itemView);

            btn_del = (TextView) itemView.findViewById(R.id.btn_del);
            btn_edit = (TextView) itemView.findViewById(R.id.btn_edit);

            buying_price_fntr = (TextView) itemView.findViewById(R.id.pu_pr);
            stock_fntr = (TextView) itemView.findViewById(R.id.color_pr);
            detail_fntr = (TextView) itemView.findViewById(R.id.format_pr);
            intitule_fntr = (TextView) itemView.findViewById(R.id.intitule_pr);

        }
    }
}
