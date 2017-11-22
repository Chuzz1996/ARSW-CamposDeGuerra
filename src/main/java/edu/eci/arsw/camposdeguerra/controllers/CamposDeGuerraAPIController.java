/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.controllers;



import edu.eci.arsw.camposdeguerra.model.Room;
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


@RestController
@RequestMapping(value = "/CamposDeGuerra")
public class CamposDeGuerraAPIController {

    @Autowired
    private CamposDeGuerraServices cdg;
    
    
    
    /**
     * 
     * @param user
     * @return 
     */
    @RequestMapping(path = "/Usuarios/{user}",method = RequestMethod.GET)
    public ResponseEntity<?> getUsuario(@PathVariable String user) {
        try {
            //Obtener datos
            return new ResponseEntity<>(cdg.getUsuario(user), HttpStatus.ACCEPTED);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * 
     * @return 
     */
    @RequestMapping(path = "/Usuarios",method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsuarios() {
        try {
            //Obtener datos
            return new ResponseEntity<>(cdg.getAllUsuarios(), HttpStatus.ACCEPTED);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    
    /**
     * 
     * @param u
     * @return 
     */
    @RequestMapping(path = "/Usuarios",method = RequestMethod.POST)
    public ResponseEntity<?> addUsuario(@RequestBody Usuario u) {
        try {
            //Registrar dato
            cdg.addNewUsuario(u);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }

    }
    
    /**
     * 
     * @param u
     * @return 
     */
    @RequestMapping(path = "/Usuarios",method = RequestMethod.PUT)
    public ResponseEntity<?> updateUsuario(@RequestBody Usuario u) {
        try {
            //Actualizar dato
            cdg.updateUsuario(u);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    /**
     * 
     * @param user
     * @return 
     */
    @RequestMapping(path = "/Usuarios/{user}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUsuario(@PathVariable String user) {
        try {
            //Borrar dato
            cdg.deleteUsuario(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    /**
     * 
     * @return 
     */
    @RequestMapping(path = "/Rooms/free",method = RequestMethod.GET)
    public ResponseEntity<?> getRoomFree() {
        try {
            //Obtener datos
            return new ResponseEntity<>(cdg.getRoomFree(), HttpStatus.ACCEPTED);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * 
     * @param u
     * @param room
     * @return 
     */
    @RequestMapping(path = "/Rooms/{room}",method = RequestMethod.POST)
    public ResponseEntity<?> addUserToRoom(@RequestBody Usuario u,@PathVariable Integer room) {
        try {
            //Registrar dato
            cdg.addUserToRoom(u, room);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }

    }
    
    /**
     * 
     * @param room
     * @return 
     */
    @RequestMapping(path = "/Rooms/{room}",method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsuariosFromRoom(@PathVariable Integer room) {
        try {
            //Obtener datos
            return new ResponseEntity<>(cdg.getAllUsuariosFromRoom(room), HttpStatus.ACCEPTED);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 
     * @param room
     * @return 
     */
    @RequestMapping(path = "/Rooms/{room}/TeamA",method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsuariosFromTeamARoom(@PathVariable Integer room) {
        try {
            //Obtener datos
            return new ResponseEntity<>(cdg.getAllUsuariosFromTeamARoom(room), HttpStatus.ACCEPTED);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * 
     * @param room
     * @return 
     */
    @RequestMapping(path = "/Rooms/{room}/TeamB",method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsuariosFromTeamBRoom(@PathVariable Integer room) {
        try {
            //Obtener datos
            return new ResponseEntity<>(cdg.getAllUsuariosFromTeamBRoom(room), HttpStatus.ACCEPTED);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * 
     * @param user
     * @param room
     * @return 
     */
    @RequestMapping(path = "/Rooms/{room}/Teams/{user}",method = RequestMethod.GET)
    public ResponseEntity<?> getMyTeam(@PathVariable String user,@PathVariable Integer room) {
        try {
            //Obtener datos
            return new ResponseEntity<>(cdg.getMyTeam(user, room), HttpStatus.ACCEPTED);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * 
     * @param user
     * @param room
     * @return 
     */
    @RequestMapping(path = "/Rooms/{room}/user",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUsuarioFromRoom(@RequestBody Usuario user,@PathVariable Integer room ) {
        try {
            //Borrar dato
            cdg.deleteUsuarioFromRoom(user, room);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * 
     * @param room
     * @return 
     */
    @RequestMapping(path = "/Rooms/{room}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllUsuariosFromRoom(@PathVariable Integer room ) {
        try {
            //Borrar dato
            cdg.deleteAllUsuariosFromRoom(room);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    /**
     * 
     * @param u
     * @param room
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    @RequestMapping(path = "/Rooms/{room}/Banderas/A",method = RequestMethod.POST)
    public ResponseEntity<?> setFlagARoom(@RequestBody Usuario u , @PathVariable Integer room) throws CamposDeGuerraNotFoundException {
        try {
            cdg.setFlagARoom(u.getUserName(), room);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    /**
     * 
     * @param u
     * @param room
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    @RequestMapping(path = "/Rooms/{room}/Banderas/B",method = RequestMethod.POST)
    public ResponseEntity<?> setFlagBRoom(@RequestBody Usuario u , @PathVariable Integer room) throws CamposDeGuerraNotFoundException {
         try {
            cdg.setFlagBRoom(u.getUserName(), room);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    /**
     * 
     * @param u
     * @param room
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    @RequestMapping(path = "/Rooms/{room}/Banderas/A/Puntuaciones",method = RequestMethod.POST)
    public  ResponseEntity<?> puntuarA(@RequestBody Usuario u,@PathVariable Integer room) throws CamposDeGuerraNotFoundException{
        try {
            cdg.puntuarA(u.getUserName(), room);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    /**
     * 
     * @param u
     * @param room
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    @RequestMapping(path = "/Rooms/{room}/Banderas/B/Puntuaciones",method = RequestMethod.POST)
    public  ResponseEntity<?> puntuarB(@RequestBody Usuario u,@PathVariable Integer room) throws CamposDeGuerraNotFoundException{
        try {
            cdg.puntuarB(u.getUserName(), room);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    /**
     * 
     * @param u
     * @param room
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    @RequestMapping(path = "/Rooms/{room}/Banderas/A",method = RequestMethod.DELETE)
    public  ResponseEntity<?> soltarBanderaA(@RequestBody Usuario u,@PathVariable Integer room) throws CamposDeGuerraNotFoundException{
         try {
            cdg.soltarBanderaA(u.getUserName(), room);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    /**
     * 
     * @param u
     * @param room
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    @RequestMapping(path = "/Rooms/{room}/Banderas/B",method = RequestMethod.DELETE)
    public  ResponseEntity<?> soltarBanderaB(@RequestBody Usuario u,@PathVariable Integer room) throws CamposDeGuerraNotFoundException{
        try {
            cdg.soltarBanderaB(u.getUserName(), room);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    /**
     * 
     * @param room
     * @return 
     */
    @RequestMapping(path = "/Rooms/{room}/Scorer",method = RequestMethod.GET)
    public ResponseEntity<?> getScorer(@PathVariable Integer room) {
        try {
            //Obtener datos
            return new ResponseEntity<>(cdg.obtenerScorer(room), HttpStatus.ACCEPTED);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * 
     * @param room
     * @return 
     */
    @RequestMapping(path = "/Rooms",method = RequestMethod.POST)
    public ResponseEntity<?> addRoom(@RequestBody Room room){
        try {
            //Obtener datos
            cdg.addRoom(room);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * 
     * @return 
     */
    @RequestMapping(path = "/Rooms",method = RequestMethod.GET)
    public ResponseEntity<?> getAllRooms(){
        try {
            //Obtener datos
            return new ResponseEntity<>(cdg.getAllRooms(),HttpStatus.ACCEPTED);
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(CamposDeGuerraAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
}
