package com.megaboletos.usuarios;
import com.megaboletos.usuarios.MetodoPago;
import com.megaboletos.usuarios.builders.Cliente;

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
