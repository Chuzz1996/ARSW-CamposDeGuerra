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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Usuario {
    
    @Autowired
    private Maquina tipoMaquina;
    private String userName="";
    private int puntaje;
    private int equipo;

    
    public Usuario() {
    }
    
    /**
     * 
     * @param userName
     * @param tipoMaquina
     * @param puntaje
     * @param equipo 
     */
    public Usuario(String userName,Maquina tipoMaquina,int puntaje, int equipo) {
        this.userName=userName;
        this.tipoMaquina=tipoMaquina;
        this.puntaje=puntaje;
        this.equipo = equipo;
       
    }
    
    /**
     * 
     * @param equipo
     */
    public void setEquipo(int equipo){
        this.equipo = equipo;
    }

    /**
     * 
     * @return 
     */
    public int getEquipo(){
        return equipo;
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

    @Override
    public String toString(){
        return "Usuario{" + "Username:" + userName + ", Puntaje:" + puntaje + ", Equipo:"+ equipo +", Maquina:" + tipoMaquina.describe() + '}';
    }
    
    
    
    
}
