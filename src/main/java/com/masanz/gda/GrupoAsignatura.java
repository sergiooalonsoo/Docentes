package com.masanz.gda;

import java.util.Objects;

public class GrupoAsignatura implements Comparable<GrupoAsignatura>{

    private Grupo grupo;
    private Asignatura asignatura;

    public GrupoAsignatura(Grupo grupo, Asignatura asignatura) {
        this.grupo = grupo;
        this.asignatura = asignatura;
    }

    public GrupoAsignatura(String nombreGrupoAsignatura) {
        int idx = nombreGrupoAsignatura.indexOf('-');
        String nombreGrupo = nombreGrupoAsignatura.substring(0, idx);
        String nombreAsignatura = nombreGrupoAsignatura.substring(idx+1, nombreGrupoAsignatura.length());
        this.grupo = new Grupo(nombreGrupo);
        this.asignatura = new Asignatura(nombreAsignatura);
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    @Override
    public String toString() {
        return grupo.toString() + "-" + asignatura.toString();
    }

    @Override
    public int compareTo(GrupoAsignatura other) {
        return this.toString().compareTo(other.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrupoAsignatura that = (GrupoAsignatura) o;
        return this.grupo.equals(that.grupo) && this.asignatura.equals(that.asignatura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grupo, asignatura);
    }

}
