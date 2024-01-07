package onlineShop.models.products.components;

public class Motherboard extends BaseComponent {
    private static final double PERFORMANCE_MULTIPLIER = 1.25;
    public Motherboard(int id, String manufacturer, String model, double price, double overallPerformance, int generation) {
        super(id, manufacturer, model, price, overallPerformance, generation);
        this.setOverallPerformance(overallPerformance);
    }
    //TODO: the multiplier is 1.25

    @Override
    protected void setOverallPerformance(double overallPerformance) {
        super.setOverallPerformance(overallPerformance * PERFORMANCE_MULTIPLIER);
    }
}
