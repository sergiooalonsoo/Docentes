package com.masanz.gda;

public class Grupo implements Comparable<Grupo> {

    private String nombre;

    public Grupo(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public void copy(Grupo otro) {
        if (otro==null) return;
        this.nombre = otro.nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int compareTo(Grupo o) {
        return nombre.compareTo(o.nombre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grupo other = (Grupo) o;
        return nombre != null ? nombre.equals(other.nombre) : other.nombre == null;
    }

    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }

}
