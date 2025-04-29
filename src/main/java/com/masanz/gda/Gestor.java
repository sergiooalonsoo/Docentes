package com.masanz.gda;

import javax.print.Doc;
import java.util.*;

/**
 * @author Nombre Apellidos
 */
public class Gestor {

    private TreeMap<Grupo, HashMap<Asignatura, ArrayList<Estudiante>>> registro;

    private TreeMap<Docente, TreeSet<GrupoAsignatura>> mapaDocentes;

    public Gestor() {
        registro = new TreeMap<>();
        mapaDocentes = new TreeMap<>();
    }

    //region get/setRegistro get/setDocentes
    public void setRegistro(TreeMap<Grupo, HashMap<Asignatura, ArrayList<Estudiante>>> registro){
        this.registro = registro;
    }
    public TreeMap<Grupo, HashMap<Asignatura, ArrayList<Estudiante>>> getRegistro() {
        return registro;
    }

    public TreeMap<Docente, TreeSet<GrupoAsignatura>> getMapaDocentes() {
        return mapaDocentes;
    }

    public void setMapaDocentes(TreeMap<Docente, TreeSet<GrupoAsignatura>> mapaDocentes) {
        this.mapaDocentes = mapaDocentes;
    }
    //endregion

    //region operaciones grupo 11-14

    /**
     * Si el grupo existe en el registro.
     * @param grupo Instancia de Grupo, puede ser null.
     * @return Devuelve null si el grupo es nulo o true o false dependiendo si la clave grupo existe en el registro.
     */
    public boolean existeGrupo(Grupo grupo) {
        if (grupo==null) return false;
        return registro.containsKey(grupo);
    }

    /**
     * Añade una entrada con el grupo y un HashMap nuevo asociado como valor
     * reemplazando a una entrada con la misma clave si ya existiese.
     * @param grupo Instancia de un grupo
     */
    public void anadirGrupo(Grupo grupo) {
        if (grupo!=null)
            registro.put(grupo, new HashMap<>());
    }

    /**
     * Devuelve un conjunto ordenado no repetido de grupos formado con
     * el conjunto de grupos que contiene el registro como claves.
     * @return TreeSet de grupos del registro
     */
    public TreeSet<Grupo> getGrupos() {
        return new TreeSet<>(registro.keySet());
    }

    /**
     * Elimina la entrada del grupo indicado del registro.
     * @param grupo Instancia de Grupo.
     */
    public void borrarGrupo(Grupo grupo) {
        registro.remove(grupo);
    }

    //endregion

    //region operaciones asignatura 21-25

    /**
     * Existe la asignatura en el grupo.
     * @param asignatura
     * @param grupo
     * @return Si existe la asignatura asociada al grupo indicado en el registro.
     */
    public boolean existeAsignaturaGrupo(Asignatura asignatura, Grupo grupo) {
        if (!existeGrupo(grupo)) {
            return false;
        }
        var asignaturaArrayListHashMap = registro.get(grupo);
        return asignaturaArrayListHashMap.containsKey(asignatura);
    }

    /**
     * Si el grupo no existe en el registro se deberá añadir dentro de este método.
     * Si existe se asignará al mapa la asignatura y un nuevo arraylist (*)
     * Incorpora una lista de estudiantes nueva asociada a una asignatura al grupo indicado.
     * @param asignatura
     * @param grupo
     */
    public void anadirAsignaturaGrupo(Asignatura asignatura, Grupo grupo) {
        if (!existeGrupo(grupo)) {
            anadirGrupo(grupo);
        }
        var asignaturaArrayListHashMap = registro.get(grupo);
        asignaturaArrayListHashMap.put(asignatura, new ArrayList<>());
    }

    /**
     * Devuelve el conjunto de asignaturas asociadas al grupo indicado.
     * @param grupo
     * @return
     */
    public HashSet<Asignatura> getAsignaturas(Grupo grupo) {
        return new HashSet<>(registro.get(grupo).keySet());
    }

    /**
     * Devuelve un conjunto de asignaturas no repetidas de todos los grupos.
     * @return
     */
    public HashSet<Asignatura> getAsignaturas() {
        HashSet<Asignatura> asignaturas = new HashSet<>();
        for (Map.Entry<Grupo, HashMap<Asignatura, ArrayList<Estudiante>>> grupo : registro.entrySet()) {
            asignaturas.addAll(grupo.getValue().keySet());
        }
        return asignaturas;
    }

