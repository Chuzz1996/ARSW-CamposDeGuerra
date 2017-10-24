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
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author pipe
 */

@JsonDeserialize(as = Destructora.class)
public class Destructora implements Maquina{
    
    public int speed;
    
    public int attack;
    
    private int x;
    private int y;
    private int direction;
    private LinkedList<Bullet> bullets;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public Destructora(int x,int y,int direction,LinkedList<Bullet> bullets){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.bullets = bullets;
    }
    
    public Destructora(){
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
        return "{velocidad:"+speed+",attack:"+attack+"}";
    }
    
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void addBullet(Bullet bullet) throws CamposDeGuerraPersistenceException {
        bullets.add(bullet);
    }

    @Override
    public LinkedList<Bullet> getBullets() throws CamposDeGuerraPersistenceException {
        return bullets;
    }

    @Override
    public void deleteBullet(Bullet bullet) throws CamposDeGuerraPersistenceException {
        bullets.remove(bullet);
    }
}
