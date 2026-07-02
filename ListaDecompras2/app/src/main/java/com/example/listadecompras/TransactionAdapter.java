package com.example.listadecompras;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactionList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Transaction transaction);
    }

    public TransactionAdapter(List<Transaction> transactionList, OnItemClickListener listener) {
        this.transactionList = transactionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.txtConcepto.setText(transaction.getConcepto());
        holder.txtMonto.setText(String.format(Locale.getDefault(), "$%.2f", transaction.getMonto()));
        holder.txtTipo.setText(transaction.getTipo());

        if ("Ingreso".equals(transaction.getTipo())) {
            holder.indicator.setBackgroundColor(Color.parseColor("#4CAF50")); // Green
            holder.txtMonto.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            holder.indicator.setBackgroundColor(Color.parseColor("#F44336")); // Red
            holder.txtMonto.setTextColor(Color.parseColor("#F44336"));
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(transaction));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactionList = transactions;
        notifyDataSetChanged();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView txtConcepto, txtMonto, txtTipo;
        View indicator;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtConcepto = itemView.findViewById(R.id.txt_concepto);
            txtMonto = itemView.findViewById(R.id.txt_monto);
            txtTipo = itemView.findViewById(R.id.txt_tipo);
            indicator = itemView.findViewById(R.id.indicator_type);
        }
    }
}
