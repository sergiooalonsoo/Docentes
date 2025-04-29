package com.masanz.gda;

public class Estudiante implements Comparable<Estudiante> {

    private String nombre;
    private String apellidos;
    private double nota;

    public Estudiante(String nombre, String apellidos) {
        this.nombre = nombre==null?"":nombre;
        this.apellidos = apellidos==null?"":apellidos;
        nota = 0;
    }

    public Estudiante(String nombre, String apellidos, double nota) {
        this(nombre, apellidos);
        this.nota = nota;
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

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getApellidosNombre() {
        return String.format("%s, %s", apellidos, nombre);
    }

    @Override
    public String toString() {
        return String.format("%s, %s (%.1f)", apellidos, nombre, nota);
    }

    @Override
    public int compareTo(Estudiante o) {
        return (apellidos+nombre).compareTo((o.apellidos+o.nombre));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estudiante that = (Estudiante) o;
        if (!apellidos.equals(that.apellidos)) return false;
        return nombre.equals(that.nombre);
    }

    @Override
    public int hashCode() {
        int result;
        result = apellidos.hashCode();
        result = 31 * result + nombre.hashCode();
        return result;
    }

}
