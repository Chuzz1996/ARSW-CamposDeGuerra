/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.model;

import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import org.springframework.stereotype.Service;

/**
 *
 * @author pipe
 */

@Service
public class Destructora implements Maquina{
    
    public int live;
    
    public int speed;
    
    public int attack;
    
    public Destructora(){
        this.live = 100;
        this.speed = 10;
        this.attack = 10;
    }

    @Override
    public void damage(int resta) throws CamposDeGuerraPersistenceException {
        this.live -= resta;
    }

    @Override
    public void addLive(int vida) throws CamposDeGuerraPersistenceException {
        this.live += vida;
    }

    @Override
    public void speed(int speed) throws CamposDeGuerraPersistenceException {
        this.speed += speed;
    }

    @Override
    public void attack(int damage) throws CamposDeGuerraPersistenceException {
        this.attack += damage;
    }

    @Override
    public String describe(){
        return "{live:"+live+",velocidad:"+speed+",attack:"+attack+"}";
    }
    
}
