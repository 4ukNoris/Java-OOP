package robotService.entities.robot;

public class MaleRobot extends BaseRobot {
    private static final int INITIAL_KILOGRAMS = 9;
    private static final int INCREASE_KILOGRAMS = 3;
    public MaleRobot(String name, String kind, double price) {
        super(name, kind, INITIAL_KILOGRAMS, price);
    }

    @Override
    public void eating() {
        super.setKilograms(INITIAL_KILOGRAMS + INCREASE_KILOGRAMS);
    }
}
