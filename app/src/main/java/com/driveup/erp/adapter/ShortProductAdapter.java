package com.driveup.erp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.driveup.erp.R;
import com.driveup.erp.model.Product;
import com.driveup.erp.model.ShortProduct;

import java.util.List;

public class ShortProductAdapter extends RecyclerView.Adapter<ShortProductAdapter.ProductViewHolder>{
    private Context mContext;
    private List<ShortProduct> mData;

    String stock, format;

    public ShortProductAdapter(Context mContext, List<ShortProduct> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ShortProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_short_product, parent, false);
        return new ShortProductAdapter.ProductViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ShortProductAdapter.ProductViewHolder holder, int position) {

        format = mData.get(position).getFormat_p();
        stock = "" + mData.get(position).getStock_p();

        holder.format_pr.setText(format);

        int color = Color.RED;

        if(mData.get(position).getStock_p() < 5){
            holder.stock_pr.setText(stock);
            holder.stock_pr.setTextColor(color);
        }else{
            holder.stock_pr.setText(stock);
        }

    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        // Call widget from here
        TextView format_pr, stock_pr;

        public ProductViewHolder(View itemView) {
            super(itemView);

            format_pr = (TextView) itemView.findViewById(R.id.format_pr);
            stock_pr = (TextView) itemView.findViewById(R.id.stock_pr);
        }
    }
}