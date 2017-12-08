/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.model;

import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import java.util.LinkedList;

/**
 *
 * @author pipe
 */

public class Maquina {
    protected int speed=0;
    protected int attack=0;
    protected int x=0;
    protected int y=0;
    protected int direction=1;
    protected LinkedList<Bullet> bullets = new LinkedList<>();
    protected String nombreImagen="";
    
    /**
     * 
     * @param speed
     * @throws CamposDeGuerraPersistenceException 
     */
    public void changeSpeed(int speed)throws CamposDeGuerraPersistenceException{
        this.speed = speed;
    }
    
    /**
     * 
     * @param attack
     * @throws CamposDeGuerraPersistenceException 
     */
    public void changeAttack(int attack)throws CamposDeGuerraPersistenceException{
        this.attack = attack;
    };
    
    /**
     *
     * @return
     */
    public String describe(){
        return "{velocidad:"+speed+",attack:"+attack+"}";
    }
    /**
     * 
     * @return
     * @throws CamposDeGuerraPersistenceException 
     */
    public LinkedList<Bullet> getBullets()throws CamposDeGuerraPersistenceException{
        return bullets;
    };

    /**
     * 
     * @param bullet
     * @throws CamposDeGuerraPersistenceException 
     */
    public void deleteBullet(Bullet bullet)throws CamposDeGuerraPersistenceException{
        bullets.remove(bullet);
    };
    
    /**
     * 
     * @param bullet
     * @throws CamposDeGuerraPersistenceException 
     */
    public void addBullet(Bullet bullet)throws CamposDeGuerraPersistenceException{
        bullets.add(bullet);
    };
    
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

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }
    
    
    
}
