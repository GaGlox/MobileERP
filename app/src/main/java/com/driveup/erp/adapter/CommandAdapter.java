package com.driveup.erp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.driveup.erp.CommandDetailActivity;
import com.driveup.erp.R;
import com.driveup.erp.model.Command;
import com.driveup.erp.model.LigneCommand;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommandAdapter extends RecyclerView.Adapter<CommandAdapter.CommandViewHolder> {
    private Context mContext;
    private List<Command> mData;

    String code;
    String customer;
    String deliveryDate;
    String status;
    String orderDate;

    public CommandAdapter(Context mContext, List<Command> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CommandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_command, parent, false);
        return new CommandViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CommandViewHolder holder, int position) {
        // Retrieve data
        code = mData.get(position).getCode_cmd();
        customer = mData.get(position).getCustomer_cmd();
        deliveryDate = timestampToString((long)mData.get(position).getDelivery_dt_cmd(), true);
        status = mData.get(position).getStatus_cmd();
        orderDate = timestampToString((long)mData.get(position).getOrder_dt_cmd(), true);

        holder.code_content.setText(code);
        holder.customer_content.setText(customer);
        holder.delivery_content.setText(deliveryDate);
        holder.status_content.setText(status);
        holder.order_content.setText(orderDate);

        if (mData.get(position).getStatus_cmd().equals("Non payé")){
            holder.status_content.setTextColor(Color.RED);
        } else {
            holder.status_content.setTextColor(Color.GREEN);
        }

        holder.arraw_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(mContext, CommandDetailActivity.class);
                detailIntent.putExtra("code", code);
                detailIntent.putExtra("customer", customer);
                detailIntent.putExtra("delivery", deliveryDate);
                detailIntent.putExtra("status", status);
                // Toast.makeText(mContext, "" + mData.get(position).getPostkey(), Toast.LENGTH_SHORT).show();
                detailIntent.putExtra("order", orderDate);

                mContext.startActivity(detailIntent);
            }
        });

    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    public static class CommandViewHolder extends RecyclerView.ViewHolder {
        // Call widget from here
        TextView code_content;
        TextView customer_content;
        TextView delivery_content;
        TextView order_content;
        TextView status_content;
        TextView arraw_next;

        public CommandViewHolder(View itemView) {
            super(itemView);

            code_content = (TextView) itemView.findViewById(R.id.cmd_code_content);
            customer_content = (TextView) itemView.findViewById(R.id.cmd_customer_content);
            delivery_content = (TextView) itemView.findViewById(R.id.cmd_delivery_content);
            order_content = (TextView) itemView.findViewById(R.id.cmd_order_date);
            status_content = (TextView) itemView.findViewById(R.id.cmd_status_content);
            arraw_next = (TextView) itemView.findViewById(R.id.arrow_next);

        }
    }

    private String timestampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", calendar).toString();
        String hour = DateFormat.format("hh:mm", calendar).toString();
        return date ;//+ " à " + hour;
    }

    private String timestampToString(long time, boolean ans) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd/MM/yyyy", calendar).toString();
        String hour = DateFormat.format("hh:mm", calendar).toString();
        return date ;//+ " à " + hour;
    }
}
