/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.mom;

import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.services.CamposDeGuerraServices;
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

    private ConcurrentHashMap<Integer, AtomicInteger> personasEnsalas = new ConcurrentHashMap<>();

    @MessageMapping("/sala.{idSala}")
    public void listoParaJugar(String estado, @DestinationVariable Integer idSala) throws Exception {
        System.out.println("LLEGO AL MOM :'D");
        System.out.println(estado);
        if (estado.equals("listo")) {
            if (personasEnsalas.containsKey(idSala)) {
                AtomicInteger temp = personasEnsalas.get(idSala);
                temp.addAndGet(1);
                personasEnsalas.putIfAbsent(idSala, temp);
            } else {
                AtomicInteger temp = new AtomicInteger(1);
                personasEnsalas.putIfAbsent(idSala, temp);
            }
            if (personasEnsalas.get(idSala).get() >= 4) {
                System.out.println("idSala:"+ idSala+ " Estado: "+estado+" Personas en Sala: "+personasEnsalas.get(idSala).get());
                msgt.convertAndSend("/topic/sala." + idSala, "Pueden Comenzar");
            }
        }
        

    }
}
