/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import java.util.LinkedList;

/**
 *
 * @author pipe
 */
public class Destructora extends Maquina{
    
    public Destructora(int x,int y,int direction,LinkedList<Bullet> bullets){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.bullets = bullets;
        this.speed = 10;
        this.attack = 15;
        this.nombreImagen="/images/destructora";
    }
    
    public Destructora(){
        this.speed = 10;
        this.attack = 15;
        this.nombreImagen="/images/destructora";
    }
}
    

