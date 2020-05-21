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

import com.ajitapp.covid19tracker.R;
import com.ajitapp.covid19tracker.StateWiseDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class StateListAdapter extends RecyclerView.Adapter<StateListAdapter.MyViewHolder> {

    private ArrayList<HashMap<String, String>> arrayList;
    Context context;
    int counter = 1;
    public StateListAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.state_list_item, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final String state_name = arrayList.get(position).get("state_name");
        final String confirm_cases = arrayList.get(position).get("confirm_cases");

        holder.state_nameTv.setText(state_name);
        holder.confirm_casesTv.setText(confirm_cases);
        holder.counterTv.setText(String.valueOf(counter ++ ));

        holder.state_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StateWiseDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("state_name", state_name);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView state_nameTv, confirm_casesTv, counterTv;
        CardView state_cardView;
        public MyViewHolder(View view) {
            super(view);

            this.state_nameTv = (TextView) view.findViewById(R.id.state_nameTv);
            this.confirm_casesTv = (TextView) view.findViewById(R.id.confirm_cases);
            this.counterTv = (TextView) view.findViewById(R.id.counter);
            this.state_cardView = (CardView) view.findViewById(R.id.state_card_view);
        }
    }

}