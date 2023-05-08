package com.megaboletos.usuarios.builders;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Currency;
public class Compra {
    private final int idCliente;
    private final int idEvento;
    private final int idCompra;
    private Currency precioFinal;
    final ArrayList<String> asientos = new ArrayList<String>();
    Compra(int idCompra) {
        this.idCompra = idCompra;
    }
    public int getIdEvento() {
        return idEvento;
    }
    public ArrayList<String> getAsientos() {
        return asientos;
    }
    public Currency getPrecioFinal() {
        return precioFinal;
    }
    public boolean pagar(int CVV){
        return true;
    }
}
