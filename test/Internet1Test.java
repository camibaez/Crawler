/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class Internet1Test {
    
    public Internet1Test() {
    }
    
    
    
    @Before
    public void setUp() {
        webcrawler.Internet.loadInstance("data/internet1.json");
    }
    
    

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void internet1Test(){
        System.out.println("Internet1.json Test");
        new webcrawler.CrawlingCentral().start();
    }
}
