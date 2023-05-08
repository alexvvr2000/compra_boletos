package com.megaboletos.usuarios.builders;
import java.util.ArrayList;
import java.util.Currency;
public class Compra {
    private final Cliente cliente;
    private int idEvento;
    final ArrayList<String> asientos = new ArrayList<String>();
    private Currency precioFinal;
    Compra(Cliente cliente) {
        this.cliente = cliente;
    }
    public Cliente getCliente() {
        return cliente;
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
