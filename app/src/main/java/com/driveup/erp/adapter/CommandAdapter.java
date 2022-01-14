package com.driveup.erp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.driveup.erp.R;
import com.driveup.erp.model.Command;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommandAdapter extends RecyclerView.Adapter<CommandAdapter.CommandViewHolder> {
    private Context mContext;
    private List<Command> mData;

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
        holder.code_content.setText(": " + mData.get(position).getCode_cmd());
        holder.customer_content.setText(": " + mData.get(position).getCustomer_cmd());
        holder.delivery_content.setText(": " + mData.get(position).getDelivery_dt_cmd());
        holder.status_content.setText(": " + mData.get(position).getStatus_cmd());
        holder.order_content.setText(": " + mData.get(position).getOrder_dt_cmd());

        if (mData.get(position).getStatus_cmd().equals("Non payé")){
            holder.status_content.setTextColor(Color.RED);
        } else {
            holder.status_content.setTextColor(Color.GREEN);
        }

    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    public class CommandViewHolder extends RecyclerView.ViewHolder {
        // Call widget from here
        TextView code_content;
        TextView customer_content;
        TextView delivery_content;
        TextView order_content;
        TextView status_content;

        public CommandViewHolder(View itemView) {
            super(itemView);

            code_content = (TextView) itemView.findViewById(R.id.cmd_code_content);
            customer_content = (TextView) itemView.findViewById(R.id.cmd_customer_content);
            delivery_content = (TextView) itemView.findViewById(R.id.cmd_delivery_content);
            order_content = (TextView) itemView.findViewById(R.id.cmd_order_date);
            status_content = (TextView) itemView.findViewById(R.id.cmd_status_content);
        }
    }

    private String timestampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", calendar).toString();
        String hour = DateFormat.format("hh:mm", calendar).toString();
        return date + " à " + hour;
    }
}
