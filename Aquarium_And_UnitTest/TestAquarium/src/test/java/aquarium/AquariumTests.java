package aquarium;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AquariumTests {
    private Fish fish1;
    private Fish fish2;
    private Fish fish3;
    private Aquarium aquarium;

    @Before
    public void setup() {
        this.fish1 = new Fish("Nemo");
        this.fish2 = new Fish("Pesho");
        this.fish3 = new Fish("Gosho");
        this.aquarium = new Aquarium("FreshWaterAquarium", 2);
    }

    @Test
    public void testCreateConstructorAquarium() {
        this.aquarium = new Aquarium("Ivan", 5);
        Assert.assertEquals("Ivan", this.aquarium.getName());
        Assert.assertEquals(5, this.aquarium.getCapacity());
        Assert.assertEquals(0, this.aquarium.getCount());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateConstructorNullName() {
        this.aquarium = new Aquarium(null, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConstructorNegativeCapacity() {
        this.aquarium = new Aquarium("Ivan", -3);
    }

    @Test
    public void testAddCorrectlyFish() {
        this.aquarium.add(fish1);
        Assert.assertEquals(1, this.aquarium.getCount());
        Assert.assertEquals("Nemo", this.fish1.getName());
        Assert.assertTrue(this.fish1.isAvailable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNotEnoughCapacity() {
        this.aquarium.add(fish1);
        this.aquarium.add(fish2);
        this.aquarium.add(fish3);
        Assert.assertEquals(2, this.aquarium.getCount());
        Assert.assertEquals("Nemo", this.fish1.getName());
        Assert.assertTrue(this.fish1.isAvailable());
    }

    @Test
    public void testRemoveExistFish() {
        this.aquarium.add(fish1);
        Assert.assertEquals(1, this.aquarium.getCount());
        this.aquarium.remove("Nemo");
        Assert.assertEquals(0, this.aquarium.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNotExistFish() {
        this.aquarium.add(fish1);
        Assert.assertEquals(1, this.aquarium.getCount());
        this.aquarium.remove("Gosho");
        Assert.assertEquals(0, this.aquarium.getCount());
    }

    @Test
    public void testSellFishExistFish() {
        this.aquarium.add(fish1);
        Assert.assertTrue(fish1.isAvailable());
        Fish soldFish = this.aquarium.sellFish("Nemo");
        Assert.assertEquals(soldFish, fish1);
        Assert.assertFalse(soldFish.isAvailable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSellFishNotExistFish() {
        this.aquarium.add(fish1);
        Assert.assertTrue(fish1.isAvailable());
        Fish soldFish = this.aquarium.sellFish("Gosho");
        Assert.assertEquals(soldFish, fish1);
        Assert.assertFalse(soldFish.isAvailable());
    }

    @Test
    public void testReportAquarium() {
        this.aquarium.add(fish1);
        this.aquarium.add(fish2);
        Assert.assertEquals("Fish available at FreshWaterAquarium: Nemo, Pesho", this.aquarium.report());
    }
}