    /**
     * Si existe la asignatura asociada al grupo elimina la asignatura y la lista de estudiantes asociada.
     * @param asignatura
     * @param grupo
     */
    public void borrarAsignaturaGrupo(Asignatura asignatura, Grupo grupo) {
        if (existeAsignaturaGrupo(asignatura, grupo)) {
            registro.get(grupo).remove(asignatura);
        }
    }

    //endregion

    //region operaciones estudiante 31-35

    /**
     * Devuelve la lista de estudiantes de la asignatura de un grupo si existe, sino devuelve null.
     * @param asignatura
     * @param grupo
     * @return
     */
    public ArrayList<Estudiante> getListaEstudiantesAsignaturaGrupo(Asignatura asignatura, Grupo grupo) {
        if (!existeGrupo(grupo) || !existeAsignaturaGrupo(asignatura, grupo)) {
            return null;
        }
        return registro.get(grupo).get(asignatura);
    }

    /**
     * Si existe un/a estudiante en la asignatura de un grupo.
     * @param estudiante
     * @param asignatura
     * @param grupo
     * @return
     */
    public boolean existeEstudianteAsignaturaGrupo(Estudiante estudiante, Asignatura asignatura, Grupo grupo) {
        if (!existeGrupo(grupo) || !existeAsignaturaGrupo(asignatura, grupo)) {
            return false;
        }
        var lista = getListaEstudiantesAsignaturaGrupo(asignatura, grupo);
        return lista.contains(estudiante);
    }

    /**
     * Devuelve la instancia del/a estudiante asociado/a a la asignatura indicada de un grupo
     * con la nota que tiene o null si no existe.
     * @param estudiante
     * @param asignatura
     * @param grupo
     * @return
     */
    public Estudiante getEstudianteAsignaturaGrupo(Estudiante estudiante, Asignatura asignatura, Grupo grupo) {
        if (!existeGrupo(grupo)) {
            return null;
        }
        if (!existeAsignaturaGrupo(asignatura, grupo)) {
            return null;
        }
        var lista = getListaEstudiantesAsignaturaGrupo(asignatura, grupo);
        if (!lista.contains(estudiante)) {
            return null;
        }else {
            int i = lista.indexOf(estudiante);
            return lista.get(i);
        }
    }


    /**
     * Si el grupo no existiese se debería crear en este método.
     * Análogamente, si no existe la asignatura asociada al grupo.
     * Al añadir la/el estudiante a la lista de estudiantes asociado a la asignatura del grupo,
     * si la/el estudiante ya existía se debe actualizar su nota con la que hay en el parámetro del método y
     * sino agregar una referencia a esta instancia.
     * @param estudiante
     * @param asignatura
     * @param grupo
     */
    public void anadirEstudianteAsignaturaGrupo(Estudiante estudiante, Asignatura asignatura, Grupo grupo) {
        if (!existeGrupo(grupo)) {
            anadirGrupo(grupo);
        }
        if (!existeAsignaturaGrupo(asignatura, grupo)) {
            anadirAsignaturaGrupo(asignatura, grupo);
        }
        var lista = getListaEstudiantesAsignaturaGrupo(asignatura, grupo);
        if (lista.contains(estudiante)) {
            int i = lista.indexOf(estudiante);
            lista.remove(i);
            lista.add(i, estudiante);
        }else {
            lista.add(estudiante);
        }
    }

    /**
     * Si existe la o el estudiante asociada/o a la asignatura asociada al grupo elimina a esta/este de la lista de estudiantes.
     * @param estudiante
     * @param asignatura
     * @param grupo
     */
    public void borrarEstudianteAsignaturaGrupo(Estudiante estudiante, Asignatura asignatura, Grupo grupo) {
        if (existeEstudianteAsignaturaGrupo(estudiante, asignatura, grupo)) {
            registro.get(grupo).get(asignatura).remove(estudiante);
        }
    }

    //endregion

    //region listados estudiantes 41-43

    /**
     * Devuelve una lista de estudiantes no repetidos de un grupo.
     * No importa la nota del/a estudiante de qué asignatura sea (no se mostrará).
     * @param grupo
     * @return
     */
    public ArrayList<Estudiante> getEstudiantes(Grupo grupo) {
        if (!existeGrupo(grupo)) {
            return null;
        }
        ArrayList<Estudiante> listaEstudiantesGrupo = new ArrayList<>();
        var asignaturaArrayListHashMap = registro.get(grupo);
        for (ArrayList<Estudiante> listaEstudiantesAsignatura : asignaturaArrayListHashMap.values()) {
            for (Estudiante estudiante : listaEstudiantesAsignatura) {
                if (!listaEstudiantesGrupo.contains(estudiante)) {
                    listaEstudiantesGrupo.add(estudiante);
                }
            }
        }
        return listaEstudiantesGrupo;
    }

