import java.util.ArrayList;
import java.util.List;

/**
 * AUTHOR: Justin Nichols
 * FILE: Node.java
 * ASSIGNMENT: Programming Assignment 10 - KineticSculpture
 * COURSE: CSC210 Spring 2019, Section D
 * PURPOSE: keeps information about a node in the the kinetic sculpture.
 */

abstract class Node {
    protected final int ID;
    protected final int SIDE_LEN;
    protected List<String> inMarbles = new ArrayList<String>();
    protected List<String> outMarbles = new ArrayList<String>();
    protected final int TL_X;
    protected final int TL_Y;


    public Node(int id, int tlX, int tlY, int sideLen) {
        ID = id;
        TL_X = tlX;
        TL_Y = tlY;
        SIDE_LEN = sideLen;
    }

    public abstract void process();

    public int getId() { return ID; }

    public void addInMarble(String inMarble) { inMarbles.add(inMarble); }

    public List<String> getOutMarbles() { return outMarbles; }

    public String get1stOutMarble() { return outMarbles.get(0); }

    public void rmOutMarble() { outMarbles.remove(0); }

    public int getTL_X() { return TL_X; }

    public int getTL_Y() { return TL_Y; }

    // below are coords for input / output ports
    public int getInX() { return TL_X; }

    public int getInY() { return TL_Y + SIDE_LEN / 2; }

    public int getOutX() { return TL_X + SIDE_LEN; }

    public int getOutY() { return TL_Y + SIDE_LEN / 2; }
}
