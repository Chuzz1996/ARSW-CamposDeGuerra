/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.model;

import java.util.LinkedList;

/**
 *
 * @author pipe
 */
public class Protectora extends Maquina{
    
    public Protectora(int x,int y,int direction,LinkedList<Bullet> bullets){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.bullets = bullets;
        this.speed = 10;
        this.attack = 15;
        this.nombreImagen="/images/protectora";
    }
    
    public Protectora(){
        this.speed = 5;
        this.attack = 10;
        this.nombreImagen="/images/protectora";
    }
    
}
