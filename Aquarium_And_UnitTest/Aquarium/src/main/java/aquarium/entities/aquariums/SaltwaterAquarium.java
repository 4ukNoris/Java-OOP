package aquarium.entities.aquariums;

public class SaltwaterAquarium extends BaseAquarium {
    private static final int INITIAL_DEFAULT_CAPACITY = 25;
    public SaltwaterAquarium(String name) {
        super(name, INITIAL_DEFAULT_CAPACITY);
    }
}
