package com.megaboletos.usuarios.builders;
import java.sql.Connection;
public class Pago {
    Pago(Connection conexion, Compra compraRealizable, int CVV) {

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
