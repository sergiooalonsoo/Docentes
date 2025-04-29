package com.masanz.gda;

public class Salida {

    public static void mostrarMenu() {
        System.out.println("=====================================================================");
        System.out.println("=           G e s t i ó n    d e    A s i g n a t u r a s           =");
        System.out.println("=====================================================================");
        System.out.println("11. Existe grupo");
        System.out.println("12. Añadir grupo");
        System.out.println("13. Listar grupos");
        System.out.println("14. Borrar grupo");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("21. Existe asignatura en un grupo");
        System.out.println("22. Añadir asignatura a un grupo");
        System.out.println("23. Listar asignaturas de un grupo");
        System.out.println("24. Borrar asignatura de un grupo");
        System.out.println("25. Listar las asignaturas distintas de todos los grupos");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("31. Listar estudiantes de una asignatura de un grupo");
        System.out.println("32. Existe estudiante en una asignatura de un grupo");
        System.out.println("33. Consultar estudiante de una asignatura de un grupo");
        System.out.println("34. Añadir estudiante a una asignatura de un grupo");
        System.out.println("35. Borrar estudiante de una asignatura de un grupo");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("41. Listar todas/os las/os estudiantes distintas/os de un grupo");
        System.out.println("42. Listar las/os estudiantes de una asignatura de todos los grupos");
        System.out.println("43. Listar los/as estudiantes con nota mayor de una asignatura");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("51. Mostrar la distribución de notas de la asignatura de un grupo");
        System.out.println("52. Mostrar la distribución de notas de una asignatura");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("61. Mostrar el grupo de un/a estudiante");
        System.out.println("62. Mostrar las notas de un/a estudiante");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("71. Listado de docentes");
        System.out.println("72. Docentes que no imparten ninguna asignatura");
        System.out.println("73. Docente que imparte en un grupo una asignatura");
        System.out.println("74. Docentes que imparten en un grupo");
        System.out.println("75. Docentes que imparten una asignatura");
        System.out.println("76. Cuántos profesores hay en cada grupo");
        System.out.println("77. Grupos comunes en los que imparten dos docentes");
        System.out.println("78. Listado con los grupos, las asignaturas y los docentes");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("0. Terminar");
        System.out.println("=====================================================================");
    }

    public static void info(String txt) {
        System.out.println(txt);
    }

    public static void listar(String[] valores) {
        for (int i = 0; i < valores.length; i++) {
            System.out.printf("%2d. %s\n", i+1, valores[i]);
        }
        System.out.println("Fin de lista.");
    }

    public static void listarH(String[] valores1, String[] valores2) {
        int n = Math.min(valores1.length, valores2.length);
        for (int i = 0; i < n; i++)  System.out.printf("|%3s",valores1[i]);
        System.out.println("|");
        for (int i = 0; i < n; i++)  System.out.printf("|---");
        System.out.println("|");
        for (int i = 0; i < n; i++)  System.out.printf("|%3s",valores2[i]);
        System.out.println("|");
        for (int i = 0; i < n; i++)  System.out.printf("|---");
        System.out.println("|");
    }

    public static void listarVnota(String[] valores1, String[] valores2) {
        int n = Math.min(valores1.length, valores2.length);
        for (int i = 0; i < n; i++) {
            //System.out.printf("%-5s: %2.2f\n", valores1[i], Double.parseDouble(valores2[i]));
            System.out.printf("%-5s: %-3s\n", valores1[i], valores2[i]);
        }
    }

    public static void listarVgrupo(String[] valores1, String[] valores2) {
        int n = Math.min(valores1.length, valores2.length);
        for (int i = 0; i < n; i++) {
            System.out.printf("%-25s %s\n", valores1[i], valores2[i]);
        }
    }

    public static void mostrar(String[][] valores, int[] colWidths) {
        for (int i = 0; i < valores.length; i++) {
            for (int j = 0; j < valores[i].length; j++) {
                System.out.printf("%" + colWidths[j] + "s", valores[i][j]);
            }
            System.out.println();
        }
    }

}