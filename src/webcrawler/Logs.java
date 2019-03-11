/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is only for logs purpose.
 * @author Camilo Baez
 */
public class Logs {
    private Set<String> succcess = new HashSet<>();
    private Set<String> skipped = new HashSet<>();
    private Set<String> errors = new HashSet<>();
    
    private Logs() {
    }
    
    public static Logs getInstance() {
        return LogsHolder.INSTANCE;
    }

    public void showResults() {
        System.out.println("Success: " + succcess);
        System.out.println("Skipped: " + skipped);
        System.out.println("Errors: " + errors);
    }
    
    private static class LogsHolder {

        private static final Logs INSTANCE = new Logs();
    }

    /**
     * @return the succcess
     */
    public Set<String> getSucccess() {
        return succcess;
    }

    /**
     * @return the skipped
     */
    public Set<String> getSkipped() {
        return skipped;
    }

    /**
     * @return the errors
     */
    public Set<String> getErrors() {
        return errors;
    }

    
    
    
}
