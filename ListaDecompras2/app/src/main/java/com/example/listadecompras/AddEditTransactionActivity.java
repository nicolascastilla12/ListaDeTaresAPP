package com.example.listadecompras;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditTransactionActivity extends AppCompatActivity {

    private EditText editConcepto, editMonto;
    private RadioGroup radioGroupTipo;
    private RadioButton radioIngreso, radioGasto;
    private Button btnSave, btnDelete;
    private DatabaseHelper dbHelper;
    private Transaction currentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_transaction);

        editConcepto = findViewById(R.id.edit_concepto);
        editMonto = findViewById(R.id.edit_monto);
        radioGroupTipo = findViewById(R.id.radio_group_tipo);
        radioIngreso = findViewById(R.id.radio_ingreso);
        radioGasto = findViewById(R.id.radio_gasto);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);

        dbHelper = new DatabaseHelper(this);

        // Check if editing existing transaction
        currentTransaction = (Transaction) getIntent().getSerializableExtra("transaction");
        if (currentTransaction != null) {
            editConcepto.setText(currentTransaction.getConcepto());
            editMonto.setText(String.valueOf(currentTransaction.getMonto()));
            if (currentTransaction.getTipo().equals("Ingreso")) {
                radioIngreso.setChecked(true);
            } else {
                radioGasto.setChecked(true);
            }
            btnDelete.setVisibility(View.VISIBLE);
        }

        btnSave.setOnClickListener(v -> saveTransaction());
        btnDelete.setOnClickListener(v -> deleteTransaction());
    }

    private void saveTransaction() {
        String concepto = editConcepto.getText().toString().trim();
        String montoStr = editMonto.getText().toString().trim();
        String tipo = radioIngreso.isChecked() ? "Ingreso" : "Gasto";

        if (concepto.isEmpty() || montoStr.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double monto = Double.parseDouble(montoStr);

        if (currentTransaction == null) {
            // New transaction
            Transaction newTransaction = new Transaction(concepto, monto, tipo);
            dbHelper.insertTransaction(newTransaction);
            Toast.makeText(this, "Transacción guardada", Toast.LENGTH_SHORT).show();
        } else {
            // Update existing
            currentTransaction.setConcepto(concepto);
            currentTransaction.setMonto(monto);
            currentTransaction.setTipo(tipo);
            dbHelper.updateTransaction(currentTransaction);
            Toast.makeText(this, "Transacción actualizada", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void deleteTransaction() {
        if (currentTransaction != null) {
            dbHelper.deleteTransaction(currentTransaction.getId());
            Toast.makeText(this, "Transacción eliminada", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
