package com.megaboletos.usuarios.builders;
import java.sql.Connection;
class Visa implements MetodoPago {
    Visa(Cliente clienteAComprar) {
    }
    @Override
    public boolean pagar(int cantidadDinero, int CVV) {
        return false;
    }
}
class Mastercard implements MetodoPago {
    Mastercard(Cliente clienteAComprar) {
    }
    @Override
    public boolean pagar(int cantidadDinero, int CVV) {
        return false;
    }
}
