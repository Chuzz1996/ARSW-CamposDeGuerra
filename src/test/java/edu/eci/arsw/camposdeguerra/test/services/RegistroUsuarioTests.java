package edu.eci.arsw.camposdeguerra.test.services;

import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import edu.eci.arsw.camposdeguerra.persistence.impl.InMemoryCamposDeGuerraPersistence;
import edu.eci.arsw.camposdeguerra.services.CamposDeGuerraServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RegistroUsuarioTests {

    @Test
    public void saveNewAndLoadTest() {

        InMemoryCamposDeGuerraPersistence icgp = new InMemoryCamposDeGuerraPersistence();
        Usuario u = new Usuario("test", "Destructor", "99999");
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
        Usuario u = new Usuario("test", "Destructor", "99999");
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

        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        CamposDeGuerraServices cdg = ac.getBean(CamposDeGuerraServices.class);

        Usuario u = new Usuario("test", "Destructor", "99999");
        try {
            cdg.addNewUsuario(u);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Usuario temp = null;
        try {
            temp = cdg.getUsuario("test");
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(temp.getUserName(), u.getUserName());
    }

    @Test
    public void saveAndUpdateAndGetUsuarioTest() {

        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        CamposDeGuerraServices cdg = ac.getBean(CamposDeGuerraServices.class);

        Usuario u = new Usuario("test", "Destructor", "99999");
        try {
            cdg.addNewUsuario(u);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        u.setPuntaje("0");
        try {
            cdg.updateUsuario(u);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Usuario temp = new Usuario();
        try {
            temp = cdg.getUsuario("test");
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals("0", temp.getPuntaje());
    }

    @Test
    public void saveAndDeleteUsuarioTest() {

        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        CamposDeGuerraServices cdg = ac.getBean(CamposDeGuerraServices.class);

        Usuario u = new Usuario("test", "Destructor", "99999");
        try {
            cdg.addNewUsuario(u);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cdg.deleteUsuario("test");
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cdg.getUsuario("test");
        } catch (CamposDeGuerraNotFoundException ex) {
            //Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
            assertEquals(ex.getMessage(), "El usuario test no existe.");
        }
    }
}
