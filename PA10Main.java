
/**
 * AUTHOR: Justin Nichols
 * FILE: PA10Main.java
 * ASSIGNMENT: Programming Assignment 10 - KineticSculpture
 * COURSE: CSC210 Spring 2019, Section D
 * PURPOSE: constructs and renders a kinetic sculpture (from an infile) using
 * model-view-controller (MCV) design pattern. Specifically, this file is the
 * controller
 *              
 * 
 * USAGE: 
 * enter the infile name/path in the input box
 *     
 *  EXAMPLE INFILE (CREATED BY INSTRUCTORS, NOT BY ME):
 *   
 * ------------------------------------------------------------
 * | delay: 1                                                 |
 * | input: RED, BLUE, YELLOW, GREEN, PURPLE, PINK, BLACK     |
 * | 0: input, (20,20)                                        |
 * | 1: passthrough, (70,40)                                  |
 * | 2: passthrough, (80,100)                                 |
 * | 3: passthrough, (80, 200)                                |
 * | 4: passthrough, (140, 100)                               | 
 * | 5: passthrough, (140, 200)                               |
 * | 6: sink, (600,150)                                       |
 * | 0 -> 1                                                   |
 * | 0 -> 2                                                   |
 * | 0 -> 3                                                   |
 * | 1 -> 5                                                   |
 * | 2 -> 4                                                   |
 * | 3 -> 4                                                   |
 * | 4 -> 6                                                   |
 * | 5 -> 6                                                   |
 * ------------------------------------------------------------
 *  
 * Input-file format must match that shown above.
 * No support exists for any further commands
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PA10Main extends Application {
    private final int WIN_HGT = 250;
    private final int WIN_WID = 650;
    private JavaFXView view = new JavaFXView(WIN_WID, WIN_HGT);
    private final int SIDE_LEN = 10;
    private int delay;
    private Group root = view.root;
    private Stage primaryStage;
    private Scanner infile = null;
    private Sculpture sculpture;
    private Map<Integer, Node> id2Node = new HashMap<Integer, Node>();

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("PA10");
        primaryStage.setScene(new Scene(mkInBox()));
        primaryStage.show();
    }

    /*
     * makes the HBox (the first scene-graph) which will accept the infile name
     * 
     * @return HBox inBox
     * the HBox mentioned above
     */
    public HBox mkInBox() {
        HBox inBox = new HBox();

        TextField fnameIn = new TextField();
        fnameIn.setPrefWidth(300);

        Button button = new Button("Process");
        button.setOnAction(event -> {
            primaryStage.setScene(new Scene(root));
            mkSculpture(fnameIn.getText());
            playSculpture();
        });

        inBox.getChildren().add(new Text("Enter infile name --->  "));
        inBox.getChildren().add(fnameIn);
        inBox.getChildren().add(button);

        return inBox;
    }

    /*
     * initializes the sculpture
     * 
     * @param String fname
     * the name of the infile. Provided by the user at runtime
     */
    public void mkSculpture(String fname) {
        try {
            infile = new Scanner(new File(fname));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // reading delay-value. Special case
        String[] delayInfoArray = infile.nextLine().split(" ");
        delay = Integer.parseInt(delayInfoArray[1]);

        // reading color-names for input-marbles. Special case
        String inMarbles = infile.nextLine();
        sculpture = new Sculpture(SIDE_LEN);

        // reading nodes and edges. General case
        while (infile.hasNextLine()) {
            String infoLine = infile.nextLine();

            if (!infoLine.equals("") && !infoLine.split(" ")[1].equals("->")) {
                mkNode(infoLine);
            } else if (!infoLine.equals("")) {
                mkEdge(infoLine);
            }
        }

        sculpture.giveInNodeInMarbles(inMarbles);
    }

    /*
     * sets up a KeyFrame that calls the sculpture's mv command with every key
     */
    private void playSculpture() {
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        final KeyFrame kf = new KeyFrame(Duration.seconds(1.5),
                (ActionEvent e) -> mvSculpture());

        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    /*
     * tells the model and view to make a node
     * 
     * @param String infoLine
     * contains info about the node
     */
    public void mkNode(String infoLine) {
        String[] info = infoLine.split(":|,");
        int id = Integer.parseInt(info[0]);
        String type = info[1].trim();
        Node node = sculpture.addNode(info, id, type);
        id2Node.put(id, node);
        view.initNode(node.getId(), node.getTL_X(), node.getTL_Y(), SIDE_LEN,
                type);
    }

    /*
     * tells the model and view to make an edge
     * 
     * @param String infoLine
     * contains info about the node
     */
    public void mkEdge(String infoLine) {
        int intlId = Integer.parseInt(infoLine.split(" ")[0]);
        int tmnlId = Integer.parseInt(infoLine.split(" ")[2]);
        Node intlNode = id2Node.get(intlId);
        Node tmnlNode = id2Node.get(tmnlId);

        sculpture.addEdge(new Edge(intlNode, tmnlNode));
        view.initEdge(intlId, tmnlId, intlNode.getOutX(), intlNode.getOutY(),
                tmnlNode.getInX(), tmnlNode.getInY());
    }

    /*
     * moves the marbles around the sculpture (in both the model and view)
     */
    public void mvSculpture() {

        // processing nodes
        view.clearEdges();
        for (Node node : sculpture.getNodes()) {
        node.process();
        }
        
        // processing edges
        Set<Node> alteredNodes = new HashSet<Node>();
        for (Edge edge : sculpture.getEdges()) {
            if (edge.needsProcessing()) {
                int intlId = edge.getIntlNode().getId();
                int tmnlId = edge.getTmnlNode().getId();
                Node intlNode = id2Node.get(intlId);
                String col = intlNode.get1stOutMarble();
                view.edgeTransition(intlId, tmnlId, col, delay);
                edge.process();
                alteredNodes.add(intlNode);
            }
        }
        
        for (Node node : alteredNodes) {
            node.rmOutMarble();
        }
    }
}
