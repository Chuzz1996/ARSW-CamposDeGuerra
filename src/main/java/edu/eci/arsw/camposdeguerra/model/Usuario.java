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

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Usuario {

    @Id
    private String id="";
    
    private Maquina tipoMaquina;
    private int puntaje=0;
    private int vida=0;
    private String equipo="";

    
    public Usuario() {
    }
    
    /**
     * 
     * @param id
     * @param tipoMaquina
     * @param puntaje
     * @param vida
     * @param equipo
     */
    public Usuario(String id,Maquina tipoMaquina,int puntaje,int vida, String equipo) {
        this.id=id;
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
    public String getId() {
        return id;
    }
    
    /**
     * 
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
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
        return "Usuario{" + "Username:" + id + ", Puntaje:" + puntaje + ", Maquina:" + tipoMaquina.describe() + '}';
    }
    
    
    
    
}
