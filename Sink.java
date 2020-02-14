/**
 * AUTHOR: Justin Nichols
 * FILE: Sink.java
 * ASSIGNMENT: Programming Assignment 10 - KineticSculpture
 * COURSE: CSC210 Spring 2019, Section D
 * PURPOSE: keeps information about a sink node in the the kinetic sculpture.
 */

public class Sink extends Node {

    public Sink(int id, int tlX, int tlY, int sideLen) { 
        super(id, tlX, tlY, sideLen);
        }

    /*
     * removes a marble from the sink
     */
    public void process() {
        if (!inMarbles.isEmpty()) {
            inMarbles.remove(0);
        }
    }
}
