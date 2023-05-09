package com.megaboletos.usuarios.builders;

public interface MetodoPago {
    boolean pagar(int cantidadDinero, int CVV);
}
