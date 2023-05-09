package com.megaboletos.usuarios;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.Date;
public class Evento {
    private String nombre;
    private String lugar;
    private Date fecha;
    private Connection conexion;
    private int idEvento;
    public Evento(Connection conexion,int idEvento) throws Exception {
        if(!Evento.existeEvento(conexion,idEvento))
            throw new Exception("Evento no existe");
        PreparedStatement query = conexion.prepareStatement(
                "select nombre, lugar, fecha from evento where idevento = ?"
        );
        query.setInt(1, idEvento);
        ResultSet resultado = query.executeQuery();
        resultado.next();
        this.conexion = conexion;
        this.idEvento = idEvento;
        this.nombre = resultado.getString("nombre");
        this.fecha = resultado.getTimestamp("fecha");
        this.lugar = resultado.getString("lugar");
    }
    public String getNombre() {
        return nombre;
    }
    public String getLugar() {
        return lugar;
    }
    public Date getFecha() {
        return fecha;
    }
    public int getIdEvento() {
        return this.idEvento;
    }
    public boolean eventoCancelado() throws Exception{
        return true;
    }
    public JSONObject asientosDisponibles() throws Exception {
        PreparedStatement query = this.conexion.prepareStatement(
                "select filasocupadas from capacidad where idevento = ?"
        );
        query.setInt(1, this.idEvento);
        ResultSet resultado = query.executeQuery();
        resultado.next();
        String jsonAsientos = resultado.getString("filasocupadas");
        return new JSONObject(jsonAsientos);
    }
    public static boolean existeEvento(Connection conexion, int idEvento) throws Exception {
        PreparedStatement query = conexion.prepareStatement(
                "select exists(" +
                        "select nombre from evento where idevento = ?" +
                        ") as existe;"
        );
        query.setInt(1, idEvento);
        ResultSet conjunto = query.executeQuery();
        conjunto.next();
        return conjunto.getBoolean("existe");
    }
}
