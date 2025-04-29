package com.example.teste1;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumoCategoria extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textTotalMes, textResumoCategorias, textCategoriaMaiorGasto;
    private DatabaseHelper databaseHelper;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resumo_categoria);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        progressBar = findViewById(R.id.progressBarResumo);
        textTotalMes = findViewById(R.id.textTotalMes);
        textResumoCategorias = findViewById(R.id.textResumoCategorias);
        textCategoriaMaiorGasto = findViewById(R.id.textCategoriaMaiorGasto);

        databaseHelper = new DatabaseHelper(this);

        calcularResumo();
    }

    private void calcularResumo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                    List<Gastos> listaGastos = databaseHelper.getAllGastos();

                    double totalMes = 0;
                    Map<String, Double> gastosPorCategoria = new HashMap<>();

                    for (Gastos gasto : listaGastos) {
                        totalMes += gasto.getValor();

                        if (gastosPorCategoria.containsKey(gasto.getCategoria())) {
                            double atual = gastosPorCategoria.get(gasto.getCategoria());
                            gastosPorCategoria.put(gasto.getCategoria(), atual + gasto.getValor());
                        } else {
                            gastosPorCategoria.put(gasto.getCategoria(), gasto.getValor());
                        }
                    }

                    String categoriaMaiorGasto = "";
                    double maiorGasto = 0;
                    for (Map.Entry<String, Double> entry : gastosPorCategoria.entrySet()) {
                        if (entry.getValue() > maiorGasto) {
                            maiorGasto = entry.getValue();
                            categoriaMaiorGasto = entry.getKey();
                        }
                    }

                    double finalTotalMes = totalMes;
                    StringBuilder resumoCategorias = new StringBuilder();
                    for (Map.Entry<String, Double> entry : gastosPorCategoria.entrySet()) {
                        resumoCategorias.append(entry.getKey())
                                .append(": R$ ")
                                .append(String.format("%.2f", entry.getValue()))
                                .append("\n");
                    }
                    String finalResumoCategorias = resumoCategorias.toString();
                    String finalCategoriaMaiorGasto = categoriaMaiorGasto + " (R$ " + String.format("%.2f", maiorGasto) + ")";

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);

                            textTotalMes.setText("Total do mês: R$ " + String.format("%.2f", finalTotalMes));
                            textResumoCategorias.setText("Resumo por Categoria:\n" + finalResumoCategorias);
                            textCategoriaMaiorGasto.setText("Categoria com maior gasto: " + finalCategoriaMaiorGasto);

                            textTotalMes.setVisibility(View.VISIBLE);
                            textResumoCategorias.setVisibility(View.VISIBLE);
                            textCategoriaMaiorGasto.setVisibility(View.VISIBLE);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}