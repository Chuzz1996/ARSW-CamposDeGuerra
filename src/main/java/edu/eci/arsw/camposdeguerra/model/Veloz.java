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
public class Veloz extends Maquina{
    
    public Veloz(int x,int y,int direction,LinkedList<Bullet> bullets){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.bullets = bullets;
        this.speed = 10;
        this.attack = 15;
        this.nombreImagen="/images/veloz";
    }
    
    public Veloz(){
        this.speed = 25;
        this.attack = 5;
        this.nombreImagen="/images/veloz";
    }
    
}
