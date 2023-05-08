package com.megaboletos.usuarios.builders;
import com.megaboletos.usuarios.PermisoUsuario;

import java.sql.Connection;
import java.time.LocalTime;
import java.util.Date;
public class Evento {
    final private String nombre;
    final private String lugar;
    final private Date fecha;
    final private LocalTime hora;
    final private Connection conexion;
    private Evento(Builder instancia) {
        this.nombre = instancia.nombre;
        this.lugar = instancia.lugar;
        this.fecha = instancia.fecha;
        this.hora = instancia.hora;
        this.conexion = instancia.conexion;
    }
    public Evento(Connection conexion, int idEvento){
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
    public static class Builder {
        private String nombre;
        private String lugar;
        private Date fecha;
        private LocalTime hora;
        private Connection conexion;
        public Builder(PermisoUsuario permisoUsuario) {
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public void setLugar(String lugar) {
            this.lugar = lugar;
        }
        public void setFecha(Date fecha) {
            this.fecha = fecha;
        }
        public void setHora(LocalTime hora) {
            this.hora = hora;
        }
        public void setConexion(Connection conexion) {
            this.conexion = conexion;
        }
        public Evento crear() {
            return new Evento(this);
        }
    }
}
