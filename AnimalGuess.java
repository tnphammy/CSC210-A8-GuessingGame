import java.util.Scanner;
/** Contains Animal Guessing Game*/
public class AnimalGuess {
    public static void main(String[] args) {
        // Set up binary tree
        DecisionTree tree = new DecisionTree("Is it a Mammal?");
        tree.setLeft(new DecisionTree("Does it have Hooves?", new DecisionTree("Does it give milk?"), new DecisionTree("Is it a carnivore?")));
        tree.setRight(new DecisionTree("Is it a Reptile?", new DecisionTree("Crocodile"), new DecisionTree("Mosquito")));
        tree.getLeft().getLeft().setLeft(new DecisionTree("Cow"));
        tree.getLeft().getLeft().setRight(new DecisionTree("Horse"));
        tree.getLeft().getRight().setLeft(new DecisionTree("Is it in the dog family?"));
        tree.getLeft().getRight().setRight(new DecisionTree("Mouse"));
        tree.getLeft().getRight().getLeft().setLeft(new DecisionTree("Dog"));
        tree.getLeft().getRight().getLeft().setRight(new DecisionTree("Cat"));
        // System.out.println(tree.followPath("Y").getData());


        Scanner user = new Scanner(System.in); // set up Scanner object
        DecisionTree curr = tree; // start at the root
        while (curr.isBranch()) {
            // Talk to user
            System.out.println(curr.getData());
            // Get user response
            String response = user.nextLine().toLowerCase();
            // Interpret response
            if (response.equals("yes") || response.equals("y")) {
                response = "Y";
            }
            else if (response.equals("no") || response.equals("n")) {
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
        String grader = user.nextLine();
        if (grader.equals("yes") || grader.equals("y")) {
            System.out.println("Cool! This was fun.");
            user.close();
        }
        else if (grader.equals("no") || grader.equals("n")) {
            System.out.println("Sorry for that. What was your animal?");
            String realAnswer = user.nextLine();
            System.out.println("Type a yes or no question that would distinguish between a " + curr.getData() + " and a " + grader + ".");
            String betterQuestion = user.nextLine();
            // TO DO: write code to integrate user question into tree.
        }
        user.close();

    }
}