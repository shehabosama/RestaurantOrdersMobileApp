package com.android.thresturent.Ui.Fragments.HomeFragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.thresturent.R;
import com.android.thresturent.common.model.Advertising;
import com.android.thresturent.common.network.Urls;
import com.squareup.picasso.Picasso;
import java.util.List;
public class AdvertisingAdapter extends RecyclerView.Adapter<AdvertisingAdapter.AdvertisingViewHolder> {
    private AdvertisingInterAction interAction;
    private Context context;
    private List<Advertising> list;

    public AdvertisingAdapter(Context context, List<Advertising> list, AdvertisingInterAction interAction) {
        this.context = context;
        this.list = list;
        this.interAction = interAction;
    }

    @NonNull
    @Override
    public AdvertisingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_desplay_ads, parent, false);
        
        return new AdvertisingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisingViewHolder holder, int position) {

        Advertising advertising = list.get(position);
        holder.itemDescription.setText(advertising.getAd_description());
        Picasso.with(context)
                .load(Urls.IMAGE_URL +advertising.getAd_image())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.itemImage);
        holder.setListener();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface AdvertisingInterAction {

        void onClick();
    }

    public class AdvertisingViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;
        private TextView itemDescription;
        private Button btn_request;

        public AdvertisingViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.imageview);
            itemDescription = itemView.findViewById(R.id.text_desc_item);
            btn_request = itemView.findViewById(R.id.btn_request_order);
        }

        public void setListener() {
            btn_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interAction.onClick();
                }
            });

        }
    }
}
