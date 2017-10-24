/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author pipe
 */
@JsonDeserialize(as = Destructora.class)
public interface Maquina {
    
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
    
    /**
     * 
     * @throws CamposDeGuerraPersistenceException 
     */
    public void addBullet(Bullet bullet)throws CamposDeGuerraPersistenceException;
    
    /**
     * 
     * @return
     * @throws CamposDeGuerraPersistenceException 
     */
    public LinkedList<Bullet> getBullets()throws CamposDeGuerraPersistenceException;

    /**
     * 
     * @param bullet
     * @throws CamposDeGuerraPersistenceException 
     */
    public void deleteBullet(Bullet bullet)throws CamposDeGuerraPersistenceException;
    
}
