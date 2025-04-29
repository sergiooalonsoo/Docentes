package com.masanz.gda;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Data1GestorTest {
    Gestor gestor;

    @BeforeEach
    void setUp() {
        gestor = new Gestor();
        Cargador.cargaNotas(gestor, "data1.csv");
    }

    //region tests operaciones grupo 11-14
    @Test
    @Order(11)
    void existeGrupo() {
        assertTrue(gestor.existeGrupo(new Grupo("DAM1A")));
        assertTrue(gestor.existeGrupo(new Grupo("DAW1")));
        assertFalse(gestor.existeGrupo(new Grupo("DAM1")));
        assertFalse(gestor.existeGrupo(new Grupo("DAW1A")));
    }

    @Test
    @Order(12)
    void anadirGrupo() {
        gestor.anadirGrupo(new Grupo("DAW1"));
        gestor.anadirGrupo(new Grupo("OTRO"));
        assertTrue(gestor.existeGrupo(new Grupo("DAW1")));
        assertTrue(gestor.existeGrupo(new Grupo("OTRO")));
        assertFalse(gestor.existeGrupo(new Grupo("DAM1")));
    }

    @Test
    @Order(13)
    void getGrupos() {
        var grupos = gestor.getGrupos();
        assertTrue(grupos.toString().equals("[ASIR1A, ASIR1B, DAM1A, DAM1B, DAW1]"));
    }

    @Test
    @Order(14)
    void borrarGrupo() {
        gestor.borrarGrupo(new Grupo("ASIR1A"));
        gestor.borrarGrupo(new Grupo("DAM1A"));
        gestor.borrarGrupo(new Grupo("DAM1A"));
        var grupos = new TreeSet<>(gestor.getRegistro().keySet());
        assertTrue(grupos.toString().equals("[ASIR1B, DAM1B, DAW1]"));
    }
    //endregion

    //region tests operaciones asignatura 21-25
    @Test
    @Order(21)
    void existeAsignaturaGrupo() {
        assertTrue(gestor.existeAsignaturaGrupo(new Asignatura("PROG"),new Grupo("DAM1A")));
        assertTrue(gestor.existeAsignaturaGrupo(new Asignatura("PROG"),new Grupo("DAW1")));
        assertFalse(gestor.existeAsignaturaGrupo(new Asignatura("PROG"),new Grupo("ASIR1A")));
        assertFalse(gestor.existeAsignaturaGrupo(new Asignatura("PROG"),new Grupo("OTRO")));
    }

    @Test
    @Order(22)
    void anadirAsignaturaGrupo() {
        gestor.anadirAsignaturaGrupo(new Asignatura("PROG"),new Grupo("DAM1A"));
        gestor.anadirAsignaturaGrupo(new Asignatura("PROG"),new Grupo("ASIR1A"));
        gestor.anadirAsignaturaGrupo(new Asignatura("PROG"),new Grupo("OTRO"));
        assertTrue(gestor.existeAsignaturaGrupo(new Asignatura("PROG"),new Grupo("DAM1A")));
        assertTrue(gestor.existeAsignaturaGrupo(new Asignatura("PROG"),new Grupo("ASIR1A")));
        assertTrue(gestor.existeAsignaturaGrupo(new Asignatura("PROG"),new Grupo("OTRO")));
        assertFalse(gestor.existeAsignaturaGrupo(new Asignatura("FUHA"),new Grupo("DAM1A")));
    }

    @Test
    @Order(23)
    void getAsignaturasGrupo() {
        var asignaturasDAW1 = gestor.getAsignaturas(new Grupo("DAW1"));
        assertEquals(7, asignaturasDAW1.size(), "[LMGI, FOL, BADA, ING, SIN, ENDE, PROG]");
        var asignaturasASIR1A = gestor.getAsignaturas(new Grupo("ASIR1A"));
        assertEquals(7, asignaturasASIR1A.size(), "[LMGI, PARE, ADSO, FOL, IMSO, ING, FUHA]");
        var asignaturasComunes = (HashSet<Asignatura>)asignaturasDAW1.clone();
        asignaturasComunes.retainAll(asignaturasASIR1A);
        assertEquals(3, asignaturasComunes.size(), "[LMGI, FOL, ING]");
    }

    @Test
    @Order(24)
    void getAsignaturasAll() {
        var asignaturas = gestor.getAsignaturas();
        assertEquals(11, asignaturas.size(), "[LMGI, PARE, ADSO, FOL, IMSO, BADA, ING, FUHA, SIN, ENDE, PROG]");
    }

    @Test
    @Order(25)
    void borrarAsignaturaGrupo() {
        var asir1a = new Grupo("ASIR1A");
        gestor.borrarAsignaturaGrupo(new Asignatura("LMGI"), asir1a);
        gestor.borrarAsignaturaGrupo(new Asignatura("FOL"), asir1a);
        gestor.borrarAsignaturaGrupo(new Asignatura("IMSO"), asir1a);
        gestor.borrarAsignaturaGrupo(new Asignatura("PROG"), asir1a);
        gestor.borrarAsignaturaGrupo(new Asignatura("FOL"), asir1a);
        var asignaturasASIR1A = new HashSet<>(gestor.getRegistro().get(new Grupo("ASIR1A")).keySet());
        assertEquals(4, asignaturasASIR1A.size(), "[PARE, ADSO, ING, FUHA]");
    }

    //endregion

    //region tests operaciones estudiante 31-35

    @Test
    @Order(31)
    void getListaEstudiantesAsignaturaGrupo() {
        var estudiantesLista = gestor.getListaEstudiantesAsignaturaGrupo(new Asignatura("ING"), new Grupo("DAM1B"));
        assertEquals(15, estudiantesLista.size());
        var listaNula = gestor.getListaEstudiantesAsignaturaGrupo(new Asignatura("ING2"), new Grupo("DAM1B"));
        assertNull(listaNula);
        listaNula = gestor.getListaEstudiantesAsignaturaGrupo(new Asignatura("ING"), new Grupo("OTRO"));
        assertNull(listaNula);
    }

    @Test
    @Order(32)
    void existeEstudianteAsignaturaGrupo() {
        boolean b;
        var e1 = new Estudiante("Samantha","Oag");
        var a1 = new Asignatura("LMGI");
        var a2 = new Asignatura("FOL");
        var g1 = new Grupo("DAW1");
        var g2 = new Grupo("OTRO");
        b = gestor.existeEstudianteAsignaturaGrupo(e1, a1, g1);
        assertTrue(b);
        b = gestor.existeEstudianteAsignaturaGrupo(e1, a2, g1);
        assertFalse(b);
        b = gestor.existeEstudianteAsignaturaGrupo(e1, a1, g2);
        assertFalse(b);
    }

    @Test
    @Order(33)
    void consultarEstudianteAsignaturaGrupo() {
        Estudiante e;
        var e1 = new Estudiante("Samantha","Oag");
        var a1 = new Asignatura("LMGI");
        var a2 = new Asignatura("ING");
        var a3 = new Asignatura("FOL");
        var g1 = new Grupo("DAW1");
        var g2 = new Grupo("OTRO");
        e = gestor.getEstudianteAsignaturaGrupo(e1, a1, g1);
        assertEquals(5.89, e.getNota());//LMGI
        e = gestor.getEstudianteAsignaturaGrupo(e1, a2, g1);
        assertEquals(6.74, e.getNota());//ING
        e = gestor.getEstudianteAsignaturaGrupo(e1, a3, g1);
        assertNull(e);//FOL
        e = gestor.getEstudianteAsignaturaGrupo(e1, a1, g2);
        assertNull(e);
    }

    @Test
    @Order(34)
    void anadirEstudianteAsignaturaGrupo() {
        int i;
        Estudiante e;
        ArrayList<Estudiante> lista;
        var e1 = new Estudiante("Samantha","Oag", 10);
        var a1 = new Asignatura("LMGI");
        var a2 = new Asignatura("FOL");
        var g1 = new Grupo("DAW1");
        var g2 = new Grupo("OTRO");
        gestor.anadirEstudianteAsignaturaGrupo(e1, a1, g1);
        lista = gestor.getRegistro().get(g1).get(a1);
        i = lista.indexOf(e1);
        e = lista.get(i);
        assertEquals(10, e.getNota());
        gestor.anadirEstudianteAsignaturaGrupo(e1, a2, g1);
        lista = gestor.getRegistro().get(g1).get(a2);
        i = lista.indexOf(e1);
        e = lista.get(i);
        assertEquals(10, e.getNota());
        gestor.anadirEstudianteAsignaturaGrupo(e1, a1, g2);
        lista = gestor.getRegistro().get(g2).get(a1);
        i = lista.indexOf(e1);
        e = lista.get(i);
        assertEquals(10, e.getNota());
    }

    @Test
    @Order(35)
    void borrarEstudianteAsignaturaGrupo() {
        var e1 = new Estudiante("Samantha","Oag");
        var e2 = new Estudiante("West","Caillou");
        var a1 = new Asignatura("LMGI");
        var a2 = new Asignatura("FOL");
        var g1 = new Grupo("DAW1");
        assertTrue(gestor.existeEstudianteAsignaturaGrupo(e1,a1,g1));
        gestor.borrarEstudianteAsignaturaGrupo(e1,a1,g1);
        assertFalse(gestor.existeEstudianteAsignaturaGrupo(e1,a1,g1));
        assertTrue(gestor.existeEstudianteAsignaturaGrupo(e2,a1,g1));
        gestor.borrarEstudianteAsignaturaGrupo(e2,a1,g1);
        assertFalse(gestor.existeEstudianteAsignaturaGrupo(e2,a1,g1));
        assertFalse(gestor.existeEstudianteAsignaturaGrupo(e1,a2,g1));
        gestor.borrarEstudianteAsignaturaGrupo(e1,a2,g1);
        assertFalse(gestor.existeEstudianteAsignaturaGrupo(e1,a2,g1));
    }

    //endregion

    //region listados estudiantes 41-43

    @Test
    @Order(41)
    void getEstudiantesGrupo() {
        Grupo grupo;
        ArrayList<Estudiante> listaEstudiantes;
        grupo = new Grupo("DAW1");
        listaEstudiantes = gestor.getEstudiantes(grupo);
        assertEquals(19,listaEstudiantes.size());
        grupo = new Grupo("DAM1A");
        listaEstudiantes = gestor.getEstudiantes(grupo);
        assertEquals(23,listaEstudiantes.size());
        grupo = new Grupo("OTRO");
        listaEstudiantes = gestor.getEstudiantes(grupo);
        assertNull(listaEstudiantes);
    }

    @Test
    @Order(42)
    void getEstudiantesAsignatura() {
        Asignatura asignatura;
        ArrayList<Estudiante> listaEstudiantes;
        asignatura = new Asignatura("PROG");
        listaEstudiantes = gestor.getEstudiantes(asignatura);
        assertEquals(59,listaEstudiantes.size());
        asignatura = new Asignatura("LMGI");
        listaEstudiantes = gestor.getEstudiantes(asignatura);
        assertEquals(100,listaEstudiantes.size());
        asignatura = new Asignatura("OTRO");
        listaEstudiantes = gestor.getEstudiantes(asignatura);
        assertNull(listaEstudiantes);
    }

    @Test
    @Order(43)
    void getEstudiantesConNotaMayorIgualQue() {
        Asignatura asignatura;
        Set<Estudiante> estudiantes;
        TreeSet<Grupo> grupos;
        TreeMap<Estudiante,Grupo> mapa;
        asignatura = new Asignatura("PROG");
        mapa = gestor.getEstudiantesConNotaMayorIgualQue(asignatura, 8);
        estudiantes = mapa.keySet();
        grupos =  new TreeSet<Grupo>(mapa.values());
        assertEquals(21,estudiantes.size());
        assertEquals("[DAM1A, DAM1B, DAW1]", grupos.toString());
        asignatura = new Asignatura("LMGI");
        mapa = gestor.getEstudiantesConNotaMayorIgualQue(asignatura, 9.5);
        estudiantes = mapa.keySet();
        grupos =  new TreeSet<Grupo>(mapa.values());
        assertEquals(6,estudiantes.size());
        assertEquals("[ASIR1A, DAM1A, DAM1B, DAW1]", grupos.toString());
    }

    //endregion

    //region distribuciones notas 51-52

    @Test
    @Order(51)
    void getDistribucionNotasAsignaturaGrupo() {
        int x;
        Asignatura asignatura;
        Grupo grupo;
        TreeMap<Integer,Integer> distribucion;
        grupo = new Grupo("DAW1");
        asignatura = new Asignatura("PROG");
        distribucion = gestor.getDistribucionNotasAsignaturaGrupo(asignatura, grupo);
        x = distribucion.values().stream().mapToInt(Integer::intValue).sum();
        assertEquals(19,x);
        asignatura = new Asignatura("FOL");
        distribucion = gestor.getDistribucionNotasAsignaturaGrupo(asignatura, grupo);
        x = distribucion.values().stream().mapToInt(Integer::intValue).sum();
        assertEquals(16,x);
    }

    @Test
    @Order(52)
    void getDistribucionNotasAsignatura() {
        int x;
        Asignatura asignatura;
        TreeMap<Integer,Integer> distribucion;
        ArrayList<Integer> values;
        asignatura = new Asignatura("PROG");
        distribucion = gestor.getDistribucionNotasAsignatura(asignatura);
        x = distribucion.values().stream().mapToInt(Integer::intValue).sum();
        assertEquals(59,x);
        asignatura = new Asignatura("FOL");
        distribucion = gestor.getDistribucionNotasAsignatura(asignatura);
        x = distribucion.values().stream().mapToInt(Integer::intValue).sum();
        assertEquals(80,x);
    }

    //endregion

    //region info estudiante 61-62

    @Test
    @Order(61)
    void grupoDelEstudiante() {
        Grupo grupo;
        grupo = gestor.grupoDelEstudiante("Samantha","Oag");
        assertEquals("DAW1", grupo.toString());
        grupo = gestor.grupoDelEstudiante("West","Caillou");
        assertEquals("DAW1", grupo.toString());
        grupo = gestor.grupoDelEstudiante("Bel", "Wilsee");
        assertEquals("ASIR1B", grupo.toString());
        grupo = gestor.grupoDelEstudiante("Kasey", "Newsham");
        assertEquals("ASIR1A", grupo.toString());
        grupo = gestor.grupoDelEstudiante("Aitor", "E");
        assertNull(grupo);
    }

    @Test
    @Order(62)
    void notasEstudiante() {
        var notas = gestor.notasEstudiante("Samantha","Oag");
        assertEquals(5.89, notas.get(new Asignatura("LMGI")));
        assertEquals(3.7, notas.get(new Asignatura("PROG")));
        assertNull(notas.get(new Asignatura("FOL")));
        assertNull(gestor.notasEstudiante("Aitor","E"));
    }

    //endregion

}