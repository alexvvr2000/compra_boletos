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
    private LocalTime hora;
    private Connection conexion;
    private int idEvento;
    public Evento(Connection conexion,int idEvento) throws Exception {
        this.conexion = conexion;
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
    public LocalTime getHora() {
        return hora;
    }
    public int getIdEvento() {
        return this.idEvento;
    }
    public boolean eventoCancelado() throws Exception{
        return true;
    }
    public JSONObject asientosDisponibles() throws Exception {
        return new JSONObject();
    }
    public static boolean existeEvento(Connection conexion, int idEvento) throws Exception {
        PreparedStatement query = conexion.prepareStatement(
                "select exists(" +
                        "select nombre from cliente where idevento = ?" +
                        ") as existe;"
        );
        query.setInt(1, idEvento);
        ResultSet conjunto = query.executeQuery();
        conjunto.next();
        return conjunto.getBoolean("existe");
    }
}
