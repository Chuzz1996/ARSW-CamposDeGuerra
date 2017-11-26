/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.eci.arsw.camposdeguerra.model;


public class Usuario {
    
    private Maquina tipoMaquina;
    private String userName="";
    private int puntaje=0;
    private int vida=0;
    private String equipo="";

    
    public Usuario() {
    }
    
    /**
     * 
     * @param userName
     * @param tipoMaquina
     * @param puntaje
     */
    public Usuario(String userName,Maquina tipoMaquina,int puntaje,int vida, String equipo) {
        this.userName=userName;
        this.tipoMaquina=tipoMaquina;
        this.puntaje=puntaje;
        this.vida = vida;
        this.equipo = equipo;
    }
    
    /**
     * 
     * @return 
     */
    public Maquina getTipoMaquina() {
        return tipoMaquina;
    }

    /**
     * 
     * @param tipoMaquina 
     */
    public void setTipoMaquina(Maquina tipoMaquina) {
        this.tipoMaquina = tipoMaquina;
    }

    /**
     * 
     * @return 
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @param userName 
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 
     * @return 
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * 
     * @param puntaje 
     */
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    /**
     * 
     * @return 
     */
    public int getVida() {
        return vida;
    }

    /**
     * 
     * @param vida 
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * 
     * @return 
     */
    public String getEquipo() {
        return equipo;
    }

    /**
     * 
     * @param equipo 
     */
    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }
    
    @Override
    public String toString(){
        return "Usuario{" + "Username:" + userName + ", Puntaje:" + puntaje + ", Maquina:" + tipoMaquina.describe() + '}';
    }
    
    
    
    
}