    /**
     * Devuelve una lista con todas/os las/los estudiantes que cursan una asignatura independientemente
     * del grupo al que pertenezcan. Como un/a estudiante no estará matriculado/a en distintos grupos,
     * no puede estar en la misma asignatura en distintos grupos con distinta nota.
     * La nota de las/los estudiantes será la nota de esa asignatura.
     * @param asignatura
     * @return
     */
    public ArrayList<Estudiante> getEstudiantes(Asignatura asignatura) {
        ArrayList<Estudiante> lista = new ArrayList<>();
        for (HashMap<Asignatura, ArrayList<Estudiante>> asignaturaArrayListHashMap : registro.values()) {
            for (Map.Entry<Asignatura, ArrayList<Estudiante>> asignaturaArrayListEntry : asignaturaArrayListHashMap.entrySet()) {
                 if (asignaturaArrayListEntry.getKey().equals(asignatura)) {
                    ArrayList<Estudiante> listaEstudiantes = asignaturaArrayListEntry.getValue();
                    for (Estudiante estudiante : listaEstudiantes) {
                        if (!lista.contains(estudiante)) {
                            lista.add(estudiante);
                        }
                    }
                }
            }
        }
        if (lista.isEmpty()) {
            return  null;
        }
        return lista;
    }

    /**
     * Devuelve un mapa ordenado por estudiante, asociado al grupo al que pertenecen,
     * en el que aparecen las/los estudiantes que tienen una nota mayor o igual en la asignatura indicada.
     * @param asignatura
     * @param nota
     * @return
     */
    public TreeMap<Estudiante,Grupo> getEstudiantesConNotaMayorIgualQue(Asignatura asignatura, double nota) {
        TreeMap<Estudiante,Grupo> mapa = new TreeMap<>();
        ArrayList<Estudiante> listaEstudiantesAsignatura = getEstudiantes(asignatura);
        for (Estudiante estudiante : listaEstudiantesAsignatura) {
            if (estudiante.getNota() >= nota) {
                Grupo grupo = grupoDelEstudiante(estudiante.getNombre(), estudiante.getApellidos());
                mapa.put(estudiante, grupo);
            }
        }
        return mapa;
    }

    //endregion

    //region distribuciones notas 51-52

    /**
     * Devuelve un mapa en el que las claves son valores del 0 al 10 y los valores el número de personas que en esa
     * asignatura en ese grupo han obtenido esa nota. El sumatorio de todos los valores debe ser el número de estudiantes
     * que hay en la lista de estudiantes de la asignatura del grupo. Por ejemplo, si nadie tiene un 0 en PROG en DAW1,
     * el valor asociado a la clave 0 será 0. Si hay 5 personas que han sacado una nota entre 8 y 9 (sin incluir el 9),
     * el valor asociado a la clave 8 será 5.
     *
     *     Opción: 51
     *     Nombre del grupo: DAW1
     *     Nombre de la asignatura: PROG
     *     |  0|  1|  2|  3|  4|  5|  6|  7|  8|  9| 10|
     *     |---|---|---|---|---|---|---|---|---|---|---|
     *     |  0|  2|  2|  3|  1|  1|  1|  1|  5|  3|  0|
     *     |---|---|---|---|---|---|---|---|---|---|---|
     *
     * @param asignatura
     * @param grupo
     * @return
     */
    public TreeMap<Integer,Integer> getDistribucionNotasAsignaturaGrupo(Asignatura asignatura, Grupo grupo) {
        TreeMap<Integer, Integer> mapa = new TreeMap<>();
        for (int i = 0; i <= 10; i++) {
            mapa.put(i, 0);
        }
        if (!existeAsignaturaGrupo(asignatura, grupo)) {
            return mapa;
        }
        ArrayList<Estudiante> lista = getListaEstudiantesAsignaturaGrupo(asignatura, grupo);
        for (Estudiante estudiante : lista) {
            int nota = (int) estudiante.getNota();
            int cnt = mapa.get(nota);
            mapa.put(nota, cnt+1);
        }
        return mapa;
    }

