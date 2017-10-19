/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.mom;

import edu.eci.arsw.camposdeguerra.model.Usuario;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 *
 * @author 2106913
 */
@Controller
public class STOMPMessagesHandler {

    
    @Autowired
    private SimpMessagingTemplate msgt;

    @MessageMapping("/sala.{idSala}")
    public void handlePointEvent(Usuario usuario,@DestinationVariable Integer idSala) throws Exception {
        System.out.print(usuario);
        System.out.print(idSala);
        msgt.convertAndSend("/topic/sala."+idSala, usuario);
    }
}
