package robotService.entities.robot;

public class FemaleRobot extends BaseRobot {
    private static final int INITIAL_KILOGRAMS = 7;
    private static final int INCREASE_KILOGRAMS = 1;
    public FemaleRobot(String name, String kind, double price) {
        super(name, kind, INITIAL_KILOGRAMS, price);
    }

    //TODO: Can only live in SecondaryService!
    @Override
    public void eating() {
        super.setKilograms(INITIAL_KILOGRAMS + INCREASE_KILOGRAMS);
    }
}
