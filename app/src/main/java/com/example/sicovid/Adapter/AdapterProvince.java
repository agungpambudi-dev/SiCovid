package com.example.sicovid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sicovid.Model.Province;
import com.example.sicovid.R;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterProvince extends RecyclerView.Adapter<AdapterProvince.ListViewHolder> {

    private Context context;
    private List<Province> listData;

    public AdapterProvince(Context context, List<Province> listData){
        this.context = context;
        this.listData = listData;
    }

    private AdapterProvince.OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(AdapterProvince.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(Province data);
    }

    @NonNull
    @Override
    public AdapterProvince.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_province_data, parent, false);
        return new AdapterProvince.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterProvince.ListViewHolder holder, int position) {
        Province province =listData.get(position);

        if(position==(getItemCount()-1)){
            holder.cvItem.setVisibility(View.GONE);
        }

        String positive = province.getPositive().replaceAll("[^\\d]", "");
        Double positiv = Double.parseDouble(positive);
        String Recovered = province.getRecovered().replaceAll("[^\\d]", "");
        Double Recover = Double.parseDouble(Recovered);
        String Deaths = province.getDeaths().replaceAll("[^\\d]", "");
        Double Death = Double.parseDouble(Deaths);

        holder.tvName.setText(province.getName());
        holder.tvPositive.setText(DecimalFormat.getInstance().format(positiv));
        holder.tvRecovered.setText(DecimalFormat.getInstance().format(Recover));
        holder.tvDeaths.setText(DecimalFormat.getInstance().format(Death));

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listData.get(holder.getAdapterPosition()));
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPositive, tvRecovered, tvDeaths, tvTitlePositive, tvTitleRecovered, tvTitleDeaths;
        CardView cvItem;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_province_name);
            tvPositive = itemView.findViewById(R.id.tv_total_positive);
            tvRecovered = itemView.findViewById(R.id.tv_total_recovered);
            tvDeaths = itemView.findViewById(R.id.tv_total_deaths);
            tvTitlePositive = itemView.findViewById(R.id.tv_positive);
            tvTitleRecovered = itemView.findViewById(R.id.tv_recovered);
            tvTitleDeaths = itemView.findViewById(R.id.tv_deaths);
            cvItem = itemView.findViewById(R.id.cv_item);
        }
    }

}
