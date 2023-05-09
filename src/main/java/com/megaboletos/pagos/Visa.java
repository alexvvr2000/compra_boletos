package com.megaboletos.pagos;
import com.megaboletos.usuarios.Cliente;
public class Visa implements MetodoPago{
    Visa(Cliente clienteAComprar) {
    }
    @Override
    public boolean pagar(int cantidadDinero, int CVV) {
        return false;
    }
}
