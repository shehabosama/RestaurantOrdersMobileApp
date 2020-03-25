package com.android.thresturent.Ui.Fragments.NotificationFragment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.thresturent.R;
import com.android.thresturent.common.model.MyOrder;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderHolder> {
    private MyOrderInterAction interAction;
    private Context context;
    private List<MyOrder> list;
    double sumofMyOrders;
    boolean isAdmin;

    public MyOrderAdapter(Context context, List<MyOrder> list, MyOrderInterAction interAction ,boolean isAdmin) {
        this.context = context;
        this.list = list;
        this.interAction = interAction;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public MyOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_desplay_my_order, parent, false);


        return new MyOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderHolder holder, int position) {
        MyOrder myOrder = list.get(position);
        holder.name_item.setText(myOrder.getItemName());
        holder.description_item.setText(myOrder.getItemDescription());
        holder.price_item.setText(myOrder.getPrice());
        holder.location_item.setText(myOrder.getLocation());
        if(myOrder.getConfirmation().equals("0")){
            holder.status_item.setText("Pending");
            holder.status_item.setBackgroundColor(Color.YELLOW);
            if(isAdmin){
                holder.reject_btn.setVisibility(View.VISIBLE);
                holder.accept_btn.setVisibility(View.VISIBLE);
                holder.delelte_image.setVisibility(View.GONE);
                holder.delete_ever.setVisibility(View.GONE);
            }
        }else if (myOrder.getConfirmation().equals("1")){
            holder.status_item.setText("Accepted");
            holder.status_item.setBackgroundColor(Color.GREEN);
            holder.reject_btn.setVisibility(View.GONE);
            holder.accept_btn.setVisibility(View.GONE);
            holder.delelte_image.setVisibility(View.VISIBLE);
            holder.delete_ever.setVisibility(View.GONE);
        }else{
            holder.status_item.setText("Rejected");
            holder.status_item.setBackgroundColor(Color.RED);
            holder.reject_btn.setVisibility(View.GONE);
            holder.accept_btn.setVisibility(View.GONE);
            holder.delelte_image.setVisibility(View.GONE);
            holder.delete_ever.setVisibility(View.VISIBLE);
        }
        holder.setListener(myOrder);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface MyOrderInterAction {
        void onClickAccept(MyOrder myOrder);

        void onClickReject(MyOrder myOrder);

        void onClickDelete(MyOrder myOrder);

        void onClickDeleteEver(MyOrder myOrder);
    }

    public class MyOrderHolder extends RecyclerView.ViewHolder {

        TextView name_item,description_item,price_item,status_item,location_item,delelte_image,delete_ever;
        Button reject_btn,accept_btn;



        public MyOrderHolder(@NonNull View itemView) {
            super(itemView);
            name_item = itemView.findViewById(R.id.text_user_name);
            description_item = itemView.findViewById(R.id.description_item);
            price_item = itemView.findViewById(R.id.price_item);
            reject_btn = itemView.findViewById(R.id.reject_btn);
            accept_btn = itemView.findViewById(R.id.accept_btn);
            status_item = itemView.findViewById(R.id.status_item);
            delelte_image = itemView.findViewById(R.id.delete_item);
            location_item = itemView.findViewById(R.id.location_item);
            delete_ever = itemView.findViewById(R.id.delete_item_ever);
            if(isAdmin){
                reject_btn.setVisibility(View.VISIBLE);
                accept_btn.setVisibility(View.VISIBLE);
                delelte_image.setVisibility(View.VISIBLE);
            }

        }

        public void setListener(final MyOrder myOrder) {
            accept_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(interAction != null){
                        interAction.onClickAccept(myOrder);
                    }
                }
            });
            reject_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(interAction != null){
                        interAction.onClickReject(myOrder);
                    }
                }
            });
            delelte_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(interAction !=null)
                        interAction.onClickDelete(myOrder);
                }
            });
            delete_ever.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(interAction !=null)
                        interAction.onClickDeleteEver(myOrder);
                }
            });

        }
    }
}
