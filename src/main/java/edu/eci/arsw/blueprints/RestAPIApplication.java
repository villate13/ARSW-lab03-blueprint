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
        
        Point[] points=new Point[]{new Point(0, 0),new Point(1, 1), new Point(1, 2),new Point(2, 2)};

        Blueprint bp = new Blueprint("Villa", "test1", points);
        bpServices.addNewBlueprint(bp);

        System.out.println(bpServices.getAllBlueprints());
    }

}
