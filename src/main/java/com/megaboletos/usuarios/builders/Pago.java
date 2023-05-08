package com.megaboletos.usuarios.builders;
import java.sql.Connection;
class Visa implements MetodoPago {
    @Override
    public boolean pagar(int cantidadDinero, int CVV) {
        return false;
    }
}
class Mastercard implements MetodoPago {
    @Override
    public boolean pagar(int cantidadDinero, int CVV) {
        return false;
    }
}
