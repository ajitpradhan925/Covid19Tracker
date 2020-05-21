package com.ajitapp.covid19tracker.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitapp.covid19tracker.DistWiseDetailsActivity;
import com.ajitapp.covid19tracker.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DistrictListAdapter extends RecyclerView.Adapter<DistrictListAdapter.MyViewHolder> {

    private ArrayList<HashMap<String, String>> arrayList;
    Context context;
    int counter = 1;
    public DistrictListAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dist_list_item, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final String state_name = arrayList.get(position).get("state_name");
        final String confirm_cases = arrayList.get(position).get("confirm_cases");
        final String dist_name = arrayList.get(position).get("dist_name");

        holder.dist_nameTv.setText(dist_name);
        holder.confirm_casesTv.setText(confirm_cases);
        holder.counterTv.setText(String.valueOf(counter ++ ));

        holder.dist_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DistWiseDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("state_name", state_name);
                intent.putExtra("dist_name", dist_name);
                intent.putExtra("confirm_cases", confirm_cases);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dist_nameTv, confirm_casesTv, counterTv;
        CardView dist_cardView;
        public MyViewHolder(View view) {
            super(view);

            this.dist_nameTv = (TextView) view.findViewById(R.id.dist_nameTv);
            this.confirm_casesTv = (TextView) view.findViewById(R.id.dist_confirm_cases);
            this.counterTv = (TextView) view.findViewById(R.id.counter);
            this.dist_cardView = (CardView) view.findViewById(R.id.dist_card_view);
        }
    }

}