package com.example.teste1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class GastosAdapter extends RecyclerView.Adapter<GastosAdapter.GastoViewHolder> {
    private List<Gastos> listaGastos;

    public GastosAdapter(List<Gastos> listaGastos) {

        this.listaGastos = new ArrayList<>(listaGastos);
    }

    @NonNull
    @Override
    public GastoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gasto, parent, false);
        return new GastoViewHolder(itemView);
    }

    @Override

    public void onBindViewHolder(@NonNull GastoViewHolder holder, int position) {
        Gastos gastos = listaGastos.get(position);
        holder.textDescricao.setText(gastos.getDescricao());
        holder.textValor.setText("R$ " + String.format("%.2f", gastos.getValor()));
        holder.textCategoria.setText(gastos.getCategoria());
        holder.textData.setText(gastos.getData());
    }
    @Override
    public int getItemCount() {
        return listaGastos.size();
    }

    public void setListaGastos(List<Gastos> lista) {
        this.listaGastos.clear();
        this.listaGastos.addAll(lista);
        notifyDataSetChanged();
    }

    public static class GastoViewHolder extends RecyclerView.ViewHolder {
        TextView textDescricao, textValor, textCategoria, textData;

        public GastoViewHolder(@NonNull View itemView) {
            super(itemView);
            textDescricao = itemView.findViewById(R.id.textDescricao);
            textValor = itemView.findViewById(R.id.textValor);
            textCategoria = itemView.findViewById(R.id.textCategoria);
            textData = itemView.findViewById(R.id.textData);
        }
    }
}