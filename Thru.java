/**
 * AUTHOR: Justin Nichols
 * FILE: Thru.java
 * ASSIGNMENT: Programming Assignment 10 - KineticSculpture
 * COURSE: CSC210 Spring 2019, Section D
 * PURPOSE: keeps information about a thru node (input or passthrough) in the
 * the kinetic sculpture.
 */

public class Thru extends Node {

    public Thru(int id, int tlX, int tlY, int sideLen) {
        super(id, tlX, tlY, sideLen);
    }

    /*
     * takes an inMarble and makes it an outMarble
     */
    public void process() {
        if (!inMarbles.isEmpty()) {
            String marble = inMarbles.remove(0);
            outMarbles.add(marble);
        }
    }
}
