import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.example.GildedRose;
import com.example.Item;

public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}
	@Test
	public void exampleTest() {
		//create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay();
		
		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//assert quality has decreased by one
		assertEquals("Failed quality for Dexterity Vest", 19, quality);
	}

	@Test
	public void testBrieQualityIncrease() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Aged Brie", 2, 0));
        inn.oneDay();
		List<Item> items = inn.getItems();
        assertEquals("Aged Brie quality did not increase", 1, items.get(0).getQuality());
	}

	@Test
	public void testBackstageIncrease() {
		GildedRose inn = new GildedRose();
        inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20));
        inn.oneDay();
        List<Item> items = inn.getItems();
        assertEquals("Backstage pass quality did not increase correctly", 22, items.get(0).getQuality());
	}

	@Test
	public void testBackstageConcert() {
		GildedRose inn = new GildedRose();
        inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 200));
		for (int i = 0; i < 11; i++) {
			inn.oneDay();
		}
        List<Item> items = inn.getItems();
        assertEquals("Backstage pass quality did not increase correctly", 0, items.get(0).getQuality());
	}

	@Test
	public void testSulfurasQuality() {
		int sellIn = 0;
		GildedRose inn = new GildedRose();
        inn.setItem(new Item("Sulfuras, Hand of Ragnaros", sellIn, 80));
		for (int i = 0; i < 1000; i++) {
			inn.oneDay();
		}
        List<Item> items = inn.getItems();
        assertEquals("Sulfaras quality should alway be 80", 80, items.get(0).getQuality());
		assertEquals("Sulfaras sellIn should alway be 0", sellIn, items.get(0).getSellIn());
	}

	@Test
	public void testQualityNotOver50() {
		GildedRose inn = new GildedRose();
        inn.setItem(new Item("+5 Dexterity Vest", 1000, 20));
        inn.setItem(new Item("Aged Brie", 2000, 0));
        inn.setItem(new Item("Elixir of the Mongoose", 5000, 7));
        inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 1500, 20));
        inn.setItem(new Item("Conjured Mana Cake", 3000, 6));

		for (int i = 0; i < 100; i++) {
			inn.oneDay();
		}

		List<Item> items = inn.getItems();
		for (Item item : items) {
			assertTrue("Quality exceeded 50 for item: " + item.getName(), item.getQuality() <= 50);
		}
	}

	@Test
	public void testQualityNonNegative() {
		GildedRose inn = new GildedRose();
        inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
        inn.setItem(new Item("Aged Brie", 2, 0));
        inn.setItem(new Item("Elixir of the Mongoose", 5, 7));
        inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
        inn.setItem(new Item("Conjured Mana Cake", 3, 6));

		for (int i = 0; i < 1000; i++) {
			inn.oneDay();
		}

		List<Item> items = inn.getItems();
		for (Item item : items) {
			assertTrue("Quality negative for item: " + item.getName(), item.getQuality() >= 0);
		}
	}

	@Test
	public void testNormalItemPastSellInDecrease() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 0, 10));
		inn.oneDay();
		List<Item> items = inn.getItems();
		assertEquals("Normal item quality did not decrease by 2 after SellIn", 8, items.get(0).getQuality());
	}

	@Test
	public void testNegativeSellin() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Regular Item", -1, 10));
		inn.oneDay();
		List<Item> items = inn.getItems();
		assertEquals("Normal item quality should decreate by 2", 8, items.get(0).getQuality());
		assertEquals("Normal item quality should decreate by 2", -2, items.get(0).getSellIn());
	}

    @Test
    public void testSulfurasDoesNotDecreaseQualityAfterSellIn() {
		GildedRose inn = new GildedRose();
        inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));

        for (int i = 0; i < 10; i++) {
            inn.oneDay();
        }

        Item sulfuras = inn.getItems().get(0);

        assertEquals(80, sulfuras.getQuality());
        assertEquals(0, sulfuras.getSellIn());
    }

}
