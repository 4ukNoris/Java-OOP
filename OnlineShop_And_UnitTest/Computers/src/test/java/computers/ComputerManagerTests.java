package computers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ComputerManagerTests {
    private Computer computer1;
    private Computer computer2;
    private Computer computer3;
    private ComputerManager computerManager;

    @Before
    public void setUp() {
        this.computer1 = new Computer("Asus", "F15", 1800);
        this.computer2 = new Computer("Asser", "A20", 1500);
        this.computer3 = new Computer("HP", "Z18", 1200);
        this.computerManager = new ComputerManager();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetComputers_Unmodifiable() {
        this.computerManager.getComputers().add(computer1);
    }

    @Test
    public void testGetCount_Size() {
        Assert.assertEquals(0, this.computerManager.getCount());
    }

    @Test
    public void testAddComputer_Correctly() {
        addComputerToTheCollection();
        Assert.assertEquals(2, this.computerManager.getCount());
        Assert.assertEquals(this.computer1, this.computerManager.getComputers().get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddComputer_NullComputer() {
        this.computerManager.addComputer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddComputer_ExistComputer() {
        addComputerToTheCollection();
        this.computerManager.addComputer(computer1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveComputer_NullManufacturer() {
        addComputerToTheCollection();
        String manufacturer = "";
        String model = "";
        Assert.assertNull(this.computerManager.removeComputer(manufacturer, model));
    }
    @Test
    public void testRemoveComputer_Correctly(){
        addComputerToTheCollection();
        String manufacturer = "Asser";
        String model = "A20";
        Assert.assertEquals(computer2, this.computerManager.removeComputer(manufacturer, model));
    }
    @Test
    public void testGetComputer_Correct() {
        addComputerToTheCollection();
        Computer testComputer = this.computerManager.getComputer("Asus", "F15");
        Assert.assertEquals("Asus", testComputer.getManufacturer());
        Assert.assertEquals("F15", testComputer.getModel());
        Assert.assertEquals(1800, testComputer.getPrice(), 1800);
    }
    @Test
    public void testGetComputersByManufacturer() {
        addComputerToTheCollection();
        List<Computer> testComputers = this.computerManager.getComputersByManufacturer("Asus");
        Assert.assertEquals(1, testComputers.size());
        Assert.assertEquals(computer1, testComputers.get(0));
    }

    private void addComputerToTheCollection() {
        this.computerManager.addComputer(computer1);
        this.computerManager.addComputer(computer2);
    }
}