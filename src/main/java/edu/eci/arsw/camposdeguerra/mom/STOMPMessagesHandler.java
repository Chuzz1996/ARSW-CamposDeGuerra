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
    private ConcurrentHashMap<AtomicInteger, ConcurrentLinkedQueue<Usuario>> sala = new ConcurrentHashMap<>();
    private AtomicInteger idSala = new AtomicInteger(0);

    @MessageMapping("/sala")
    public void handlePointEvent(Usuario usuario) throws Exception {
        if (sala.contains(idSala)) {
            if (sala.get(idSala).size() > 5) {
                ConcurrentLinkedQueue<Usuario> tmp = new ConcurrentLinkedQueue<>();
                tmp.add(usuario);
                idSala.addAndGet(1);
                sala.putIfAbsent(idSala, tmp);
                msgt.convertAndSend("/topic/sala."+idSala.toString(), usuario);
            } else {
                sala.get(idSala).add(usuario);
                msgt.convertAndSend("/topic/sala."+idSala.toString(), usuario);
            }
        } else {
            ConcurrentLinkedQueue<Usuario> tmp = new ConcurrentLinkedQueue<>();
            tmp.add(usuario);
            idSala.addAndGet(1);
            sala.putIfAbsent(idSala, tmp);
            msgt.convertAndSend("/topic/sala."+idSala.toString(), usuario);
        }
    }
}
