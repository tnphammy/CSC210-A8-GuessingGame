import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/** Contains Animal Guessing Game */
public class AnimalGuess {
    /**
     * Inserts a new animal into the tree for more details
     * 
     * @param tree     the decision tree or node
     * @param question the provided question
     * @param answer   the corresponding answer
     * @param animal the provided animal
     */
    public static void improveTree(DecisionTree tree, String question, String answer, String animal) {
        // Store (wrong) program guess
        DecisionTree guess = new DecisionTree(tree.getData(), tree.getLeft(), tree.getRight());
        // Replace program guess node with question
        tree.setData(question);
        // Point answer to the right directions
        if (interpret(answer)) {
            tree.setLeft(new DecisionTree(animal));
            tree.setRight(guess);
        } else if (!interpret(answer)) {
            tree.setRight(new DecisionTree(animal));
            tree.setLeft(guess);
        }
    }

    /**
     * Convert a queue to a String
     * 
     * @param q the queue 
     * @return the String representation of the queue
     */
    public static String qtoString(Queue<String> q) {
        String output = "";
        while (!q.isEmpty()) {
            output += q.poll(); /* gets the data after the space */
        }
        return output;
    }

    /**
     * Read a line and build a tree
     * 
     * @param line the instructions
     */
    public static DecisionTree setPath(DecisionTree tree, String line) {
        // Build a Queue representation of the line
        Queue<String> q = new LinkedList<>();
        for (char c : line.toCharArray()) {
            q.add(String.valueOf(c));
        }
        // Read Queue
        DecisionTree curr = tree;
        // Follow Q while valid and not a leaf
        while (curr.isBranch()) {
            // 1. get direction
            String direction = q.poll();
            // 2. follow direction -> update curr
            if (curr.getLeft() == null || curr.getRight() == null) { /** Add left/right to a root */
                q.remove(); /* gets rid of space */
                // insert leaf correctly
                String input = "";
                while (!q.isEmpty()) {
                    input += q.poll(); /* gets the data after the space */
                }
                if (interpret(direction)) { /* Answer is "yes" */
                    curr.setLeft(new DecisionTree(input));
                    break;
                } else if (!interpret(direction)) { /* Answer is "no" */
                    curr.setRight(new DecisionTree(input));
                    break;
                }
            } else {
                curr = curr.followPath(direction);
            }
        }
        // When Q is a leaf, set the data
        if (curr.isLeaf()) {
            // Root case
            if (q.peek().equals(" ")) {
                if (q.isEmpty()) {
                }
                q.poll();
                String input = "";
                while (!q.isEmpty()) {
                    input += q.poll(); /* gets the data after the space */
                }
                curr.setData(input);
            } else {
                // get direction for new leaf
                String direction = q.poll();
                q.remove(); /* gets rid of space */

                // insert leaf correctly
                String input = "";
                while (!q.isEmpty()) {
                    input += q.poll(); /* gets the data after the space */
                }
                if (interpret(direction)) { /* Answer is "yes" */
                    curr.setLeft(new DecisionTree(input));
                } else if (!interpret(direction)) { /* Answer is "no" */
                    curr.setRight(new DecisionTree(input));
                }
            }
        }
        return tree;
    }

    /** Encodes a tree into a String format
     * @param tree the tree being interpreted
     */
    public static String encodeTree(DecisionTree tree) {
        return encoderHelper(tree, "");
    }

    /**
     * Helper function to read each level of binary tree (Breadth-first)
     * @param node the current node
     * @param path the String representation of the path to the node
     * @return the entirety of the tree as a String
     */
    public static String encoderHelper(DecisionTree node, String path) {
        // Base case: Null
        if (node == null) {
            return "";
        }
        // Base case: One line
        String line = path + " " + node.getData() + "\n";
    
        // // Recursive step: Read Left and Right 
        String leftLine = encoderHelper(node.getLeft(), path + "Y");
        String rightLine = encoderHelper(node.getRight(), path + "N");
        return line + leftLine + rightLine;
    }
    


    /**
     * Interprets a user's response into a boolean value
     * 
     * @param answer the user's response string
     * @return true or false
     */
    public static Boolean interpret(String answer) {
        answer = answer.toLowerCase();
        if (answer.equals("yes") || answer.equals("y")) {
            return true;
        } else if (answer.equals("no") || answer.equals("n")) {
            return false;
        } else {
            System.out.println("answer was: " + answer);
            throw new UnsupportedOperationException("Unsupported input.");
        }
    }

    /**
     * Main method for running and debugging
     * @param args input
    * @throws FileNotFoundException 
    */
    public static void main(String[] args) throws FileNotFoundException {
        // Makes first tree
        DecisionTree tree = new DecisionTree("root");
        // Reads file to make tree
        String filePath = "AnimalTree.txt";
        try (Scanner reader = new Scanner(new File(filePath))) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                tree = setPath(tree, line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

        Boolean gameContinues = true; /* ensures game loop */
        try (Scanner user = new Scanner(System.in)) { // set up Scanner object
            while (gameContinues) {
                DecisionTree curr = tree; // start at the root
                while (curr.isBranch()) {
                    // Talk to user
                    System.out.println(curr.getData());
                    // Get user response
                    String response = user.nextLine().toLowerCase();
                    // Interpret response
                    if (interpret(response)) {
                        response = "Y";
                    } else if (!interpret(response)) {
                        response = "N";
                    }
                    // Follow user's response on the DecisionTree
                    if (response.equals("Y") || response.equals("N")) {
                        curr = curr.followPath(response);
                    } else {
                        throw new UnsupportedOperationException("Invalid response.");
                    }
                }
                // Checks Program Answer
                if (curr.isLeaf()) {
                    System.out.println("Is it a " + curr.getData() + "?");
                }
                // Happy case
                String grader = user.nextLine().toLowerCase();
                if (interpret(grader)) {
                    System.out.println("Cool! This was fun.");
                }
                // Improvement case
                else if (!interpret(grader)) {
                    // Obtains new animal
                    System.out.println("Sorry for that. What was your animal?");
                    String realAnimal = user.nextLine();
                    // Obtains question
                    System.out.println("Type a yes or no question that would distinguish between a " + curr.getData()
                            + " and a " + realAnimal + ".");
                    String betterQuestion = user.nextLine();
                    // Obtains corresponding answer
                    System.out.println("Would you answer yes to this question for the " + realAnimal + "?");
                    String betterAnswer = user.nextLine().toLowerCase();
                    // Run improvement
                    improveTree(curr, betterQuestion, betterAnswer, realAnimal);
                    // Store current tree for later
                    
                    String encoded = encodeTree(tree);

                    try (PrintWriter writer = new PrintWriter("AnimalTree.txt")) {
                        writer.print(encoded);
                    }
                }

                System.out.println("Play again?");
                String userWish = user.nextLine().toLowerCase();
                if (!interpret(userWish)) {
                    gameContinues = false;
                }
            }
        }
    }
}