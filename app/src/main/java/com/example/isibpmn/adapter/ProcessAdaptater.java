package com.example.isibpmn.adapter;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.isibpmn.R;
import com.example.isibpmn.data.model.Input;
import com.example.isibpmn.data.model.LesInputs;
import com.example.isibpmn.data.model.ListeInputs;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static java.sql.Types.NULL;

public class ProcessAdaptater extends RecyclerView.Adapter<ProcessAdaptater.View_holder> {

    private List<Input> mData= new ArrayList<>();

    private Context context;


    public ProcessAdaptater(Context context, List<Input> data) {

        this.mData = data;
        this.context = context;
    }

    public void setListProcess(List<Input> data) {
        mData.clear();
        this.mData = data;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ProcessAdaptater.View_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.process_layout, viewGroup, false);
        return new ProcessAdaptater.View_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessAdaptater.View_holder holder, int position) {
        Log.i("dispname", "hello");
        String name=mData.get(position).getName();
        holder.myTextView.setText(name);
       String type=mData.get(position).getType();
        if ( type== "text") {
            holder.myEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        else if ( type== "date") {
            holder.myEditText.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
        }
    }
    @Override
    public int getItemCount() {
        return mData==null? 0 : mData.size();
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class View_holder extends RecyclerView.ViewHolder {
        TextView myTextView;
        EditText myEditText;

        public View_holder(@NonNull View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.textprocess);
            myEditText = (EditText) itemView.findViewById(R.id.process);



        }


    }
}