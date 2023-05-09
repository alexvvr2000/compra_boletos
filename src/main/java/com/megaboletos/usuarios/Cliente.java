package com.megaboletos.usuarios;
import com.megaboletos.ObjetoBase;
import com.megaboletos.pagos.MetodoPago;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
public class Cliente extends Usuario implements ObjetoBase {
    private final String[] campos = {
            "nombre",
            "apellidoPaterno",
            "apellidoMaterno",
            "correo",
            "claveInicioSesion"
    };
    private Cliente(final Builder instancia, String claveAcceso) throws Exception{
        super();
        this.conexionBase = instancia.conexion;
    }
    public Cliente(Connection connection, String correo, String claveAcceso) throws Exception {
        super(connection, correo, claveAcceso);
        if(Administrador.esAdmin( this.conexionBase, this.idUsuario)) {
            throw new Exception("Es admin");
        }
    }
    @Override
    public boolean actualizarDatos(JSONObject datos) throws Exception {
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
        query.setInt(1, this.idUsuario);
        int camposAfectados = query.executeUpdate();
        conexionBase.commit();
        return camposAfectados == 1;
    }
    public static class Builder implements ClassBuilder<Cliente> {
        private String nombre;
        private String apellidoPaterno;
        private String apellidoMaterno;
        private String correo;
        private String claveAcceso;
        private Connection conexion;
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public void setApellidoPaterno(String apellidoPaterno) {
            this.apellidoPaterno = apellidoPaterno;
        }
        public void setApellidoMaterno(String apellidoMaterno) {
            this.apellidoMaterno = apellidoMaterno;
        }
        public void setCorreo(String correo) {
            this.correo = correo;
        }
        public void setConexion(Connection conexion) {
            this.conexion = conexion;
        }
        public void setClaveAcceso(String claveAcceso) {
            this.claveAcceso = claveAcceso;
        }
        @Override
        public Cliente crear() throws Exception{
            return new Cliente(this, this.claveAcceso);
        }
        @Override
        public boolean camposValidos() {
            return false;
        }
    }
    public MetodoPago obtenerMetodoPago(int idMetodoPago) {
        return null;
    }
    public boolean modificarMetodoPago(int idMetodoPago, JSONObject datos) {
        return false;
    }
    public boolean eliminarMetodoPago(int idMetodoPago) {
        return false;
    }
}
