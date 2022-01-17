package com.driveup.erp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.driveup.erp.R;
import com.driveup.erp.model.LignCommand;

import java.util.List;

public class LignCommandAdapter extends RecyclerView.Adapter<LignCommandAdapter.CommandViewHolder> {
    private Context mContext;
    private List<LignCommand> mData;

    String id_lign;
    String detail;
    String quantity;
    String total_price;

    public LignCommandAdapter(Context mContext, List<LignCommand> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public LignCommandAdapter.CommandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_command_lign, parent, false);
        return new LignCommandAdapter.CommandViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull LignCommandAdapter.CommandViewHolder holder, int position) {
        // Retrieve data
        id_lign = "" + mData.get(position).getId_lign_cmd();
        detail = mData.get(position).getProduct_cmd();
        quantity = "" + mData.get(position).getQuantity_cmd();
        total_price = "" + mData.get(position).getTotal_price_cmd();

        holder.id_lign_cmd.setText(id_lign);
        holder.detail_cmd.setText(detail);
        holder.quantity_cmd.setText(quantity);
        holder.total_price_cmd.setText(total_price);

    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    public static class CommandViewHolder extends RecyclerView.ViewHolder {
        // Call widget from here
        TextView id_lign_cmd;
        TextView detail_cmd;
        TextView quantity_cmd;
        TextView total_price_cmd;

        public CommandViewHolder(View itemView) {
            super(itemView);

            id_lign_cmd = (TextView) itemView.findViewById(R.id.id_lign_cmd);
            detail_cmd = (TextView) itemView.findViewById(R.id.detail_cmd);
            quantity_cmd = (TextView) itemView.findViewById(R.id.quantity_cmd);
            total_price_cmd = (TextView) itemView.findViewById(R.id.total_price_cmd);

        }
    }

}
