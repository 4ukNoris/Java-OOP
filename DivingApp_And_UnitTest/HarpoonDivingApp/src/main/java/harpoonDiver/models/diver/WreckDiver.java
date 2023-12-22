package harpoonDiver.models.diver;

public class WreckDiver extends BaseDiver {
    private static final int INITIAL_OXYGEN = 150;
    public WreckDiver(String name) {
        super(name, INITIAL_OXYGEN);
    }
}
