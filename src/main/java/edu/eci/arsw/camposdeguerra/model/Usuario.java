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
    private String tipoMaquina="";
    private String userName="";
    private String puntaje="";

    public Usuario() {
    }
    
    public Usuario(String userName,String tipoMaquina,String puntaje) {
        this.userName=userName;
        this.tipoMaquina=tipoMaquina;
        this.puntaje=puntaje;
    }

    public String getTipoMaquina() {
        return tipoMaquina;
    }

    public void setTipoMaquina(String tipoMaquina) {
        this.tipoMaquina = tipoMaquina;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    

    public String getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }

    

    @Override
    public String toString() {
        return "Usuario{" + "Username=" + userName + ", Puntaje=" + puntaje + ", Maquina=" + tipoMaquina + '}';
    }
    
    
    
    
}
