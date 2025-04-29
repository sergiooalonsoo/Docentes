package com.masanz.gda;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Menu {

    private final Gestor gestor;

    public static void main(String[] args) {
        String fileNotas = null;
        String fileDocentes = null;
        if (args.length >= 2) {
            if (args[0].equals("-n")) fileNotas = args[1];
            if (args[0].equals("-d")) fileDocentes = args[1];
        }
        if (args.length == 4) {
            if (args[2].equals("-n")) fileNotas = args[3];
            if (args[2].equals("-d")) fileDocentes = args[3];
        }
        if (fileNotas==null) {
            fileNotas = "notas.csv";
        }
        if (fileDocentes==null) {
            fileDocentes = "docentes.csv";
        }
        Gestor gestor = new Gestor();
        Cargador.cargaNotas(gestor, fileNotas);
        Cargador.cargaDocentes(gestor, fileDocentes);
        Menu menu = new Menu(gestor);
        menu.run();
    }

    public Menu(Gestor gestor) {
        this.gestor = gestor;
    }

    public void run() {
        Salida.mostrarMenu();
        EMenu opcion = Entrada.leerOpcionMenu("Opción");
        while (opcion != EMenu.SALIR) {
            switch (opcion) {

                case EXISTE_GRUPO -> existeGrupo();
                case ANADIR_GRUPO -> anadirGrupo();
                case LISTAR_GRUPOS -> listarGrupos();
                case BORRAR_GRUPO -> borrarGrupo();

                case EXISTE_ASIGNATURA_GRUPO -> existeAsignatura();
                case ANADIR_ASIGNATURA_GRUPO -> anadirAsignatura();
                case LISTAR_ASIGNATURAS_GRUPO -> listarAsignaturasGrupo();
                case BORRAR_ASIGNATURA_GRUPO -> borrarAsignatura();
                case LISTAR_ASIGNATURAS_TODAS -> listarAsignaturasTodas();

                case LISTAR_ESTUDIANTES_ASIGNATURA_GRUPO -> listarEstudiantesAsignaturaGrupo();
                case EXISTE_ESTUDIANTE_ASIGNATURA_GRUPO -> existeEstudianteAsignaturaGrupo();
                case CONSULTAR_ESTUDIANTE_ASIGNATURA_GRUPO -> consultarEstudianteAsignaturaGrupo();
                case ANADIR_ESTUDIANTE_ASIGNATURA_GRUPO -> anadirEstudianteAsignaturaGrupo();
                case BORRAR_ESTUDIANTE_ASIGNATURA_GRUPO -> borrarEstudianteAsignaturaGrupo();

                case LISTAR_ESTUDIANTES_GRUPO -> listarEstudiantesGrupo();
                case LISTAR_ESTUDIANTES_ASIGNATURA -> listarEstudiantesAsignatura();
                case LISTAR_ESTUDIANTES_NOTA -> listarEstudiantesNota();

                case DISTRIBUCION_NOTAS_ASIGNATURA_GRUPO -> distribucionNotasAsignaturaGrupo();
                case DISTRIBUCION_NOTAS_ASIGNATURA -> distribucionNotasAsignatura();

                case GRUPO_ALUMNO -> grupoAlumno();
                case NOTAS_ALUMNO -> notasAlumno();

                case LISTAR_DOCENTES -> listarDocentes();
                case LISTAR_DOCENTES_SIN_CARGA -> listarDocentesSinCarga();
                case BUSCAR_DOCENTE_GRUPO_ASIGNATURA -> buscarDocenteGrupoAsignatura();
                case LISTAR_DOCENTES_GRUPO -> listarDocentesGrupo();
                case LISTAR_GRUPOS_NUMERO_DOCENTES -> listarGruposNumeroDocentes();
                case LISTAR_DOCENTES_ASIGNATURAS_GRUPO -> listarDocentesAsignaturasGrupo();
                case GRUPOS_COMUNES_DOS_DOCENTES -> gruposComunesDosDocentes();
                case LISTAR_GRUPOS_ASIGNATURA_DOCENTE -> listarGruposAsignaturaDocente();

                default -> { }
            }
            if (!Entrada.leerConfirmacion("Continuar")) {
                break;
            }
//            Salida.mostrarMenu();
            opcion = Entrada.leerOpcionMenu("Opción");
        }
    }

    //region operaciones grupo 11-14
    private void existeGrupo() {
        String nombre = Entrada.leerString("Nombre del grupo");
        Grupo grupo = new Grupo(nombre);
        if (gestor.existeGrupo(grupo)) {
            Salida.info("El grupo existe");
        }else{
            Salida.info("El grupo no existe");
        }
    }

    private void anadirGrupo() {
        String nombre = Entrada.leerString("Nombre del grupo");
        Grupo grupo = new Grupo(nombre);
        if (gestor.existeGrupo(grupo)){
            Salida.info("El grupo ya existe");
        }else{
            gestor.anadirGrupo(grupo);
            Salida.info("Grupo añadido.");
        }
    }

    private void listarGrupos() {
        Salida.listar(Arrays.stream(gestor.getGrupos().toArray()).map(Object::toString).toArray(String[]::new));
    }

    private void borrarGrupo() {
        String nombre = Entrada.leerString("Nombre del grupo");
        Grupo grupo = new Grupo(nombre);
        if (!gestor.existeGrupo(grupo)) {
            Salida.info("El grupo no existe");
        }else{
            gestor.borrarGrupo(grupo);
            Salida.info("Grupo borrado.");
        }
    }

    //endregion

    //region operaciones asignatura 21-25
    private void existeAsignatura() {
        String nombreGrupo = Entrada.leerString("Nombre del grupo");
        Grupo grupo = new Grupo(nombreGrupo);
        String nombreAsignatura = Entrada.leerString("Nombre de la asignatura");
        Asignatura asignatura = new Asignatura(nombreAsignatura);
        if (gestor.existeAsignaturaGrupo(asignatura, grupo)) {
            Salida.info("La asignatura existe");
        }else{
            Salida.info("La asignatura no existe");
        }
    }

    private void anadirAsignatura() {
        String nombreGrupo = Entrada.leerString("Nombre del grupo");
        Grupo grupo = new Grupo(nombreGrupo);
        String nombreAsignatura = Entrada.leerString("Nombre de la asignatura");
        Asignatura asignatura = new Asignatura(nombreAsignatura);
        if (!gestor.existeGrupo(grupo)){
            Salida.info("El grupo no existe, créalo primero.");
        }else if (gestor.existeAsignaturaGrupo(asignatura, grupo)) {
            Salida.info("La asignatura ya existe");
        }else{
            gestor.anadirAsignaturaGrupo(asignatura, grupo);
            Salida.info("Asignatura añadida.");
        }
    }

    private void listarAsignaturasGrupo() {
        String nombreGrupo = Entrada.leerString("Nombre del grupo");
        Grupo grupo = new Grupo(nombreGrupo);
        if (!gestor.existeGrupo(grupo)) {
            Salida.info("El grupo no existe, créalo primero.");
        }else {
            Salida.listar(Arrays.stream(gestor.getAsignaturas(grupo).toArray()).map(Object::toString).toArray(String[]::new));
        }
    }

    private void borrarAsignatura() {
        String nombreGrupo = Entrada.leerString("Nombre del grupo");
        Grupo grupo = new Grupo(nombreGrupo);
        String nombreAsignatura = Entrada.leerString("Nombre de la asignatura");
        Asignatura asignatura = new Asignatura(nombreAsignatura);
        if (!gestor.existeGrupo(grupo)){
            Salida.info("El grupo no existe.");
        }else if (!gestor.existeAsignaturaGrupo(asignatura, grupo)) {
            Salida.info("No existe esa asignatura en ese grupo");
        }else{
            gestor.borrarAsignaturaGrupo(asignatura, grupo);
            Salida.info("Asignatura borrada.");
        }
    }

    private void listarAsignaturasTodas() {
        Salida.listar(Arrays.stream(gestor.getAsignaturas().toArray()).map(Object::toString).toArray(String[]::new));
    }

    //endregion

    //region operaciones estudiante 31-35

    private void listarEstudiantesAsignaturaGrupo() {
        String nombreGrupo = Entrada.leerString("Nombre del grupo");
        Grupo grupo = new Grupo(nombreGrupo);
        String nombreAsignatura = Entrada.leerString("Nombre de la asignatura");
        Asignatura asignatura = new Asignatura(nombreAsignatura);
        if (!gestor.existeGrupo(grupo)) {
            Salida.info("El grupo no existe");
        }else if (!gestor.existeAsignaturaGrupo(asignatura, grupo)) {
            Salida.info("La asignatura no existe");
        }else {
            Salida.listar(Arrays.stream(gestor.getListaEstudiantesAsignaturaGrupo(asignatura,grupo).toArray()).map(Object::toString).toArray(String[]::new));
        }
    }

    private void existeEstudianteAsignaturaGrupo() {
        String nombreGrupo = Entrada.leerString("Nombre del grupo");
        Grupo grupo = new Grupo(nombreGrupo);
        String nombreAsignatura = Entrada.leerString("Nombre de la asignatura");
        Asignatura asignatura = new Asignatura(nombreAsignatura);
        String nombreEstudiante = Entrada.leerString("Nombre del/a estudiante");
        String apellidosEstudiante = Entrada.leerString("Apellidos del/a estudiante");
        Estudiante estudiante = new Estudiante(nombreEstudiante, apellidosEstudiante);
        if (!gestor.existeGrupo(grupo)) {
            Salida.info("El grupo no existe");
        }else if (!gestor.existeAsignaturaGrupo(asignatura, grupo)) {
            Salida.info("La asignatura no existe");
        }else if (!gestor.existeEstudianteAsignaturaGrupo(estudiante, asignatura, grupo)) {
            Salida.info("La/El estudiante no existe");
        }else {
            Salida.info("La/El estudiante existe");
        }
    }

    private void consultarEstudianteAsignaturaGrupo() {
        String nombreGrupo = Entrada.leerString("Nombre del grupo");
        Grupo grupo = new Grupo(nombreGrupo);
        String nombreAsignatura = Entrada.leerString("Nombre de la asignatura");
        Asignatura asignatura = new Asignatura(nombreAsignatura);
        String nombreEstudiante = Entrada.leerString("Nombre del/a estudiante");
        String apellidosEstudiante = Entrada.leerString("Apellidos del/a estudiante");
        Estudiante estudiante = new Estudiante(nombreEstudiante, apellidosEstudiante);
        if (!gestor.existeGrupo(grupo)) {
            Salida.info("El grupo no existe");
        }else if (!gestor.existeAsignaturaGrupo(asignatura, grupo)) {
            Salida.info("La asignatura no existe");
        }else if (!gestor.existeEstudianteAsignaturaGrupo(estudiante, asignatura, grupo)) {
            Salida.info("La/El estudiante no existe");
        }else {
            var e = gestor.getEstudianteAsignaturaGrupo(estudiante, asignatura, grupo);
            Salida.info(e.toString());
        }
    }

    private void anadirEstudianteAsignaturaGrupo() {
        String nombreGrupo = Entrada.leerString("Nombre del grupo");
        Grupo grupo = new Grupo(nombreGrupo);
        String nombreAsignatura = Entrada.leerString("Nombre de la asignatura");
        Asignatura asignatura = new Asignatura(nombreAsignatura);
        String nombreEstudiante = Entrada.leerString("Nombre del/a estudiante");
        String apellidosEstudiante = Entrada.leerString("Apellidos del/a estudiante");
        Estudiante estudiante = new Estudiante(nombreEstudiante, apellidosEstudiante);
        if (!gestor.existeGrupo(grupo)) {
            Salida.info("El grupo no existe");
        }else if (!gestor.existeAsignaturaGrupo(asignatura, grupo)) {
            Salida.info("La asignatura no existe");
        }else if (gestor.existeEstudianteAsignaturaGrupo(estudiante, asignatura, grupo)) {
            Salida.info("La/El estudiante existe");
        }else {
            gestor.anadirEstudianteAsignaturaGrupo(estudiante, asignatura, grupo);
            Salida.info("Estudiante añadida o añadido.");
        }
    }

    private void borrarEstudianteAsignaturaGrupo() {
        String nombreGrupo = Entrada.leerString("Nombre del grupo");
        Grupo grupo = new Grupo(nombreGrupo);
        String nombreAsignatura = Entrada.leerString("Nombre de la asignatura");
        Asignatura asignatura = new Asignatura(nombreAsignatura);
        String nombreEstudiante = Entrada.leerString("Nombre del/a estudiante");
        String apellidosEstudiante = Entrada.leerString("Apellidos del/a estudiante");
        Estudiante estudiante = new Estudiante(nombreEstudiante, apellidosEstudiante);
        if (!gestor.existeGrupo(grupo)) {
            Salida.info("El grupo no existe");
        }else if (!gestor.existeAsignaturaGrupo(asignatura, grupo)) {
            Salida.info("La asignatura no existe");
        }else if (!gestor.existeEstudianteAsignaturaGrupo(estudiante, asignatura, grupo)) {
            Salida.info("La/El estudiante no existe");
        }else {
            gestor.borrarEstudianteAsignaturaGrupo(estudiante, asignatura, grupo);
            Salida.info("Estudiante borrado o borrada.");
        }
    }

    //endregion

    //region listados estudiantes 41-43

    private void listarEstudiantesGrupo() {
        String nombreGrupo = Entrada.leerString("Nombre del grupo");
        Grupo grupo = new Grupo(nombreGrupo);
        if (!gestor.existeGrupo(grupo)) {
            Salida.info("El grupo no existe");
        }else {
            Salida.listar(gestor.getEstudiantes(grupo).stream().map(e -> e.getApellidosNombre()).toArray(String[]::new));
        }
    }

    private void listarEstudiantesAsignatura() {
        String nombreAsignatura = Entrada.leerString("Asignatura");
        Asignatura asignatura = new Asignatura(nombreAsignatura);
        Salida.listar(gestor.getEstudiantes(asignatura).stream().map(e -> e.toString()).toArray(String[]::new));
    }

    private void listarEstudiantesNota() {
        String nombreAsignatura = Entrada.leerString("Asignatura");
        Asignatura asignatura = new Asignatura(nombreAsignatura);
        double nota = Entrada.leerDoublePositivo("Nota");
        var mapa = gestor.getEstudiantesConNotaMayorIgualQue(asignatura, nota);
        String[] keys = Arrays.stream(mapa.keySet().toArray()).map(Object::toString).toArray(String[]::new);
        String[] values = Arrays.stream(mapa.values().toArray()).map(Object::toString).toArray(String[]::new);
        Salida.listarVgrupo(keys, values);
    }

    //endregion

    //region distribuciones notas 51-52

    private void distribucionNotasAsignaturaGrupo() {
        String nombreGrupo = Entrada.leerString("Nombre del grupo");
        Grupo grupo = new Grupo(nombreGrupo);
        String nombreAsignatura = Entrada.leerString("Nombre de la asignatura");
        Asignatura asignatura = new Asignatura(nombreAsignatura);
        if (!gestor.existeGrupo(grupo)) {
            Salida.info("El grupo no existe");
        }else if (!gestor.existeAsignaturaGrupo(asignatura, grupo)) {
            Salida.info("La asignatura no existe");
        }else {
            var res =gestor.getDistribucionNotasAsignaturaGrupo(asignatura, grupo);
            String[] keys = Arrays.stream(res.keySet().toArray()).map(Object::toString).toArray(String[]::new);
            String[] values = Arrays.stream(res.values().toArray()).map(Object::toString).toArray(String[]::new);
            Salida.listarH(keys, values);
        }
    }

    private void distribucionNotasAsignatura() {
        String nombreAsignatura = Entrada.leerString("Nombre de la asignatura");
        Asignatura asignatura = new Asignatura(nombreAsignatura);
        var res = gestor.getDistribucionNotasAsignatura(asignatura);
        String[] keys = Arrays.stream(res.keySet().toArray()).map(Object::toString).toArray(String[]::new);
        String[] values = Arrays.stream(res.values().toArray()).map(Object::toString).toArray(String[]::new);
        Salida.listarH(keys, values);
    }

    //endregion

    //region info estudiante 61-62

    private void grupoAlumno() {
        String nombreEstudiante = Entrada.leerString("Nombre del/a estudiante");
        String apellidosEstudiante = Entrada.leerString("Apellidos del/a estudiante");
        Grupo grupo = gestor.grupoDelEstudiante(nombreEstudiante, apellidosEstudiante);
        if (grupo==null) {
            Salida.info("La/El estudiante no existe en ningún grupo.");
        }else {
            Salida.info("La/El estudiante pertenece al grupo " + grupo);
        }
    }

    private void notasAlumno() {
        String nombreEstudiante = Entrada.leerString("Nombre del/a estudiante");
        String apellidosEstudiante = Entrada.leerString("Apellidos del/a estudiante");
        if (gestor.grupoDelEstudiante(nombreEstudiante, apellidosEstudiante)==null) {
            Salida.info("La/El estudiante no existe en ningún grupo.");
        }else {
            var res = gestor.notasEstudiante(nombreEstudiante,apellidosEstudiante);
            String[] keys = Arrays.stream(res.keySet().toArray()).map(Object::toString).toArray(String[]::new);
            String[] values = Arrays.stream(res.values().toArray()).map(Object::toString).toArray(String[]::new);
            Salida.listarVnota(keys, values);
        }
    }

    //endregion

    //region operaciones docentes 71-78

    private void listarDocentes() {
        var res = gestor.getDocentes();
        Salida.listar(Arrays.stream(res.toArray()).map(Object::toString).toArray(String[]::new));
    }

    private void listarDocentesSinCarga() {
        var res = gestor.getDocentesSinCarga();
        Salida.listar(Arrays.stream(res.toArray()).map(Object::toString).toArray(String[]::new));
    }

    private void buscarDocenteGrupoAsignatura() {
        String nombreGrupo = Entrada.leerString("Nombre del grupo");
        String nombreAsignatura = Entrada.leerString("Nombre de la asignatura");
        var docente = gestor.getDocenteGrupoAsignatura(new Grupo(nombreGrupo), new Asignatura(nombreAsignatura));
        if (docente==null){
            Salida.info("No existe ese grupo o asignatura o no tiene docente.");
        }else{
            Salida.info(docente.toString());
        }
    }

    private void listarDocentesGrupo() {
        String nombreGrupo = Entrada.leerString("Nombre del grupo");
        var docentes = gestor.getDocentesGrupo(new Grupo(nombreGrupo));
        if (docentes.isEmpty()){
            Salida.info("Ese grupo no tiene docentes o no existe.");
        }else {
            Salida.listar(Arrays.stream(docentes.toArray()).map(Object::toString).toArray(String[]::new));
        }
    }

    private void listarGruposNumeroDocentes() {
        var res = gestor.getGruposNumeroDocentes();
        String[] keys = Arrays.stream(res.keySet().toArray()).map(Object::toString).toArray(String[]::new);
        String[] values = Arrays.stream(res.values().toArray()).map(Object::toString).toArray(String[]::new);
        Salida.listarVnota(keys, values);
    }

    private void listarDocentesAsignaturasGrupo() {
        String nombreGrupo = Entrada.leerString("Nombre del grupo");
        var res = gestor.getAsignaturasDocentesGrupo(new Grupo(nombreGrupo));
        String[] keys = Arrays.stream(res.keySet().toArray()).map(Object::toString).toArray(String[]::new);
        String[] values = Arrays.stream(res.values().toArray()).map(Object::toString).toArray(String[]::new);
        Salida.listarVnota(keys, values);
    }

    private void gruposComunesDosDocentes() {
        var docentes = gestor.getDocentes();
        Salida.listar(Arrays.stream(docentes.toArray()).map(Object::toString).toArray(String[]::new));
        int i = Entrada.leerNumero("Número del primer docente", 1, docentes.size());
        int j = Entrada.leerNumero("Número del segundo docente", 1, docentes.size());
        Docente docente1 = docentes.get(i-1);
        Docente docente2 = docentes.get(j-1);
        var res = gestor.getGruposComunes(docente1, docente2);
        Salida.listar(Arrays.stream(res.toArray()).map(Object::toString).toArray(String[]::new));
    }

    private void listarGruposAsignaturaDocente() {
        var res = gestor.getGruposAsignaturasDocentes();
        int m = 0;
        for (TreeMap<Asignatura, Docente> ads : res.values()) {
            m += ads.size();
        }
        String[][] t = new String [m][3];
        int i = 0;
        int j = 0;
        for (Map.Entry<Grupo, TreeMap<Asignatura, Docente>> entry : res.entrySet()) {
            j = i;
            var ads = entry.getValue();
            for (Map.Entry<Asignatura, Docente> ad : ads.entrySet()) {
                t[i][0] = "";
                t[i][1] = ad.getKey().toString();
                t[i][2] = ad.getValue().toString();
                i++;
            }
            t[j][0] = entry.getKey().toString();
        }
        Salida.mostrar(t, new int[]{-8, -8, -25});
    }

    //endregion

}
