/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.mom;



import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
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
    SimpMessagingTemplate msgt;
    ConcurrentHashMap<String, ConcurrentLinkedQueue<?>> test = new ConcurrentHashMap<>();
	
	
    /*
    @MessageMapping("/newpoint.{numdibujo}")
    public void handlePointEvent(Point pt, @DestinationVariable String numdibujo) throws Exception {
        
        if (!ServerPointsPolygon.containsKey(numdibujo)) {
            ConcurrentLinkedQueue<Point> pointsPolygon = new ConcurrentLinkedQueue<>();
            ServerPointsPolygon.putIfAbsent(numdibujo, pointsPolygon);
        }
        ServerPointsPolygon.get(numdibujo).add(pt);
        msgt.convertAndSend("/topic/newpoint." + numdibujo, pt);
        if (ServerPointsPolygon.get(numdibujo).size() >=4) {
            msgt.convertAndSend("/topic/newpolygon." + numdibujo, ServerPointsPolygon.get(numdibujo));
        }
    }
    */
}
