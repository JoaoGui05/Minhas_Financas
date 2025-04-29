package com.example.teste1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GastosAdapter adapter;
    private DatabaseHelper databaseHelper;
    private Button btnAdicionarGasto, btnResumoCategoria;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseHelper = new DatabaseHelper(this);

        databaseHelper.limparGastos();
        recyclerView = findViewById(R.id.recyclerViewGastos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdicionarGasto = findViewById(R.id.btnAdicionarGasto);
        btnResumoCategoria = findViewById(R.id.btnResumoCategoria);

        btnAdicionarGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CadastroGasto.class);
                startActivity(intent);
            }
        });

        btnResumoCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ResumoCategoria.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarListaGastos();
    }

    private void carregarListaGastos() {
        List<Gastos> lista = databaseHelper.getAllGastos();
        if (adapter == null) {
            adapter = new GastosAdapter(lista);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setListaGastos(lista);
        }
    }
}