    /**
     * Devuelve un mapa en el que las claves son valores del 0 al 10 y los valores el número de personas que en esa
     * asignatura considerando todos los grupos han obtenido esa nota.
     *
     *     Opción: 52
     *     Nombre de la asignatura: PROG
     *     |  0|  1|  2|  3|  4|  5|  6|  7|  8|  9| 10|
     *     |---|---|---|---|---|---|---|---|---|---|---|
     *     |  5|  8|  5|  4|  4|  7|  1|  4| 13|  7|  1|
     *     |---|---|---|---|---|---|---|---|---|---|---|
     *
     * @param asignatura
     * @return
     */
    public TreeMap<Integer,Integer> getDistribucionNotasAsignatura(Asignatura asignatura) {
        TreeMap<Integer, Integer> mapa = new TreeMap<>();
        for (int i = 0; i <= 10; i++) {
            mapa.put(i, 0);
        }
        for (Grupo grupo : getGrupos()) {
            TreeMap<Integer,Integer> mapaAsignaturaGrupo = getDistribucionNotasAsignaturaGrupo(asignatura, grupo);
            mapa = acumulaDistribucionesDeNotas(mapa, mapaAsignaturaGrupo);
        }
        return mapa;
    }

    private TreeMap<Integer,Integer> acumulaDistribucionesDeNotas(TreeMap<Integer,Integer> map1, TreeMap<Integer,Integer> map2) {
        TreeMap<Integer, Integer> mapa = new TreeMap<>();
        for (int i = 0; i <= 10; i++) {
            mapa.put(i, map1.get(i) + map2.get(i));
        }
        return mapa;
    }

    //endregion

    //region info estudiante 61-62

    /**
     * Devuelve el grupo al que pertenece el estudiante o null. Un/a estudiante sólo pertenece a un grupo,
     * si se encuentra una coincidencia se puede devolver esa.
     *
     *     Opción: 61
     *     Nombre del/a estudiante: Samantha
     *     Apellidos del/a estudiante: Oag
     *     La/El estudiante pertenece al grupo DAW1
     *
     * @param nombre
     * @param apellidos
     * @return
     */
    public Grupo grupoDelEstudiante(String nombre, String apellidos) {
        Estudiante estudiante = new Estudiante(nombre,apellidos);
        for (Map.Entry<Grupo, HashMap<Asignatura, ArrayList<Estudiante>>> grupoHashMapEntry : registro.entrySet()) {
            Grupo grupo = grupoHashMapEntry.getKey();
            var asignaturas = grupoHashMapEntry.getValue();
            for (ArrayList<Estudiante> estudiantes : asignaturas.values()) {
                if (estudiantes.contains(estudiante)) {
                    return grupo;
                }
            }
        }
        return null;
    }

    /**
     * Devuelve un mapa de asignaturas y notas obtenidas de un/a estudiante o null si no tiene.
     *
     *     Opción: 62
     *     Nombre del/a estudiante: Samantha
     *     Apellidos del/a estudiante: Oag
     *     LMGI : 5.89
     *     BADA : 5.45
     *     ING  : 6.74
     *     SIN  : 3.49
     *     ENDE : 8.90
     *     PROG : 3.70
     *
     * @param nombre
     * @param apellidos
     * @return
     */
    public HashMap<Asignatura, Double> notasEstudiante(String nombre, String apellidos) {
        HashMap<Asignatura, Double> notas = new HashMap<>();
        Grupo grupo = grupoDelEstudiante(nombre, apellidos);
        if (grupo==null) {
            return null;
        }
        Estudiante estudiante = new Estudiante(nombre, apellidos);
        for (Map.Entry<Asignatura, ArrayList<Estudiante>> asignaturaArrayListEntry : registro.get(grupo).entrySet()) {
            Asignatura asignatura = asignaturaArrayListEntry.getKey();
            ArrayList<Estudiante> lista = asignaturaArrayListEntry.getValue();
            if (lista.contains(estudiante)) {
                int i = lista.indexOf(estudiante);
                estudiante = lista.get(i);
                notas.put(asignatura, estudiante.getNota());
            }
        }
        return notas;
    }

    //endregion


    //region operaciones docentes 71-78

    /**
     * 71. Listado de docentes
     * @return
     */
    public ArrayList<Docente> getDocentes() {
        //TODO: 71. Listado de docentes DONE
        return new ArrayList<>(mapaDocentes.keySet());
    }

