package com.masanz.gda;

public class Asignatura implements Comparable<Asignatura> {

    private String nombre;

    public Asignatura(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int compareTo(Asignatura o) {
        return nombre.compareTo(o.nombre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asignatura asignatura = (Asignatura) o;
        return nombre != null ? nombre.equals(asignatura.nombre) : asignatura.nombre == null;
    }

    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }

}
