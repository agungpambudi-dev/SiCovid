package com.example.sicovid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sicovid.Model.Hospital;
import com.example.sicovid.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterHospital extends RecyclerView.Adapter<AdapterHospital.ListVewHolder> {

    Context context;
    List<Hospital> listData;
    List<Hospital> listDataRs;

    public AdapterHospital(Context context, List<Hospital>listData){
        this.context = context;
        this.listData = listData;
        this.listDataRs = listData;
    }

    public interface OnItemClickCallback {
        void onItemClicked(Hospital data);
    }

    private AdapterHospital.OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(AdapterHospital.OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public AdapterHospital.ListVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital_data, parent, false);
        return new AdapterHospital.ListVewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterHospital.ListVewHolder holder, int position) {
        Hospital hospital = listDataRs.get(position);

        holder.tvName.setText(hospital.getName());
        holder.tvArea.setText(hospital.getArea());
        holder.tvAddress.setText(hospital.getAddress());
        holder.tvPhone.setText(hospital.getPhone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listDataRs.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listDataRs.size();
    }

    public static class ListVewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvArea, tvAddress, tvPhone;

        public ListVewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_hospital_name);
            tvArea = itemView.findViewById(R.id.tv_hospital_area);
            tvAddress = itemView.findViewById(R.id.tv_hospital_address);
            tvPhone = itemView.findViewById(R.id.tv_hospital_phone);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listDataRs = listData;
                } else {
                    List<Hospital> filteredList = new ArrayList<>();
                    for (Hospital hospital : listData) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (hospital.getArea().toLowerCase().contains(charString.toLowerCase()) || hospital.getArea().contains(charSequence)) {
                            filteredList.add(hospital);
                        }
                    }

                    listDataRs = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listDataRs;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listDataRs = (ArrayList<Hospital>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Hospital hospital);
    }

}
