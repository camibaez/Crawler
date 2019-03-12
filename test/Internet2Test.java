/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import webcrawler.Logs;

/**
 *
 * @author User
 */
public class Internet2Test {

    public Internet2Test() {
    }

    @Before
    public void setUp() {
        webcrawler.Internet.loadInstance("data/internet2.json");
    }

    @Test
    public void internet1Test() {
        System.out.println("Internet2.json Test");
        new webcrawler.CrawlingCentral().run();

        Logs logs = webcrawler.Logs.getInstance();
        Set<String> expected = new HashSet<>(Arrays.asList("http://foo.bar.com/p1", "http://foo.bar.com/p2",
                                                            "http://foo.bar.com/p3", "http://foo.bar.com/p4",
                                                            "http://foo.bar.com/p5"
                                                           ));
        assertEquals(expected, logs.getSucccess());

        expected = new HashSet<>(Arrays.asList("http://foo.bar.com/p1"));
        assertEquals(expected, logs.getSkipped());

        expected = new HashSet<>(Arrays.asList());
        assertEquals(expected, logs.getErrors());
    }
}
