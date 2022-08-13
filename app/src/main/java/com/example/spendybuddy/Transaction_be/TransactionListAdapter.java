package com.example.spendybuddy.Transaction_be;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendybuddy.R;
import com.example.spendybuddy.data.model.Transaction;
import com.example.spendybuddy.utils.RTDB;

import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionListHolder> {
    Context context;
    List<Transaction> record;
    String username;

    public TransactionListAdapter(Context context , List<Transaction> record, String username){
        this.context = context;
        this.record = record;
        this.username = username;
    }

    @NonNull
    @Override
    public TransactionListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_list_item_view,parent,false);
        return new TransactionListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionListHolder holder, int position) {
        holder.set(record.get(position));
    }



    @Override
    public int getItemCount() {
        return record.size();
    }

    public class TransactionListHolder extends RecyclerView.ViewHolder{
        TextView amountView ;
        TextView categoryView;
        TextView descriptionView;
        TextView dateView;
        AppCompatImageButton editButton;
        AppCompatImageButton deleteButton;
        RTDB rtdb;


        public TransactionListHolder(@NonNull View itemView) {
            super(itemView);
            amountView = itemView.findViewById(R.id.amount_text);
            categoryView = itemView.findViewById(R.id.category_view);
            descriptionView = itemView.findViewById(R.id.description_view);
            dateView = itemView.findViewById(R.id.time_view);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
            this.rtdb = new RTDB();

        }


        void set(Transaction m){
            this.dateView.setText(m.getDate());
            this.descriptionView.setText(m.getDescription());
            this.amountView.setText(String.valueOf(m.getAmount()));
            this.categoryView.setText(m.getTransactionType().toString());

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rtdb.deleteTransaction(m.getId());
                }
            });
            this.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context,TransactionEditActivity.class);
                    i.putExtra("transaction" , m);
                    i.putExtra("username", username);
                    context.startActivity(i);
                }
            });

        }

    }

}
