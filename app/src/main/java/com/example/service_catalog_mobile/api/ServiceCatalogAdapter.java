package com.example.service_catalog_mobile.api;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.service_catalog_mobile.R;

import java.util.ArrayList;

public class ServiceCatalogAdapter extends RecyclerView.Adapter<ServiceCatalogAdapter.ViewHolder> implements Filterable {
    private ArrayList<ServiceCatalog> mServiceData;
    private ArrayList<ServiceCatalog> mServiceDataAll;
    private Context mContext;
    private int lastPosition = -1;


    public ServiceCatalogAdapter(Context mContext, ArrayList<ServiceCatalog> mServiceData) {
        this.mContext = mContext;
        this.mServiceData = mServiceData;
        this.mServiceDataAll = mServiceData;
    }


    @Override
    public Filter getFilter() {
        return serviceFilter;
    }

    private Filter serviceFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charseq) {
            ArrayList<ServiceCatalog> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (charseq == null || charseq.length() == 0) {
                results.count = mServiceDataAll.size();
                results.values = mServiceDataAll;
            } else {
                String filterPattern = charseq.toString().toLowerCase().trim();

                for (ServiceCatalog item : mServiceDataAll) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }
            return results;
        }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
            mServiceData = (ArrayList) results.values;
            notifyDataSetChanged();
    }
};

    @NonNull
    @Override
    public ServiceCatalogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_servicecalatog, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceCatalogAdapter.ViewHolder holder, int position) {
        ServiceCatalog currentItem = mServiceData.get(position);

        holder.bindTo(currentItem);
        if(holder.getAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mServiceData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mNameText;
        private TextView mDescriptionText;
        private TextView mHrefText;
        private TextView mLifecycleStatusText;


        public ViewHolder(View itemView) {
            super(itemView);
            mNameText = itemView.findViewById(R.id.itemTitle);
            mDescriptionText = itemView.findViewById(R.id.subTitle);
            mHrefText = itemView.findViewById(R.id.price);
            mLifecycleStatusText = itemView.findViewById(R.id.price);
        }
        void bindTo(ServiceCatalog currentItem){
            mNameText.setText(currentItem.getName());
            mDescriptionText.setText(currentItem.getDescription());
            mHrefText.setText(currentItem.getHref());
            mLifecycleStatusText.setText(currentItem.getLifecycleStatus());


            itemView.findViewById(R.id.add_to_cart).setOnClickListener(v -> {
               final Intent intent = new Intent(mContext, UpdateServiceCatalog.class);
                intent.putExtra("CURRENT_ID", currentItem.getId());
                System.out.println(currentItem.getId());
                mContext.startActivity(intent);
            });
            itemView.findViewById(R.id.delete).setOnClickListener(view -> ((ServiceCatalogListActivity)mContext).deleteItem(currentItem));

        }
    }
}

