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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class leerMapas {
    
    public static String[][] muestraContenido(String id){
        String[][] mat=null;
        FileReader f= null;
        
        try {
            
            f = new FileReader("mapa"+id+".txt");
            BufferedReader br = new BufferedReader(f);
            while(br.ready()){
                String[] input = br.readLine().trim().split(" ");
                mat = new String[Integer.parseInt(input[0])][Integer.parseInt(input[1])];
                for(int i = 0; i < Integer.parseInt(input[0]); i++){
                    String file = br.readLine();
                    for (int j = 0; j < Integer.parseInt(input[1]); j++) {
                        mat[i][j] = file.substring(j, j+1);
                        
                    }
                }
            }
            br.close();
            } catch (IOException ex) {   
                Logger.getLogger(leerMapas.class.getName()).log(Level.SEVERE, null, ex);
            }   
            
        return mat;
    }

   
}
