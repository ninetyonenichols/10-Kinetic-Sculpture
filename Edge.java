/**
 * AUTHOR: Justin Nichols
 * FILE: Edge.java
 * ASSIGNMENT: Programming Assignment 10 - KineticSculpture
 * COURSE: CSC210 Spring 2019, Section D
 * PURPOSE: keeps information about an edge the kinetic sculpture. 
 */

public class Edge {
    private final Node INTL_NODE;
    private final Node TMNL_NODE;

    public Edge(Node intlNode, Node tmnlNode) {
        INTL_NODE = intlNode;
        TMNL_NODE = tmnlNode;
    }

    /*
     * moves a marble from an initial node to a terminal node
     */
    public void process() {
        String marbleClone = INTL_NODE.get1stOutMarble();
        TMNL_NODE.addInMarble(marbleClone);
    }

    /*
     * determines whether this edge needs to be processed at a given KeyFrame
     * 
     * @return boolean
     * true if needs processing. false otherwise
     */
    public boolean needsProcessing() {
        return !INTL_NODE.getOutMarbles().isEmpty();
    }

    public Node getIntlNode() { return INTL_NODE; }

    public Node getTmnlNode() { return TMNL_NODE; }
}
