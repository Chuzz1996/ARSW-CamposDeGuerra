/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.mom;

import edu.eci.arsw.camposdeguerra.model.Bullet;
import edu.eci.arsw.camposdeguerra.model.Usuario;
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
    private controlTimeAndLogical l;

 

    @MessageMapping("/sala.{idSala}")
    public void listoParaJugar(String estado, @DestinationVariable Integer idSala) throws Exception {
        Integer pos=l.listoParaJugar(estado, idSala);
        msgt.convertAndSend("/topic/sala." + idSala + "/pos",pos);
        if (l.roomFull(idSala)) {
            msgt.convertAndSend("/topic/sala." + idSala, "Pueden comensar");
            msgt.convertAndSend("/topic/sala." + idSala + "/tiempo", l.getTime(idSala));
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
    
    @MessageMapping("/sala.{idSala}/tiempo")
    public void reportarEndGame(String ans,@DestinationVariable Integer idSala) throws Exception {
        l.gameEnded(idSala);
        msgt.convertAndSend("/topic/sala." + idSala + "/tiempo", "ok");
    }
    
}
