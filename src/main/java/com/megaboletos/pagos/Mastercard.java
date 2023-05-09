package com.megaboletos.pagos;
import com.megaboletos.usuarios.Cliente;
public class Mastercard implements MetodoPago{
    Mastercard(Cliente clienteAComprar) {
    }
    @Override
    public boolean pagar(int cantidadDinero, int CVV) {
        return false;
    }
}
