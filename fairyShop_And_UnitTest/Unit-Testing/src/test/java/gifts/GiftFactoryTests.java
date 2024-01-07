package gifts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class GiftFactoryTests {
    private Gift gift1;
    private Gift gift2;
    private Gift gift3;
    private GiftFactory giftFactory;

    @Before
    public void setup() {
        this.gift1 = new Gift("Train", 25.5);
        this.gift2 = new Gift("Bear", 15.5);
        this.gift3 = new Gift("Car", 35.5);
        this.giftFactory = new GiftFactory();
        this.giftFactory.createGift(gift1);
        this.giftFactory.createGift(gift2);
    }

    @Test
    public void testCreateGiftFactory() {
        GiftFactory giftFactory1 = new GiftFactory();
        Assert.assertEquals(0, giftFactory1.getCount());
    }

    @Test
    public void testGetCount() {
        Assert.assertEquals(2, this.giftFactory.getCount());
    }

    @Test
    public void testCreateGiftCorrectData() {
        Assert.assertEquals("Successfully added gift Car with magic 35,50.", this.giftFactory.createGift(gift3));
        Assert.assertEquals(3, this.giftFactory.getCount());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateGiftWithExistGift() {

        Assert.assertEquals("gifts. Gift with name Bear already exists.", this.giftFactory.createGift(gift2));
        Assert.assertEquals(3, this.giftFactory.getCount());
    }
    @Test(expected = NullPointerException.class)
    public void testCreateGiftWithNullGift() {
        this.giftFactory.createGift(null);
    }
    @Test
    public void testRemoveGiftReturnTrue() {
        Assert.assertTrue(this.giftFactory.removeGift(gift1.getType()));
        Assert.assertEquals(1, this.giftFactory.getCount());
    }
    @Test
    public void testRemoveGiftReturnFalseName() {
        Assert.assertFalse(this.giftFactory.removeGift(gift3.getType()));
        Assert.assertEquals(2, this.giftFactory.getCount());
    }
    @Test(expected = NullPointerException.class)
    public void testRemoveGiftWithEmptyName() {
        this.giftFactory.removeGift(" ");
    }
    @Test(expected = NullPointerException.class)
    public void testRemoveGiftWithNullName() {
        this.giftFactory.removeGift(null);
    }
    @Test
    public void testGetPresentWithLeastMagicExistGift() {
        Assert.assertEquals(gift2.getMagic(), this.giftFactory.getPresentWithLeastMagic().getMagic(), gift2.getMagic());
        Assert.assertEquals(gift2, this.giftFactory.getPresentWithLeastMagic());
    }
    @Test
    public void testGetPresentWithLeastMagicNullGift() {
        this.giftFactory = new GiftFactory();
        Assert.assertNull(this.giftFactory.getPresentWithLeastMagic());
    }
    @Test
    public void testGetPresentCorrect() {
        Assert.assertEquals(gift1.getType(), this.giftFactory.getPresent("Train").getType());
        Assert.assertEquals(gift1, this.giftFactory.getPresent("Train"));
    }
    @Test
    public void testGetPresentNullGift() {
        Assert.assertNull(this.giftFactory.getPresent("Car"));
    }
    @Test
    public void testGetPresentsAllCollection() {
        Collection<Gift> testCollection = new ArrayList<>();
        Collection<Gift> testPresents = this.giftFactory.getPresents();
        testCollection.add(gift1);
        testCollection.add(gift2);
        Assert.assertEquals(testCollection.size(), testPresents.size());

    }
    @Test(expected = UnsupportedOperationException.class)
    public void IfTrueToModifiedCollectionThrowException(){
        this.giftFactory.createGift(this.gift3);
        this.giftFactory.getPresents().remove(this.gift3);

    }
}
