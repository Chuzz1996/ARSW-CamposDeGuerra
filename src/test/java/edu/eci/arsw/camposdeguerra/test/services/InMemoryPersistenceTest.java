/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.test.services;

import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import edu.eci.arsw.camposdeguerra.persistence.impl.InMemoryCamposDeGuerraPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class InMemoryPersistenceTest {

    @Test
    public void saveNewAndLoadTest() {

        InMemoryCamposDeGuerraPersistence icgp = new InMemoryCamposDeGuerraPersistence();
        Usuario u = new Usuario("test1", "Destructor", "99999");
        try {
            icgp.saveUsuario(u);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            assertNotNull("Loading a previously stored user.", icgp.getUsuario(u.getUserName()));
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void saveExistingUserTest() {
        InMemoryCamposDeGuerraPersistence icgp = new InMemoryCamposDeGuerraPersistence();
        Usuario u = new Usuario("test1", "Destructor", "99999");
        try {
            icgp.saveUsuario(u);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            icgp.saveUsuario(u);
        } catch (CamposDeGuerraPersistenceException ex) {
            //Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
            assertEquals(ex.getMessage(), "El nombre de usuario ya se encuentra registrado,elija otro nombre");
        }
    }
    
    @Test
    public void saveAndGetUsuarioTest() {

        InMemoryCamposDeGuerraPersistence icgp = new InMemoryCamposDeGuerraPersistence();

        Usuario u = new Usuario("test1", "Destructor", "99999");
        try {
            icgp.saveUsuario(u);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Usuario temp = null;
        try {
            temp = icgp.getUsuario("test1");
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(temp.getUserName(), u.getUserName());
    }

    @Test
    public void saveAndUpdateAndGetUsuarioTest() {

        InMemoryCamposDeGuerraPersistence icgp = new InMemoryCamposDeGuerraPersistence();

        Usuario u = new Usuario("test1", "Destructor", "99999");
        try {
            icgp.saveUsuario(u);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        u.setPuntaje("0");
        try {
            icgp.updateUsuario(u);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Usuario temp = new Usuario();
        try {
            temp = icgp.getUsuario("test1");
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals("0", temp.getPuntaje());
    }

    @Test
    public void saveAndDeleteUsuarioTest() {

        InMemoryCamposDeGuerraPersistence icgp = new InMemoryCamposDeGuerraPersistence();

        Usuario u = new Usuario("test1", "Destructor", "99999");
        try {
            icgp.saveUsuario(u);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            icgp.deleteUsuario("test1");
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            icgp.getUsuario("test1");
        } catch (CamposDeGuerraNotFoundException ex) {
            //Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
            assertEquals(ex.getMessage(), "El usuario test1 no existe.");
        }
    }
    
    
}
