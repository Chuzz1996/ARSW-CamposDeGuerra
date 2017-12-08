/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.logic;

/**
 *
 * @author 2107713
 */
import edu.eci.arsw.camposdeguerra.model.Mapa;

public class leerMapas {

    public static String[][] muestraContenido(Mapa m) {
        String[][] mat;
        String[] input = m.getMapa().split(" ");
        mat = new String[Integer.parseInt(input[0])][Integer.parseInt(input[1])];
        for (int i = 0; i < Integer.parseInt(input[0]); i++) {
            for (int j = 0; j < Integer.parseInt(input[1]); j++) {
                mat[i][j] = input[j+2 + Integer.parseInt(input[1]) * i];
            }
        }
        return mat;
    }
}
