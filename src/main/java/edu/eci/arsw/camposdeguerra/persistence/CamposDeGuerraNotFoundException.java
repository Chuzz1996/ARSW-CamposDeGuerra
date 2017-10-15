/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.persistence;


public class CamposDeGuerraNotFoundException extends Exception{

    public CamposDeGuerraNotFoundException(String message) {
        super(message);
    }

    public CamposDeGuerraNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
