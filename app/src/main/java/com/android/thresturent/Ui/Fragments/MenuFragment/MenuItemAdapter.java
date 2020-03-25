package com.android.thresturent.Ui.Fragments.MenuFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.thresturent.R;
import com.android.thresturent.common.model.MenuItem;
import com.android.thresturent.common.network.Urls;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuViewHolder>{

    private List<MenuItem> menuItemList;
    private Context context;
    private MenuInterAction menuInterAction;

    public MenuItemAdapter(Context context, List<MenuItem> menuItemList ,MenuInterAction menuInterAction) {

        this.menuItemList = menuItemList;
        this.context = context;
        this.menuInterAction = menuInterAction;

    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.custome_desplay_menu_item, parent, false);
        return new MenuViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem menuItem = menuItemList.get(position);
        Picasso.with(context)
                .load(Urls.IMAGE_URL +menuItem.getItem_image())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.itemImage);
        holder.itemName.setText(menuItem.getItem_name());
        holder.itemDescription.setText(menuItem.getItem_description());
        holder.itemPrice.setText(String.valueOf(menuItem.getPrice()));
        holder.setListener(menuItem);
        holder.ratingBar.setRating(0.0f);
        if (menuItem.getEvaluation()>60){
            holder.ratingBar.setRating(1.0f);
        }if (menuItem.getEvaluation()>120){
            holder.ratingBar.setRating(2.0f);
        } if (menuItem.getEvaluation()>180){
            holder.ratingBar.setRating(3.0f);
        } if (menuItem.getEvaluation()>240){
            holder.ratingBar.setRating(3.0f);
        } if (menuItem.getEvaluation()>320){
            holder.ratingBar.setRating(4.0f);
        } if (menuItem.getEvaluation()>380){
            holder.ratingBar.setRating(5.0f);
        }

    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

    interface MenuInterAction{
        void onClickRequestOrder(MenuItem menuItem);

        void onClickItem(MenuItem menuItem);
    }
    public class MenuViewHolder extends RecyclerView.ViewHolder{
        private ImageView itemImage;
        private TextView itemName,itemDescription,itemPrice;
        private Button btn_request;
        private RatingBar ratingBar;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.imageview);
            itemName = itemView.findViewById(R.id.text_name_item_menu);
            itemDescription = itemView.findViewById(R.id.text_desc_item);
            btn_request = itemView.findViewById(R.id.btn_request_order);
            itemPrice = itemView.findViewById(R.id.text_price_item);
            ratingBar = itemView.findViewById(R.id.rate_bar);
        }

        public void setListener(final MenuItem menuItem){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuInterAction.onClickItem(menuItem);
                }
            });
            btn_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(menuInterAction !=null)
                        menuInterAction.onClickRequestOrder(menuItem);
                }
            });
        }

    }
}
