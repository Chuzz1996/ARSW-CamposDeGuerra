/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.mom;

import edu.eci.arsw.camposdeguerra.model.Bullet;
import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.impl.InMemoryCamposDeGuerraRoomPersistence;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
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
    @Autowired
    private InMemoryCamposDeGuerraRoomPersistence imrp;

    private final ConcurrentHashMap<Integer, AtomicInteger> personasEnsalas = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, String> estadoSalas = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, Timer> controlTiempo = new ConcurrentHashMap<>();

    @MessageMapping("/sala.{idSala}")
    public void listoParaJugar(String estado, @DestinationVariable Integer idSala) throws Exception {
        if (estado.equals("listo")) {
            if (personasEnsalas.containsKey(idSala)) {
                AtomicInteger temp = personasEnsalas.get(idSala);
                msgt.convertAndSend("/topic/sala." + idSala + "/pos", temp.addAndGet(1));
                personasEnsalas.putIfAbsent(idSala, temp);
            } else {
                AtomicInteger temp = new AtomicInteger(1);
                personasEnsalas.putIfAbsent(idSala, temp);
                msgt.convertAndSend("/topic/sala." + idSala + "/pos", temp.get());
                estadoSalas.putIfAbsent(idSala, "nojugando");
            }
            if (personasEnsalas.get(idSala).get() >= 4 && estadoSalas.get(idSala).equals("nojugando")) {
                msgt.convertAndSend("/topic/sala." + idSala, "Pueden Comenzar");
                estadoSalas.replace(idSala, "jugando");
                Timer temp = new Timer();
                temp.schedule(new java.util.TimerTask() {
                    @Override
                    public void run() {
                        msgt.convertAndSend("/topic/sala." + idSala + "/endGame", "Termino el juego");
                    }
                }, 180000);
                controlTiempo.putIfAbsent(idSala, temp);
            } else if (personasEnsalas.get(idSala).get() >= 4 && estadoSalas.get(idSala).equals("jugando")) {
                msgt.convertAndSend("/topic/sala." + idSala, "Pueden Comenzar");
            }
        }
    }


    @MessageMapping("/sala.{idSala}/bullets")
    public void reportarBala(Bullet b, @DestinationVariable Integer idSala) throws Exception {
        msgt.convertAndSend("/topic/sala." + idSala + "/bullets", b);
    }

    @MessageMapping("/sala.{idSala}/A")
    public void reportarInfoTeamA(Usuario u, @DestinationVariable Integer idSala) throws Exception {
        msgt.convertAndSend("/topic/sala." + idSala + "/A", u);
    }

    @MessageMapping("/sala.{idSala}/B")
    public void reportarInfoTeamB(Usuario u, @DestinationVariable Integer idSala) throws Exception {
        msgt.convertAndSend("/topic/sala." + idSala + "/B", u);
    }
}
