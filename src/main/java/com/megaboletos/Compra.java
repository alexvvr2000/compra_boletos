package com.megaboletos;
import com.megaboletos.pagos.MetodoPago;
import com.megaboletos.usuarios.ClassBuilder;
import com.megaboletos.usuarios.Cliente;
import java.sql.Connection;
import java.util.ArrayList;
public class Compra {
    private int idCliente = 0;
    private int idEvento = 0;
    private int idCompra;
    private  int precioFinal = 0;
    private Connection conexion;
    final ArrayList<String> asientos = new ArrayList<String>();
    public Compra(Connection conexion,int idCompra) {
        this.idCompra = idCompra;
        this.conexion = conexion;
    }
    private Compra(Cliente clientePorComprar, Builder nuevaCompra, MetodoPago metodoPago) {

    }
    public int getIdCliente() {
        return this.idCliente;
    }
    public int getIdEvento() {
        return this.idEvento;
    }
    public int getIdCompra() {
        return this.idCompra;
    }
    public int getPrecioFinal() {
        return this.precioFinal;
    }
    public ArrayList<String> getAsientos() {
        return this.asientos;
    }
    public boolean pagar(int CVV, Cliente cliente){
        return true;
    }
    public static class Builder implements ClassBuilder<Compra> {
        private Cliente clientePorComprar;
        private int idEvento;
        private ArrayList<String> asientos = new ArrayList<String>();
        private MetodoPago metodoPago;
        public Builder(Cliente clientePorComprar) {
            this.clientePorComprar = clientePorComprar;
        }
        public Builder agregarEvento(int idEvento, ArrayList<String> asientos) {
            this.idEvento = idEvento;
            return this;
        }
        public Builder agregarMetodoPago(MetodoPago metodoPago){
            this.metodoPago = metodoPago;
            return this;
        }
        @Override
        public Compra crear() throws Exception{
            return new Compra(this.clientePorComprar, this,this.metodoPago);
        }
        @Override
        public boolean camposValidos() {
            return false;
        }
        private boolean pagarAsientos() {
            return true;
        }
        private int calcularPrecioFinal(){
            return 0;
        }
    }
}
