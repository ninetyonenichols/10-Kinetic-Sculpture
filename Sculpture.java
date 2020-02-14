import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * AUTHOR: Justin Nichols
 * FILE: Sculpture.java
 * ASSIGNMENT: Programming Assignment 10 - KineticSculpture
 * COURSE: CSC210 Spring 2019, Section D
 * PURPOSE: keeps information about the kinetic sculpture. Essentially, this
 * file is the top-level file of the model in the MVC design pattern.
 */

public class Sculpture {
    private final int SIDE_LEN;
    private Map<Integer, Node> id2Node = new TreeMap<Integer, Node>();
    private Set<Node> nodes = new HashSet<Node>();
    private Set<Edge> edges = new HashSet<Edge>();
    private Node inNode = null;

    public Sculpture(int sideLen) { SIDE_LEN = sideLen; }

    /*
     * adds a node to the sculpture
     *
     * @param String[] info
     * each element contains info about the array
     * 
     * @param int id
     * the node's id
     * 
     * @param String type
     * the node's type
     * 
     * @ return Node node
     * the node that was added to the sculpture
     */
    public Node addNode(String[] info, int id, String type) {
        int tlX = Integer.parseInt(info[2].trim().substring(1));
        int tlY = Integer.parseInt(
                info[3].trim().substring(0, info[3].trim().length() - 1));

        Node node = null;
        switch (type) {
        case "input":
            node = new Thru(id, tlX, tlY, SIDE_LEN);
            inNode = node;
            break;
        case "passthrough":
            node = new Thru(id, tlX, tlY, SIDE_LEN);
            break;
        case "sink":
            node = new Sink(id, tlX, tlY, SIDE_LEN);
        }

        id2Node.put(id, node);
        nodes.add(node);

        return node;
    }


    /*
     * initializes the input marbles for the input node
     * 
     * @param String infoLine
     * a line from the infile containing each input-marble's color-name
     */
    public void giveInNodeInMarbles(String infoLine) {
        String[] cols = infoLine.split(":|,");
        cols = Arrays.copyOfRange(cols, 1, cols.length);
        for (String col : cols) {
            inNode.addInMarble(col);
        }
    }

    public void addEdge(Edge edge) { edges.add(edge); }

    public Map<Integer, Node> getId2Node() { return id2Node; }

    public Node getInNode() { return inNode; }

    public Set<Node> getNodes() { return nodes; }

    public Set<Edge> getEdges() { return edges; }
}
