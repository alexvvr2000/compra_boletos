package com.megaboletos.usuarios;
import com.megaboletos.ObjetoBase;
import org.json.JSONObject;

import java.sql.Connection;
import java.time.LocalTime;
import java.util.Date;
public class Evento {
    private String nombre;
    private String lugar;
    private Date fecha;
    private LocalTime hora;
    private boolean estaCancelado;
    private Connection conexion;
    public Evento(Connection conexion,int idEvento){
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
    public boolean eventoCancelado() throws Exception{
        return true;
    }
}
