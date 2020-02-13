/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jmvillatei
 */
@SpringBootApplication
public class RestAPIApplication {

    static BlueprintsServices bpServices;
    
    /**
     * @param args the command line arguments
     * @throws edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException
     * @throws edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException
     */
    public static void main(String[] args) throws BlueprintPersistenceException, BlueprintNotFoundException {

        ApplicationContext bPrints = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        bpServices = bPrints.getBean(BlueprintsServices.class);
        
        Point[] points1=new Point[]{new Point(0, 0),new Point(10, 10), new Point(15, 20),new Point(20, 20)};
        Blueprint bp = new Blueprint("Villate", "enProduccion", points1);
        bpServices.addNewBlueprint(bp);
        
        Point[] points2=new Point[]{new Point(1, 1),new Point(10, 10), new Point(10, 20),new Point(20, 20)};
        Blueprint bp2 = new Blueprint("Isaza", "enProduccion2", points2);
        bpServices.addNewBlueprint(bp2);
        
        

        System.out.println(bpServices.getAllBlueprints());
        System.out.println(bpServices.getBlueprint("Villate", "enProduccion").getAuthor());
        System.out.println(bpServices.getBlueprint("Isaza", "enProduccion2").getAuthor());
    }

}
