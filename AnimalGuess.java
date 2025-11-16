import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
import java.io.File;
import java.io.FileNotFoundException;

/** Contains Animal Guessing Game */
public class AnimalGuess {
    /**
     * Inserts a new animal into the tree for more details
     * 
     * @param tree     the decision tree or node
     * @param question the provided question
     * @param answer   the corresponding answer
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
     * @param q
     * @return
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

    public static void main(String[] args) {
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