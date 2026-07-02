package com.example.listadecompras;

import java.io.Serializable;

public class Transaction implements Serializable {
    private int id;
    private String concepto;
    private double monto;
    private String tipo; // "Ingreso" o "Gasto"

    public Transaction(int id, String concepto, double monto, String tipo) {
        this.id = id;
        this.concepto = concepto;
        this.monto = monto;
        this.tipo = tipo;
    }

    public Transaction(String concepto, double monto, String tipo) {
        this.concepto = concepto;
        this.monto = monto;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
