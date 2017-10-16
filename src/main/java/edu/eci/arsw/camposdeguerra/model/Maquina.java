/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.model;

import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;

/**
 *
 * @author pipe
 */

public interface Maquina {
    
    /**
     * 
     * @param resta
     * @throws CamposDeGuerraPersistenceException 
     */
    public void damage(int resta)throws CamposDeGuerraPersistenceException;
    
    /**
     * 
     * @param vida
     * @throws CamposDeGuerraPersistenceException 
     */
    public void addLive(int vida)throws CamposDeGuerraPersistenceException;
    
    /**
     * 
     * @param speed
     * @throws CamposDeGuerraPersistenceException 
     */
    public void speed(int speed)throws CamposDeGuerraPersistenceException;
    
    /**
     * 
     * @param damage
     * @throws CamposDeGuerraPersistenceException 
     */
    public void attack(int damage)throws CamposDeGuerraPersistenceException;
    
    /**
     *
     * @return
     */
    public String describe();
    
}
