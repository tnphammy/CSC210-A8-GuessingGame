/** Contains Animal Guessing Game*/
public class AnimalGuess {
    public static void main(String[] args) {
        DecisionTree tree = new DecisionTree("Is it a Mammal?");
        tree.setLeft(new DecisionTree("Does it have Hooves?", new DecisionTree("Make milk?"), new DecisionTree("Carnivore?")));
        tree.setRight(new DecisionTree("Is it a Reptile?"));

        System.out.println(tree.getData());
        System.out.println(tree.getLeft().getLeft().getData());

    }
}