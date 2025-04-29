package com.masanz.gda;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Cargador {

    /**
     * Carga un fichero de tipo notas en el gestor pasado como parámetro.
     * @param gestor
     * @param file
     */
    public static void cargaNotas(Gestor gestor, String file) {
        Scanner sc = null;
        try {
            try {
                sc = new Scanner(Cargador.class.getResourceAsStream(file));
            }catch (Exception e){
                sc = new Scanner(file);
            }
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] a = linea.split(";");
                String nombreGrupo = a[0];
                String nombreAsignatura = a[1];
                String nombreAlumno = a[2];
                String apellidosAlumno = a[3];
                double nota = Double.parseDouble(a[4]);
                Grupo grupo = new Grupo(nombreGrupo);
                Asignatura asignatura = new Asignatura(nombreAsignatura);
                Estudiante estudiante = new Estudiante(nombreAlumno, apellidosAlumno, nota);
                // Añadir estudiante asignatura grupo
                TreeMap<Grupo, HashMap<Asignatura, ArrayList<Estudiante>>> registro = gestor.getRegistro();
                if (grupo==null || !registro.containsKey(grupo)) {
                    registro.put(grupo, new HashMap<>());
                }
                var asignaturaArrayListHashMap = registro.get(grupo);
                if (!asignaturaArrayListHashMap.containsKey(asignatura)) {
                    asignaturaArrayListHashMap.put(asignatura, new ArrayList<Estudiante>());
                }
                var listaEstudiantes = asignaturaArrayListHashMap.get(asignatura);
                if (listaEstudiantes.contains(estudiante)) {
                    int i = listaEstudiantes.indexOf(estudiante);
                    listaEstudiantes.remove(i);
                    listaEstudiantes.add(i, estudiante);
                }else {
                    listaEstudiantes.add(estudiante);
                }
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

    /**
     * Carga un fichero de tipo docentes en el gestor pasado como parámetro.
     * @param gestor
     * @param file
     */
    public static void cargaDocentes(Gestor gestor, String file) {
        Scanner sc = null;
        try {
            try {
                sc = new Scanner(Cargador.class.getResourceAsStream(file));
            }catch (Exception e){
                sc = new Scanner(file);
            }
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] a = linea.split(";");
                String nombre = a[0];
                String apellidos = a[1];
                String fechaInicio = a[2];
                Docente docente = new Docente(nombre, apellidos, fechaInicio);
                TreeSet<GrupoAsignatura> grupoAsignaturas = new TreeSet<>();
                for (int i = 3; i < a.length; i++) {
                    grupoAsignaturas.add(new GrupoAsignatura(a[i]));
                }
                // Añadir docentes con grupoAsignaturas
                TreeMap<Docente, TreeSet<GrupoAsignatura>> docentes = gestor.getMapaDocentes();
                docentes.put(docente, grupoAsignaturas);
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

}
