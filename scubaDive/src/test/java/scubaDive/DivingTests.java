package scubaDive;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DivingTests {
    private static final String DIVER1_NAME = "Gosho";
    private static final String DIVER2_NAME = "Ivan";
    private static final String DIVER3_NAME = "Cvetan";
    private static final String DIVING_NAME = "Sinemoretz";
    private static final double DEFAULT_OXYGEN_DIVER1 = 70;
    private static final double DEFAULT_OXYGEN_DIVER2 = 80;
    private static final double DEFAULT_OXYGEN_DIVER3 = 90;
    private static final int INITIAL_CAPACITY = 3;
    private DeepWaterDiver diver1;
    private DeepWaterDiver diver2;
    private DeepWaterDiver diver3;
    private Diving diving;

    @Before
    public void divingSetUp() {
        this.diver1 = new DeepWaterDiver(DIVER1_NAME, DEFAULT_OXYGEN_DIVER1);
        this.diver2 = new DeepWaterDiver(DIVER2_NAME, DEFAULT_OXYGEN_DIVER2);
        this.diver3 = new DeepWaterDiver(DIVER3_NAME, DEFAULT_OXYGEN_DIVER3);
        this.diving = new Diving(DIVING_NAME, INITIAL_CAPACITY);
    }

    @Test
    public void testCreateNewDiving_Correctly() {
        this.diving = new Diving("Aquarium", 2);
        Assert.assertEquals("Aquarium", this.diving.getName());
        Assert.assertEquals(2, this.diving.getCapacity());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateNewDiving_WithNullName() {
        this.diving = new Diving(null, 2);
        Assert.assertEquals(2, this.diving.getCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNewDiving_WithNegativeCapacity() {
        this.diving = new Diving("Aquarium", -2);
    }

    @Test
    public void testGetCount() {
        Assert.assertEquals(0, this.diving.getCount());
    }

    @Test
    public void testAddDeepWaterDiver_Correctly() {
        addDiversInDiving();
        Assert.assertEquals(2, this.diving.getCount());
        Assert.assertEquals(DIVER1_NAME, this.diver1.getName());
        Assert.assertEquals(DEFAULT_OXYGEN_DIVER1, this.diver1.getOxygen(), DEFAULT_OXYGEN_DIVER1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddDeepWaterDiver_NotEnoughCapacity() {
        addDiversInDiving();
        this.diving.addDeepWaterDiver(diver3);
        this.diving.addDeepWaterDiver(new DeepWaterDiver("Kiro", 97));
        Assert.assertEquals(3, this.diving.getCount());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddDeepWaterDiver_ExistDiver() {
        addDiversInDiving();
        this.diving.addDeepWaterDiver(new DeepWaterDiver(DIVER1_NAME, DEFAULT_OXYGEN_DIVER1));
        Assert.assertEquals(3, this.diving.getCount());
    }

    @Test
    public void testRemoveDeepWaterDiver_ReturnTrue() {
        addDiversInDiving();
        Assert.assertTrue(this.diving.removeDeepWaterDiver(diver1.getName()));
    }
    @Test
    public void testRemoveDeepWaterDiver_ReturnFalse() {
        addDiversInDiving();
        Assert.assertFalse(this.diving.removeDeepWaterDiver(diver3.getName()));
    }

    private void addDiversInDiving() {
        this.diving.addDeepWaterDiver(diver1);
        this.diving.addDeepWaterDiver(diver2);
    }
}
