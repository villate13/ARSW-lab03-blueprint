/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    ApplicationContext bPrints = new ClassPathXmlApplicationContext("applicationContext.xml");
    BlueprintsServices bpServices = bPrints.getBean(BlueprintsServices.class);

    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts0 = new Point[]{new Point(40, 40), new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);

        ibpp.saveBlueprint(bp0);

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        ibpp.saveBlueprint(bp);

        assertNotNull("Loading a previously stored blueprint returned null.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()));

        assertEquals("Loading a previously stored blueprint returned a different blueprint.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);

    }

    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        Point[] pts2 = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint bp2 = new Blueprint("john", "thepaint", pts2);

        try {
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        } catch (BlueprintPersistenceException ex) {

        }

    }
    
    @Test
    public void blueprintFilterRedundancy() throws BlueprintPersistenceException, BlueprintNotFoundException {

        Point[] points=new Point[]{new Point(10, 20),new Point(30, 40), new Point(50, 60),new Point(70, 80)};

        Blueprint bp = new Blueprint("Juan", "test1", points);
        bpServices.addNewBlueprint(bp);

        assertEquals(bpServices.getBlueprint("Juan", "test1").getPoints().size(),
                2);
    }

    @Test
    public void blueprintFilterSubsampling() throws BlueprintPersistenceException, BlueprintNotFoundException {

        Point[] points=new Point[]{new Point(1, 1),new Point(10, 10), new Point(14, 20),new Point(20, 20)};

        Blueprint bp = new Blueprint("Manuel", "Test2", points);
        bpServices.addNewBlueprint(bp);

        assertEquals(bpServices.getBlueprint("Manuel", "Test2").getPoints().size(),
                2);
    }
    

    @Test
    public void blueprintByAuthorTest() throws BlueprintNotFoundException, BlueprintPersistenceException {
        
        Point[] points2=new Point[]{new Point(1, 1),new Point(10, 10), new Point(10, 20),new Point(20, 20)};
        Blueprint bp2 = new Blueprint("Isaza", "Test3", points2);
        bpServices.addNewBlueprint(bp2);

        assertEquals(bpServices.getBlueprint("Isaza", "Test3").getAuthor(), "Isaza");
    }

    


    


    
}
