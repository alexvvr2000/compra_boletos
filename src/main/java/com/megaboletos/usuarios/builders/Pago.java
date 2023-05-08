package com.megaboletos.usuarios.builders;
import java.sql.Connection;
interface agregarCVV {
    MetodoPago agregarCVV(int CVV);
}
public class Pago implements agregarCVV{
    private Connection conexion;
    private Compra compraRealizable;
    Pago(Connection conexion, Compra compraRealizable) {
        this.conexion = conexion;
        this.compraRealizable = compraRealizable;
    }

    @Override
    public MetodoPago agregarCVV(int CVV) {
    }
}
class Visa implements MetodoPago {
    @Override
    public boolean pagar() {
        return false;
    }
}
class Mastercard implements MetodoPago {
    @Override
    public boolean pagar() {
        return false;
    }
}
