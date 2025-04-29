package com.masanz.gda;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Docente implements Comparable<Docente> {

    private String nombre;
    private String apellidos;
    private LocalDateTime fechaInicio;

    public Docente() {
        this.nombre = "";
        this.apellidos = "";
        fechaInicio = LocalDateTime.now();
    }

    public Docente(String nombre, String apellidos) {
        this.nombre = nombre==null?"":nombre;
        this.apellidos = apellidos==null?"":apellidos;
        fechaInicio = LocalDateTime.now();
    }

    public Docente(String nombre, String apellidos, LocalDateTime fechaInicio) {
        this(nombre, apellidos);
        this.fechaInicio = fechaInicio;
    }

    public Docente(String nombre, String apellidos, String fechaInicio) {
        this(nombre, apellidos);
        this.fechaInicio = LocalDateTime.parse(fechaInicio + " 00:00:00",
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre==null?"":nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos==null?"":apellidos;
    }

    public String getFechaInicioYYYYMMDD() {
        return fechaInicio.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getApellidosNombre() {
        return String.format("%s, %s", apellidos, nombre);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Docente docente = (Docente) other;
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellidos, fechaInicio);
    }

    @Override
    public int compareTo(Docente other) {
        return this.toString().compareTo(other.toString());
    }

    @Override
    public String toString() {
        return String.format("%s, %s", apellidos, nombre);
    }

}
