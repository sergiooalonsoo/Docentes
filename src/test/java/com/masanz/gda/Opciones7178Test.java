package com.masanz.gda;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Opciones7178Test {

    Gestor gestor;

    @BeforeEach
    void setUp() {
        gestor = new Gestor();
        Cargador.cargaNotas(gestor, "notas.csv");
        Cargador.cargaDocentes(gestor, "docentes.csv");
    }

    @Test
    @Order(71)
    void getDocentes() {
        var res = gestor.getDocentes();
        assertEquals(15, res.size());
        assertEquals("Patxi", res.get(0).getNombre());
        assertEquals("Nerea", res.get(14).getNombre());
    }

    @Test
    @Order(72)
    void getDocentesSinCarga() {
        var res = gestor.getDocentesSinCarga();
        assertEquals(3, res.size());
        var a = new String[] {"Iñigo","Silvia","Nerea"};
        int i = 0;
        for (Docente docente : res) {
            assertEquals(a[i++], docente.getNombre());
        }
    }

    @Test
    @Order(73)
    void getDocenteGrupoAsignatura() {
        var grupo = new Grupo("DAW1");
        var asignatura = new Asignatura("PROG");
        var res = gestor.getDocenteGrupoAsignatura(grupo, asignatura);
        assertEquals("Miren", res.getNombre());
        grupo = new Grupo("ASIR1");
        asignatura = new Asignatura("IMSO");
        res = gestor.getDocenteGrupoAsignatura(grupo, asignatura);
        assertEquals("Aitor", res.getNombre());
        grupo = new Grupo("DAM1");
        asignatura = new Asignatura("LMGI");
        res = gestor.getDocenteGrupoAsignatura(grupo, asignatura);
        assertEquals("Santi", res.getNombre());
    }

    @Test
    @Order(74)
    void getDocentesGrupo() {
        var grupo = new Grupo("DAW1");
        var res = gestor.getDocentesGrupo(grupo);
        assertEquals(6, res.size());
        var a = new String[] {"Patxi","Santi","Miren","Laura","Ion","Maite"};
        int i = 0;
        for (Docente docente : res) {
            assertEquals(a[i++], docente.getNombre());
        }
    }

    @Test
    @Order(75)
    void getGruposNumeroDocentes() {
        var res = gestor.getGruposNumeroDocentes();
        assertEquals(3, res.size());
        assertEquals(5, res.get(new Grupo("ASIR1")));
        assertEquals(6, res.get(new Grupo("DAM1")));
        assertEquals(6, res.get(new Grupo("DAW1")));
    }

    @Test
    @Order(76)
    void getAsignaturasDocentesGrupo() {
        var res = gestor.getAsignaturasDocentesGrupo(new Grupo("DAW1"));
        assertEquals(7, res.size());
        assertEquals("Laura", res.get(new Asignatura("BADA")).getNombre());
        assertEquals("Ion", res.get(new Asignatura("ING1")).getNombre());
        assertEquals("Miren", res.get(new Asignatura("PROG")).getNombre());
    }

    @Test
    @Order(77)
    void getGruposComunes() {
        var docente1 = new Docente("Santi", "Eseberri");
        var docente2 = new Docente("Maite", "Santesteban");
        var res = gestor.getGruposComunes(docente1, docente2);
        assertEquals(2, res.size());
        assertTrue(res.contains(new Grupo("DAM1")));
        assertTrue(res.contains(new Grupo("DAW1")));
    }

    @Test
    @Order(78)
    void getGruposAsignaturasDocentes() {
        var res = gestor.getGruposAsignaturasDocentes();
        var r1 = res.keySet();
        assertTrue(r1.contains(new Grupo("ASIR1")));
        assertTrue(r1.contains(new Grupo("DAM1")));
        assertTrue(r1.contains(new Grupo("DAW1")));
        assertFalse(r1.contains(new Grupo("OTRO")));
        var r2 = res.get(new Grupo("ASIR1")).keySet();
        assertTrue(r2.contains(new Asignatura("FUHA")));
        assertTrue(r2.contains(new Asignatura("LMGI")));
        assertFalse(r2.contains(new Asignatura("PROG")));
        var r3 = res.get(new Grupo("DAW1")).values();
        assertTrue(r3.contains(new Docente("Ion","Perez")));
        assertFalse(r3.contains(new Docente("Karmen","Ruiz")));
    }

}
