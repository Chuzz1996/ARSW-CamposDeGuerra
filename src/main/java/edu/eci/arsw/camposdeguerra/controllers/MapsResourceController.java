/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.controllers;


import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import edu.eci.arsw.camposdeguerra.services.CamposDeGuerraServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping("/Mapas")
public class MapsResourceController {

 
    
    @Autowired
    private CamposDeGuerraServices cdg;
    

    
    /**
     * 
     * @param id
     * @return 
     */
    @RequestMapping(path = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getMapa(@PathVariable String id) {
        try {
            //Obtener datos
            return new ResponseEntity<>(cdg.getMap(id), HttpStatus.ACCEPTED);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(UsersResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * 
     * @return 
     *
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllMaps() {
        try {
            //Obtener datos
            return new ResponseEntity<>(cdg.getAllUsuarios(), HttpStatus.ACCEPTED);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(UsersResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }**/
    
    
    
    
    
    
    

}
