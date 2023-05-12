package com.megaboletos.usuarios;
import com.megaboletos.ObjetoBase;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Cliente extends Usuario implements ObjetoBase {
    public static String[] tipoCuenta = {
            "visa",
            "mastercard"
    };
    private final String[] campos = {
            "nombre",
            "apellidoPaterno",
            "apellidoMaterno",
            "correo",
            "claveInicioSesion"
    };
    private Cliente(final Builder instancia) throws Exception{
        super();
        if (Cliente.existeCorreo(instancia.conexion,instancia.correo))
            throw new Exception("Ya esta correo en base");
        this.conexionBase = instancia.conexion;
        this.nombre = instancia.nombre;
        this.apellidoPaterno = instancia.apellidoPaterno;
        this.apellidoMaterno = instancia.apellidoMaterno;
        this.correo = instancia.correo;
        PreparedStatement query = this.conexionBase.prepareStatement(
                "insert into usuario (nombre, apellidoPaterno, apellidoMaterno, correo, claveInicioSesion, esAdmin) " +
                        " values (?, ?, ?, ?, ?, ?) returning idUsuario;"
        );
        query.setString(1, this.nombre);
        query.setString(2, this.apellidoPaterno);
        query.setString(3, this.apellidoMaterno);
        query.setString(4, this.correo);
        query.setString(5, instancia.claveAcceso);
        query.setBoolean(6, false);
        ResultSet resultado = query.executeQuery();
        resultado.next();
        this.idUsuario = resultado.getInt("idUsuario");
        this.conexionBase.commit();
    }
    public Cliente(Connection connection, String correo, String claveAcceso) throws Exception {
        super(connection, correo, claveAcceso);
    }
    @Override
    public boolean actualizarDatos(JSONObject datos) throws Exception {
        if(this.sesionCerrada) throw new Exception("Sesion cerrada vuelva a abrirla");
        ArrayList<String> campoBase = new ArrayList<String>();
        for(String campo: this.campos) {
            if(!datos.has(campo)) continue;
            String nuevoCampo = datos.getString(campo);
            campoBase.add(
                    String.format(
                            "%s = '%s'", campo, nuevoCampo
                    )
            );
            switch (campo) {
                case "nombre":
                    this.nombre = nuevoCampo;
                    break;
                case "apellidoPaterno":
                    this.apellidoPaterno = nuevoCampo;
                    break;
                case "apellidoMaterno":
                    this.apellidoMaterno = nuevoCampo;
                    break;
                case "correo":
                    this.correo = nuevoCampo;
                    break;
            }
        }
        String[] arreglo = new String[campoBase.size()];
        String camposQuery = String.join(",", campoBase.toArray(arreglo));
        String stringQuery = String.format(
                "UPDATE usuario SET %s WHERE idusuario = %d"
                , camposQuery, this.idUsuario
        );
        PreparedStatement query = conexionBase.prepareStatement(stringQuery);
        int camposAfectados = query.executeUpdate();
        conexionBase.commit();
        return camposAfectados == 1;
    }
    @Override
    public boolean baja() throws Exception {
        PreparedStatement query = this.conexionBase.prepareStatement(
                "delete from usuario where idusuario = ?;"
        );
        query.setInt(1, this.getIdUsuario());
        int camposAfectados = query.executeUpdate();
        conexionBase.commit();
        this.sesionCerrada = true;
        return camposAfectados == 1;
    }
    public static class Builder implements ClassBuilder<Cliente> {
        private String nombre = "";
        private String apellidoPaterno = "";
        private String apellidoMaterno = "";
        private String correo = "";
        private String claveAcceso = "";
        private Connection conexion = null;
        public Builder(Connection conexion) {
            this.conexion = conexion;
        }
        public Builder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }
        public Builder setApellidoPaterno(String apellidoPaterno) {
            this.apellidoPaterno = apellidoPaterno;
            return this;
        }
        public Builder setApellidoMaterno(String apellidoMaterno) {
            this.apellidoMaterno = apellidoMaterno;
            return this;
        }
        public Builder setCorreo(String correo) throws Exception{
            if(Cliente.existeCorreo(this.conexion,correo))
                throw new Exception("Correo existe en base");
            this.correo = correo;
            return this;
        }
        public Builder setClaveAcceso(String claveAcceso) {
            this.claveAcceso = claveAcceso;
            return this;
        }
        @Override
        public Cliente crear() throws Exception{
            if(!this.camposValidos()) throw new Exception("Campos vacios o formato invalido");
            return new Cliente(this);
        }
        @Override
        public boolean camposValidos() {
            boolean nombreValido = this.nombre.length() >= 1 && this.nombre.length() <= 30;
            boolean apellidoPaternoValido = this.apellidoPaterno.length() >= 1 && this.apellidoPaterno.length() <= 30;
            boolean apellidoMaternoValido = this.apellidoMaterno.length() >= 1 && this.apellidoMaterno.length() <= 30;
            boolean correoValido = this.correo.length() >= 1 && this.correo.length() <= 30;
            boolean claveValida = this.claveAcceso.length() >= 1 && this.claveAcceso.length() <= 30;
            boolean conexionValida = this.conexion != null;

            return nombreValido &&
                    apellidoMaternoValido &&
                    apellidoPaternoValido &&
                    correoValido &&
                    claveValida &&
                    conexionValida;
        }
    }
    public static boolean existeCorreo(Connection conexion,String correo) throws Exception{
        PreparedStatement query = conexion.prepareStatement(
                "select exists(" +
                        "select nombre from usuario where correo = ?" +
                        ") as existe;"
        );
        query.setString(1, correo);
        ResultSet conjunto = query.executeQuery();
        conjunto.next();
        return conjunto.getBoolean("existe");
    }
    public Map<String, String> obtenerMetodoPago(int idMetodoPago) throws Exception{
        if (!this.tieneMetodoPago(idMetodoPago)) throw new Exception("Metodo de pago no existe");
        PreparedStatement query = this.conexionBase.prepareStatement(
                "select " +
                    "idUsuario, idMetodoPago,cuenta, fechavencimiento, tipocuenta " +
                " from metodopago where idmetodopago = ? and idusuario = ?;"
        );
        query.setInt(1, idMetodoPago);
        query.setInt(2, this.idUsuario);
        ResultSet resultado = query.executeQuery();
        boolean valoresEnBase = resultado.next();
        if(!valoresEnBase) return null;
        Map<String, String> metodoPago = new HashMap<String, String>();
        metodoPago.put("cuenta", resultado.getString("cuenta"));
        metodoPago.put("fechaVencimiento", resultado.getString("fechavencimiento"));
        metodoPago.put("tipoCuenta", resultado.getString("tipocuenta"));
        metodoPago.put("idMetodoPago", resultado.getString("idMetodoPago"));
        metodoPago.put("idUsuario", resultado.getString("idUsuario"));
        return Collections.unmodifiableMap(metodoPago);
    }
    public boolean modificarMetodoPago(int idMetodoPago, JSONObject datos) throws Exception{
        if(!this.tieneMetodoPago(idMetodoPago)) throw new Exception("No existe metodo pago");
        String[] campos = {"cuenta", "fechaVencimiento", "tipoCuenta"};
        ArrayList<String> valorUpdate = new ArrayList<String>();
        for(String campo: campos) {
            if(!datos.has(campo)) continue;
            String nuevoCampo = datos.getString(campo);
            String campoFormateado = campo.equals("tipoCuenta")
                    ? "%s = cast('%s' as multinacional)" : "%s = '%s'";
            valorUpdate.add(
                    String.format(campoFormateado, campo, nuevoCampo)
            );
        }
        String[] valoresNuevos = new String[valorUpdate.size()];
        valorUpdate.toArray(valoresNuevos);
        String listaUpdate = String.join(",", valoresNuevos);
        PreparedStatement query = this.conexionBase.prepareStatement(
                String.format("update metodopago set %s where idMetodoPago= ?", listaUpdate)
        );
        query.setInt(1,idMetodoPago);
        int camposAfectados = query.executeUpdate();
        this.conexionBase.commit();
        return camposAfectados == 1;
    }
    public boolean eliminarMetodoPago(int idMetodoPago) throws Exception {
        if(!this.tieneMetodoPago(idMetodoPago)) throw new Exception("No existe metodo pago");
        PreparedStatement query = this.conexionBase.prepareStatement(
                "delete from metodopago where idusuario = ? and idmetodopago = ?;"
        );
        query.setInt(1, this.getIdUsuario());
        query.setInt(2, idMetodoPago);
        int camposAfectados = query.executeUpdate();
        conexionBase.commit();
        return camposAfectados == 1;
    }
    public int agregarMetodoPago(String cuenta, String fechaVencimiento, String tipoCuenta) throws Exception{
        PreparedStatement query = this.conexionBase.prepareStatement(
                "insert into MetodoPago (idUsuario, cuenta, fechaVencimiento, tipoCuenta)" +
                " values(?, ?, ?, cast(? as multinacional)) returning idmetodopago;"
        );
        query.setInt(1, this.getIdUsuario());
        query.setString(2, cuenta);
        query.setString(3, fechaVencimiento);
        query.setString(4, tipoCuenta);
        ResultSet resultado = query.executeQuery();
        resultado.next();
        this.conexionBase.commit();
        return resultado.getInt("idmetodopago");
    }
    public boolean tieneMetodoPago(int idMetodoPago) throws Exception{
        PreparedStatement query = this.conexionBase.prepareStatement(
            "select exists ( select " +
                " metodopago.idmetodopago " +
                " from metodopago " +
                " inner join usuario on metodopago.idusuario = usuario.idusuario" +
                " where metodopago.idmetodopago = ? and metodopago.idusuario = ? " +
           ") as existemetodo;"
        );
        query.setInt(1, idMetodoPago);
        query.setInt(2, this.getIdUsuario());
        ResultSet resultado = query.executeQuery();
        resultado.next();
        return resultado.getBoolean("existemetodo");
    }
    public boolean existeCompra(int idCompra) throws Exception{
        PreparedStatement query = this.conexionBase.prepareStatement(
            "select exists ( " +
                "select " +
                    "usuario.idUsuario " +
                    "from usuario " +
                    "inner join compra on compra.idusuario = usuario.idusuario " +
                    "where usuario.idUsuario = ? and compra.idCompras = ? " +
            ") as existemetodo;"
        );
        query.setInt(1, this.getIdUsuario());
        query.setInt(2, idCompra);
        ResultSet resultado = query.executeQuery();
        resultado.next();
        return resultado.getBoolean("existemetodo");
    }
    public Integer cantidadMetodosPago() throws Exception{
        PreparedStatement query = this.conexionBase.prepareStatement(
                "select " +
                        "cast(count(idMetodoPago) as integer) as cantidad " +
                        "from metodopago where idUsuario = ?;"
        );
        query.setInt(1, this.getIdUsuario());
        ResultSet resultado = query.executeQuery();
        resultado.next();
        return resultado.getInt("cantidad");
    }
    public Integer cantidadCompras() throws Exception {
        PreparedStatement query = this.conexionBase.prepareStatement(
                "select " +
                        "cast(count(idCompras) as integer) as cantidad " +
                        "from compra where idUsuario = ?;"
        );
        query.setInt(1, this.getIdUsuario());
        ResultSet resultado = query.executeQuery();
        resultado.next();
        return resultado.getInt("cantidad");
    }
    public static class MetodosPagoIterator implements Iterator<Map<String, String>> {
        private int cantidadValores = 0;
        private int valorActual = 0;
        private ResultSet clavesCrudas = null;
        private Cliente cliente = null;
        public MetodosPagoIterator(Cliente cliente) throws Exception {
            this.cantidadValores = cliente.cantidadMetodosPago();
            if(this.cantidadValores == 0) throw new Exception("No hay clientes en base");
            PreparedStatement queryMetodosPago = cliente.conexionBase.prepareStatement(
                "select " +
                "idMetodoPago " +
                "from metodopago where idUsuario = ?;"
            );
            queryMetodosPago.setInt(1, cliente.getIdUsuario());
            this.clavesCrudas = queryMetodosPago.executeQuery();
            this.cliente = cliente;
        }
        @Override
        public boolean hasNext() {
            return this.cantidadValores != this.valorActual;
        }
        @Override
        public Map<String, String> next() {
            try{
                this.clavesCrudas.next();
                int idActual = this.clavesCrudas.getInt("idMetodoPago");
                Map<String, String> metodoActual = this.cliente.obtenerMetodoPago(idActual);
                this.valorActual++;
                return metodoActual;
            } catch (Exception e) {
                System.out.println(e.getClass().getName() + ": " + e.getMessage());
            }
            return null;
        }
    }
}
