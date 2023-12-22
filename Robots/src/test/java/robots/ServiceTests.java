package robots;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServiceTests {
    private Service service;
    private Robot robot1;
    private Robot robot2;
    private Robot robot3;
    @Before
    public void setup(){
        this.service = new Service("Main", 2);
        this.robot1 = new Robot("Gosho");
        this.robot2 = new Robot("Pesho");
        this.robot3 = new Robot("Ivan");
    }
    @Test
    public void testCreateNewServiceCorrect(){
        service = new Service("NewService", 2);
        Assert.assertEquals("NewService", service.getName());
        Assert.assertEquals(2, service.getCapacity());
    }
    @Test(expected = NullPointerException.class)
    public void testCreateNewServiceNullName(){
        service = new Service(null, 2);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNewServiceNegativeCapacity(){
        service = new Service("NewService", -1);
    }
    @Test
    public void testAddRobotCorrectAdding(){
        this.service.add(robot1);
        this.service.add(robot2);
        Assert.assertEquals("Gosho", robot1.getName());
        Assert.assertEquals("Pesho", robot2.getName());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddRobotFullCapacity(){
        this.service.add(robot1);
        this.service.add(robot2);
        this.service.add(robot3);
    }
    @Test
    public void testGetCount(){
        this.service.add(robot1);
        this.service.add(robot2);
        Assert.assertEquals(2, this.service.getCount());
    }
    @Test
    public void testRemoveCorrectName(){
        this.service.add(robot1);
        this.service.add(robot2);
        this.service.remove(robot1.getName());
        Assert.assertEquals(1, this.service.getCount());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveIncorrectName(){
        this.service.add(robot1);
        this.service.add(robot2);
        this.service.remove("Petkan");
    }
    @Test
    public void testForSaleCorrectSealing() {
        this.service.add(robot1);
        this.service.forSale(robot1.getName());
        Assert.assertFalse(robot1.isReadyForSale());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testForSaleIncorrectName() {
        this.service.add(robot1);
        this.service.forSale("Ivan");
        Assert.assertTrue(robot1.isReadyForSale());
    }
    @Test
    public void testReportOutputMessage() {
        this.service.add(robot1);
        this.service.add(robot2);
        String output = "The robot Gosho, Pesho is in the service Main!";
        Assert.assertEquals(output, this.service.report());
    }
}
