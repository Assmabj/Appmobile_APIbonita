package com.example.isibpmn.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isibpmn.ProcessActivity;
import com.example.isibpmn.R;
import com.example.isibpmn.data.model.ListeProcess;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class ListeProcessAdapter extends RecyclerView.Adapter<ListeProcessAdapter.viewHolder> {

    private List<ListeProcess> mData;
   private ClickedItem clickedItem;
    private Context context;

    public ListeProcessAdapter(  ClickedItem clickedItem) {

      this.clickedItem=clickedItem;
    }
public void setListProcess(Context context, List<ListeProcess> data)
 { mData=new ArrayList<>();
     this.context=context;
     this.mData=data;
 notifyDataSetChanged();

}

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.liste_process_layout,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {

        holder.myTextView.setText(mData.get(position).getDisplayName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
          @Override
        public void onClick(View view) {
              clickedItem.ClickedUser(mData.get(position).getId());
       // Intent intent = new Intent(context, ProcessActivity.class);
        //Bundle b= new Bundle();
        //intent.putExtra("id",mData.get(position).getId());
        //intent.putExtras(b);



       // context.startActivity(intent);

    }
});


    }

    @Override
    public int getItemCount() {
        return mData==null? 0: mData.size();
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public interface ClickedItem{
        public void ClickedUser(String id);
    }
    public static class viewHolder extends RecyclerView.ViewHolder  {
        TextView myTextView;

       CardView cardView;
       RelativeLayout rl;
     public   viewHolder(@NonNull View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.textprocess);
            cardView=(CardView) itemView.findViewById(R.id.cv);
             rl=(RelativeLayout) itemView.findViewById(R.id.rl);


     }


    }




    }

