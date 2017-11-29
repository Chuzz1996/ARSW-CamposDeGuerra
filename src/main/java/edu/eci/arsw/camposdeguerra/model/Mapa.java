/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.model;

import org.springframework.data.annotation.Id;

/**
 *
 * @author 2107713
 */
public class Mapa {
    @Id
    private String id = "";
    private String mapa = "";
    
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
