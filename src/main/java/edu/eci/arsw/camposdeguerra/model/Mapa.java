/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author 2107713
 */

@Document(collection = "maps")
public class Mapa {
    @Id
    private String id = "";
    
    private String mapa = "";
    
    public Mapa(String id,String mapa){
        this.id=id;
        this.mapa=mapa;
    }
    
    public Mapa(){
    }
    
    /**
     * 
     * @return 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return 
     */
    public String getMapa() {
        return mapa;
    }

    /**
     * 
     * @param mapa 
     */
    public void setMapa(String mapa) {
        this.mapa = mapa;
    }
    
    
}
