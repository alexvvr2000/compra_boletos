package com.megaboletos.usuarios.builders;
import com.megaboletos.usuarios.PermisoUsuario;
import java.time.LocalTime;
import java.util.Date;
public class Evento {
    private String nombre;
    private String lugar;
    private Date fecha;
    private LocalTime hora;
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
        public Evento crear(PermisoUsuario usuario) {
            return new Evento(this);
        }
    }
    private Evento(Builder instancia) {
        this.nombre = instancia.nombre;
        this.lugar = instancia.lugar;
        this.fecha = instancia.fecha;
        this.hora = instancia.hora;
    }
}
