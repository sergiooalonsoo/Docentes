package com.masanz.gda;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DesarrolloGestorTest {

    //region tests operaciones grupo 11-14
    @Test
    @Order(11)
    void existeGrupo() {
        var gestor = new Gestor();
        var registro = new TreeMap<Grupo, HashMap<Asignatura, ArrayList<Estudiante>>>();
        registro.put(new Grupo("G1"), new HashMap<>());
        gestor.setRegistro(registro);
        boolean b;
        b = gestor.existeGrupo(new Grupo("G1"));
        assertTrue(b, "G1 existe");
        b = gestor.existeGrupo(new Grupo("G2"));
        assertFalse(b, "G2 no existe");
        b = gestor.existeGrupo(null);
        assertFalse(b, "Un grupo null no existe");
    }

    @Test
    @Order(12)
    void anadirGrupo() {
        boolean b;
        var gestor = new Gestor();
        var g1 = new Grupo("G1");
        gestor.anadirGrupo(g1);
        var registro = gestor.getRegistro();
        b = registro.containsKey(g1);
        assertTrue(b, "G1 tiene que estar");
        var map = registro.get(g1);
        assertNotNull(map, "G1 tiene que tener un mapa no nulo");
        assertEquals(0, map.size(), "G1 tiene que tener un mapa vacío");
    }

    @Test
    @Order(13)
    void getGrupos() {
        var gestor = new Gestor();
        var registro = gestor.getRegistro();
        var g1 = new Grupo("G1");
        var g2 = new Grupo("G2");
        registro.put(g1, new HashMap<>());
        registro.put(g2, new HashMap<>());
        var grupos = gestor.getGrupos();
        assertEquals(2, grupos.size(), "tiene que haber 2 grupos");
        assertTrue(grupos.contains(g1), "G1 tiene que estar");
        assertTrue(grupos.contains(g2), "G2 tiene que estar");
    }

    @Test
    @Order(14)
    void borrarGrupo() {
        var gestor = new Gestor();
        var registro = gestor.getRegistro();
        var g1 = new Grupo("G1");
        var g2 = new Grupo("G2");
        var g3 = new Grupo("G3");
        var g4 = new Grupo("G4");
        registro.put(g1, new HashMap<>());
        registro.put(g2, new HashMap<>());
        registro.put(g3, new HashMap<>());
        registro.put(g4, new HashMap<>());
        gestor.borrarGrupo(new Grupo("G1"));
        gestor.borrarGrupo(new Grupo("G2"));
        gestor.borrarGrupo(new Grupo("G5"));
        var grupos = gestor.getRegistro().keySet();
        assertEquals(2, grupos.size(), "tiene que haber 2 grupos");
        assertTrue(grupos.contains(g3), "G3 tiene que estar");
        assertTrue(grupos.contains(g4), "G4 tiene que estar");
    }

    //endregion

    //region tests operaciones asignatura 21-25

    @Test
    @Order(21)
    void existeAsignaturaGrupo() {
        var gestor = new Gestor();
        var registro = new TreeMap<Grupo, HashMap<Asignatura, ArrayList<Estudiante>>>();
        var g1 = new Grupo("G1");
        var g2 = new Grupo("G2");
        var g3 = new Grupo("G3");
        var a1 = new Asignatura("A1");
        var a2 = new Asignatura("A2");
        var l1 = new ArrayList<Estudiante>();
        var l2 = new ArrayList<Estudiante>();
        var m1 = new HashMap<Asignatura, ArrayList<Estudiante>>();
        var m2 = new HashMap<Asignatura, ArrayList<Estudiante>>();
        m1.put(a1,l1);
        m2.put(a2,l2);
        registro.put(g1, m1);
        registro.put(g2, m2);
        gestor.setRegistro(registro);
        boolean b;
        b = gestor.existeAsignaturaGrupo(a1, g1);
        assertTrue(b, "A1 existe en G1");
        b = gestor.existeAsignaturaGrupo(a2, g1);
        assertFalse(b, "A2 no existe en G1");
        b = gestor.existeAsignaturaGrupo(a1, g2);
        assertFalse(b, "A1 no existe en G2");
        b = gestor.existeAsignaturaGrupo(a2, g2);
        assertTrue(b, "A2 existe en G2");
        b = gestor.existeAsignaturaGrupo(a1, g3);
        assertFalse(b, "G3 no esta");
    }

    @Test
    @Order(22)
    void anadirAsignaturaGrupo() {
        boolean b;
        var gestor = new Gestor();
        var g1 = new Grupo("G1");
        var g2 = new Grupo("G2");
        var a1 = new Asignatura("A1");
        var a2 = new Asignatura("A2");
        var a3 = new Asignatura("A3");
        gestor.anadirAsignaturaGrupo(a1, g1);
        gestor.anadirAsignaturaGrupo(a2, g1);
        gestor.anadirAsignaturaGrupo(a2, g2);
        var registro = gestor.getRegistro();
        var grupos = registro.keySet();
        b = grupos.contains(g1);
        assertTrue( b, "Existe G1");
        b = grupos.contains(g2);
        assertTrue( b, "Existe G2");
        var map1 = registro.get(g1);
        var asignaturas1 = map1.keySet();
        b = asignaturas1.contains(a1);
        assertTrue( b, "Existe A1 en G1");
        b = asignaturas1.contains(a2);
        assertTrue( b, "Existe A2 en G1");
        b = asignaturas1.contains(a3);
        assertFalse( b, "No existe A3 en G1");
        var map2 = registro.get(g2);
        var asignaturas2 = map2.keySet();
        b = asignaturas2.contains(a1);
        assertFalse( b, "No existe A1 en G2");
        b = asignaturas2.contains(a2);
        assertTrue( b, "Existe A2 en G2");
        b = asignaturas2.contains(a3);
        assertFalse( b, "No existe A3 en G2");
    }

    @Test
    @Order(23)
    void getAsignaturasGrupo() {
        boolean b;
        var gestor = new Gestor();
        var registro = new TreeMap<Grupo, HashMap<Asignatura, ArrayList<Estudiante>>>();
        var g1 = new Grupo("G1");
        var a1 = new Asignatura("A1");
        var a2 = new Asignatura("A2");
        var a3 = new Asignatura("A3");
        var l1 = new ArrayList<Estudiante>();
        var l2 = new ArrayList<Estudiante>();
        var l3 = new ArrayList<Estudiante>();
        var m1 = new HashMap<Asignatura, ArrayList<Estudiante>>();
        m1.put(a1,l1);
        m1.put(a2,l2);
        m1.put(a3,l3);
        registro.put(g1, m1);
        gestor.setRegistro(registro);
        var grupos = registro.keySet();
        b = grupos.contains(g1);
        assertTrue( b, "Existe G1");
        var asignaturas = gestor.getAsignaturas(g1);
        assertEquals(3, asignaturas.size(), "Hay 3 asignaturas");
        b = asignaturas.contains(a1);
        assertTrue( b, "Existe A1 en G1");
        b = asignaturas.contains(a2);
        assertTrue( b, "Existe A2 en G1");
        b = asignaturas.contains(a3);
        assertTrue( b, "Existe A3 en G1");
    }

    @Test
    @Order(24)
    void getAsignaturas() {
        boolean b;
        var gestor = new Gestor();
        var registro = new TreeMap<Grupo, HashMap<Asignatura, ArrayList<Estudiante>>>();
        var g1 = new Grupo("G1");
        var g2 = new Grupo("G2");
        var a11 = new Asignatura("A1");
        var a12 = new Asignatura("A1");
        var a2 = new Asignatura("A2");
        var a3 = new Asignatura("A3");
        var l1 = new ArrayList<Estudiante>();
        var l2 = new ArrayList<Estudiante>();
        var m1 = new HashMap<Asignatura, ArrayList<Estudiante>>();
        m1.put(a11,l1);
        m1.put(a2,l1);
        registro.put(g1, m1);
        var m2 = new HashMap<Asignatura, ArrayList<Estudiante>>();
        m2.put(a12,l2);
        m2.put(a3,l2);
        registro.put(g2, m2);
        gestor.setRegistro(registro);
        var grupos = registro.keySet();
        var asignaturas = gestor.getAsignaturas();
        assertEquals(3, asignaturas.size(), "Hay 3 asignaturas");
        b = asignaturas.contains(a11);
        assertTrue( b, "Existe A1 1");
        b = asignaturas.contains(a12);
        assertTrue( b, "Existe A1 2");
        b = asignaturas.contains(a2);
        assertTrue( b, "Existe A2");
        b = asignaturas.contains(a3);
        assertTrue( b, "Existe A3");
    }

    @Test
    @Order(25)
    void borrarAsignaturaGrupo() {
        boolean b;
        var gestor = new Gestor();
        var registro = new TreeMap<Grupo, HashMap<Asignatura, ArrayList<Estudiante>>>();
        var g1 = new Grupo("G1");
        var a1 = new Asignatura("A1");
        var a2 = new Asignatura("A2");
        var a3 = new Asignatura("A3");
        var l1 = new ArrayList<Estudiante>();
        var l2 = new ArrayList<Estudiante>();
        var l3 = new ArrayList<Estudiante>();
        var m1 = new HashMap<Asignatura, ArrayList<Estudiante>>();
        m1.put(a1,l1);
        m1.put(a2,l2);
        m1.put(a3,l3);
        registro.put(g1, m1);
        gestor.setRegistro(registro);
        var grupos = registro.keySet();
        b = grupos.contains(g1);
        assertTrue( b, "Existe G1");
        var mapa = gestor.getRegistro().get(g1);
        var asignaturas = mapa.keySet();
        b = asignaturas.toString().contains("A1")&&asignaturas.toString().contains("A2")&&asignaturas.toString().contains("A3");
        assertTrue(b, "Están A1, A2, A3");
        gestor.borrarAsignaturaGrupo(a2,g1);
        b = asignaturas.toString().contains("A1")&&asignaturas.toString().contains("A3");
        assertTrue(b, "Están A1, A3");
        gestor.borrarAsignaturaGrupo(a1,g1);
        b = asignaturas.toString().contains("A3");
        assertTrue(b, "Está A3");
        gestor.borrarAsignaturaGrupo(a3,g1);
        assertEquals(0, asignaturas.size(), "Está vacío");
    }

    //endregion


}