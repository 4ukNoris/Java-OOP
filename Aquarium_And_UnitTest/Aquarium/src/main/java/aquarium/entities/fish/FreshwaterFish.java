package aquarium.entities.fish;

public class FreshwaterFish extends BaseFish {
    private static final int INITIAL_SIZE = 3;
    private static final int INCREASE_SIZE = 3;

    public FreshwaterFish(String name, String species, double price) {
        super(name, species, price);
        setSize(INITIAL_SIZE);
    }

    //TODO: Can only live in FreshwaterAquarium!

    @Override
    public void eat() {
        int newSize = getSize() + INCREASE_SIZE;
        setSize(newSize);
    }
}
