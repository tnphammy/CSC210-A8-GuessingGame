import java.util.Scanner;
/** Contains Animal Guessing Game*/
public class AnimalGuess {
    /**
     * Inserts a new animal into the tree for more details
     * @param tree the decision tree or node
     * @param question the provided question
     * @param answer the corresponding answer 
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
        }
        else if (!interpret(answer)) {
            tree.setRight(new DecisionTree(animal));
            tree.setLeft(guess);
        }
    }
    
    /**
     * Interprets a user's response into a boolean value
     * @param answer the user's response string
     * @return true or false
     */
    public static Boolean interpret(String answer) {
        answer.toLowerCase();
        if (answer.equals("yes") || answer.equals("y")) {
            return true;
        }
        else if (answer.equals("no") || answer.equals("n")) {
            return false;
        }
        else {
            throw new UnsupportedOperationException("Unsupported input.");
        }
    }
    public static void main(String[] args) {
        // Hard-code binary tree 
        // DecisionTree tree = new DecisionTree("Is it a Mammal?");
        // tree.setLeft(new DecisionTree("Does it have Hooves?", new DecisionTree("Does it give milk?"), new DecisionTree("Is it a carnivore?")));
        // tree.setRight(new DecisionTree("Is it a Reptile?", new DecisionTree("Crocodile"), new DecisionTree("Mosquito")));
        // tree.getLeft().getLeft().setLeft(new DecisionTree("Cow"));
        // tree.getLeft().getLeft().setRight(new DecisionTree("Horse"));
        // tree.getLeft().getRight().setLeft(new DecisionTree("Is it in the dog family?"));
        // tree.getLeft().getRight().setRight(new DecisionTree("Mouse"));
        // tree.getLeft().getRight().getLeft().setLeft(new DecisionTree("Dog"));
        // tree.getLeft().getRight().getLeft().setRight(new DecisionTree("Cat"));
        // System.out.println(tree.followPath("Y").getData());

        DecisionTree tree = new DecisionTree("mouse");

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
                    }
                    else if (!interpret(response)) {
                        response = "N";
                    }
                    // Follow user's response on the DecisionTree
                    if(response.equals("Y") || response.equals("N")) {
                        curr = curr.followPath(response);
                    }
                    else {
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
                    System.out.println("Type a yes or no question that would distinguish between a " + curr.getData() + " and a " + realAnimal + ".");
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