    /**
     * 72. Docentes que no imparten ninguna asignatura
     * @return
     */
    public TreeSet<Docente> getDocentesSinCarga() {
        //TODO: 72. Docentes que no imparten ninguna asignatura DONE
        TreeSet<Docente> listaDocentesSinCarga = new TreeSet<>();
        for (Map.Entry<Docente, TreeSet<GrupoAsignatura>> entry : mapaDocentes.entrySet()) {
            if (entry.getValue().isEmpty()) {
                listaDocentesSinCarga.add(entry.getKey());
            }
        }
        return listaDocentesSinCarga;
    }

    /**
     * 73. Docente que imparte en un grupo una asignatura
     * @param grupo
     * @param asignatura
     * @return
     */
    public Docente getDocenteGrupoAsignatura(Grupo grupo, Asignatura asignatura) {
        //TODO: 73. Docente que imparte en un grupo una asignatura DONE
        for (Map.Entry<Docente, TreeSet<GrupoAsignatura>> entry : mapaDocentes.entrySet()) {
            for (GrupoAsignatura grupoAsignatura : entry.getValue()) {
                if (grupoAsignatura.getGrupo().equals(grupo) && grupoAsignatura.getAsignatura().equals(asignatura)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    /**
     * 74. Docentes que imparten en un grupo
     * @param grupo
     * @return
     */
    public TreeSet<Docente> getDocentesGrupo(Grupo grupo) {
        //TODO: 74. Docentes que imparten en un grupo DONE
        TreeSet<Docente> listaDocentesGrupo = new TreeSet<>();
        for (Map.Entry<Docente, TreeSet<GrupoAsignatura>> entry : mapaDocentes.entrySet()) {
            for (GrupoAsignatura grupoAsignatura : entry.getValue()) {
                if (grupoAsignatura.getGrupo().equals(grupo)) {
                    listaDocentesGrupo.add(entry.getKey());
                }
            }
        }
        return listaDocentesGrupo;
    }

    /**
     * 75. Cuántos profesores hay en cada grupo
     * @return
     */
    public TreeMap<Grupo, Integer> getGruposNumeroDocentes() {
        //TODO: 75. Cuántos profesores hay en cada grupo DONE
        TreeMap<Grupo, Integer> listaDocentesCantidad = new TreeMap<>();
        ArrayList<Grupo> listaGrupos = new ArrayList<>(getGrupos());

        for (Grupo grupo : listaGrupos) {
            TreeSet<Docente> listaDocentesGrupo = new TreeSet<>(getDocentesGrupo(grupo));
            int numero = listaDocentesGrupo.size();
            listaDocentesCantidad.put(grupo, numero);
        }

        return listaDocentesCantidad;
    }

    /**
     * 76. Docentes que imparten una asignatura
     * @param grupo
     * @return
     */
    public TreeMap<Asignatura, Docente> getAsignaturasDocentesGrupo(Grupo grupo) {
        //TODO: 76. Docentes que imparten una asignatura
        TreeMap<Asignatura, Docente> listaAsignaturasDocentes = new TreeMap<>();
        HashSet<Asignatura> listaAsignaturasGrupo = getAsignaturas(grupo);

        for (Asignatura asignatura : listaAsignaturasGrupo) {
            for (Map.Entry<Docente, TreeSet<GrupoAsignatura>> entry : mapaDocentes.entrySet()) {
                for (GrupoAsignatura grupoAsignatura : entry.getValue()) {
                    if (grupoAsignatura.getAsignatura().equals(asignatura) && grupoAsignatura.getGrupo().equals(grupo)) {
                        listaAsignaturasDocentes.put(asignatura, entry.getKey());
                    }
                }
            }
        }
        return listaAsignaturasDocentes;
    }

    /**
     * 77. Grupos comunes en los que imparten dos docentes
     * @param docente1
     * @param docente2
     * @return
     */
    public TreeSet<Grupo> getGruposComunes(Docente docente1, Docente docente2) {
        //TODO: 77. Grupos comunes en los que imparten dos docentes
        return null;
    }

    /**
     * 78. Listado con los grupos, las asignaturas y los docentes
     * @return
     */
    public TreeMap<Grupo, TreeMap<Asignatura, Docente>> getGruposAsignaturasDocentes() {
        //TODO: 78. Listado con los grupos, las asignaturas y los docentes
        return null;
    }

    //endregion

}

