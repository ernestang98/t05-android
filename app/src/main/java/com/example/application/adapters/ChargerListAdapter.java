package com.example.application.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.example.application.R;
import com.example.application.activities.InfoActivity;
import com.example.application.models.ChargerObject;
import com.example.application.utils.ClickListener;

import java.util.List;

// https://howtodoinjava.com/java/serialization/custom-serialization-readobject-writeobject/
// https://stackoverflow.com/questions/8887197/reliably-convert-any-object-to-string-and-then-back-again/8887244

public class ChargerListAdapter extends RecyclerView.Adapter<ChargerListAdapter.ViewHolder> {

    public static final String TAG = "ChargerListAdapter";
    public static Context context;
    public static List<ChargerObject> listOfChargers;
    public ClickListener clickListener;

    // data is passed into the constructor
    public ChargerListAdapter(Context context, List<ChargerObject> listOfChargers, ClickListener clickListener) {
        this.context = context;
        this.listOfChargers = listOfChargers;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View chargerView = inflater.inflate(R.layout.charger_unit, parent, false);

        return new ViewHolder(chargerView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil((float) listOfChargers.size() / 2);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
        public void bind(int position) {
            Log.i(TAG, "Position " + position);

            int firstHalf = position * 2;
            int secondHalf = firstHalf + 1;

            TextView textView1 = itemView.findViewById(R.id.cu_group_1_container_header);
            TextView textView11 = itemView.findViewById(R.id.cu_group_1_container_subgroup_text);

            CardView cardView1 = itemView.findViewById(R.id.cu_group_1);
            cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startInfoPage(context, listOfChargers.get(firstHalf));
                    Log.i(TAG, "it be working, im position: " + firstHalf);
                }
            });

            textView1.setText(listOfChargers.get(firstHalf).getChargerName());
            textView11.setText(listOfChargers.get(firstHalf).getChargerPower() + listOfChargers.get(firstHalf).getChargerPowerUnit());

            if (secondHalf < listOfChargers.size()) {
                TextView textView2 = itemView.findViewById(R.id.cu_group_2_container_header);
                TextView textView22 = itemView.findViewById(R.id.cu_group_2_container_subgroup_text);
                textView2.setText(listOfChargers.get(secondHalf).getChargerName());
                textView22.setText(listOfChargers.get(secondHalf).getChargerPower() + listOfChargers.get(secondHalf).getChargerPowerUnit());
                CardView cardView2 = itemView.findViewById(R.id.cu_group_2);
                cardView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startInfoPage(context, listOfChargers.get(secondHalf));
                        Log.i(TAG, "it be working, im position: " + secondHalf);
                    }
                });
            } else {
                itemView.findViewById(R.id.cu_group_2).setVisibility(View.GONE);
                itemView.findViewById(R.id.cu_group_spacing).setVisibility(View.GONE);
            }
        }
    }

    public static void startInfoPage(Context context, ChargerObject data) {
        Intent intent = new Intent(context, InfoActivity.class);
        Gson gson = new Gson();
        String stringData = gson.toJson(data);
        intent.putExtra("data", stringData);
        context.startActivity(intent);
    }

}
