package com.driveup.erp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.driveup.erp.R;
import com.driveup.erp.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    private Context mContext;
    private List<Product> mData;

    String unit_price, stock, format, intitule, color;

    public ProductAdapter(Context mContext, List<Product> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_product, parent, false);
        return new ProductAdapter.ProductViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {

        intitule = mData.get(position).getIntitule_p();
        format = mData.get(position).getFormat_p();
        stock = "" + mData.get(position).getStock_p();
        unit_price = "" + mData.get(position).getPu_p() + " Fc";
        color = mData.get(position).getColor_p();

        holder.intitule_pr.setText(intitule);
        holder.format_pr.setText(format);
        holder.color_pr.setText(color);
        holder.unit_price_pr.setText(unit_price);
        holder.stock_pr.setText(stock);

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

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        // Call widget from here
        TextView btn_del, btn_edit, unit_price_pr, color_pr, format_pr, intitule_pr, stock_pr;

        public ProductViewHolder(View itemView) {
            super(itemView);

            btn_del = (TextView) itemView.findViewById(R.id.btn_del);
            btn_edit = (TextView) itemView.findViewById(R.id.btn_edit);

            unit_price_pr = (TextView) itemView.findViewById(R.id.pu_pr);
            color_pr = (TextView) itemView.findViewById(R.id.color_pr);
            format_pr = (TextView) itemView.findViewById(R.id.format_pr);
            intitule_pr = (TextView) itemView.findViewById(R.id.intitule_pr);
            stock_pr = (TextView) itemView.findViewById(R.id.stock_pr);

        }
    }
}