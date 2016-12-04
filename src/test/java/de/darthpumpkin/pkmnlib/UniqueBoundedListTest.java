package de.darthpumpkin.pkmnlib;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class UniqueBoundedListTest {

	private PokemonSpecies s;

	@Before
	public void setUpSpecies() {
		s = new DummySpeciesFactory().getSpeciesById(1);
	}

	@Test
	public void testUniqueBoundedList() {
		assertTrue(UniqueBoundedList.DEFAULT_MAX_SIZE >= 0);
		UniqueBoundedList<PokemonInstance> t = new UniqueBoundedList<PokemonInstance>();
		assertEquals(UniqueBoundedList.DEFAULT_MAX_SIZE, t.getMaxSize());
		assertTrue(t.isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUniqueBoundedListIntIllegalMaxSize() {
		new UniqueBoundedList<PokemonInstance>(-1);
	}

	@Test
	public void testUniqueBoundedListIntLegalMaxSize() {
		int[] sizes = new int[] { 0, 1, 6, 100, 10000 };
		for (int size : sizes) {
			UniqueBoundedList<PokemonInstance> t = new UniqueBoundedList<PokemonInstance>(
					size);
			assertEquals(size, t.getMaxSize());
			assertTrue(t.isEmpty());
		}
	}

	@Test
	public void testUniqueBoundedListCollectionOfQextendsPokemonInstance() {
		List<PokemonInstance[]> emptyArrays = new ArrayList<>();
		List<PokemonInstance[]> nonEmptyArrays = new ArrayList<>();

		emptyArrays.add(new PokemonInstance[0]);
		emptyArrays.add(new PokemonInstance[100]);

		nonEmptyArrays
				.add(new PokemonInstance[] { new PokemonInstanceBuilder(s)
						.makePokemon() });

		for (PokemonInstance[] nonEmpty : nonEmptyArrays) {
			UniqueBoundedList<PokemonInstance> t = new UniqueBoundedList<PokemonInstance>(
					Arrays.asList(nonEmpty));
			assertEquals(UniqueBoundedList.DEFAULT_MAX_SIZE, t.getMaxSize());
			assertFalse(t.isEmpty());
			assertArrayEquals(nonEmpty, t.toArray());
		}
		for (PokemonInstance[] empty : emptyArrays) {
			UniqueBoundedList<PokemonInstance> t = new UniqueBoundedList<PokemonInstance>(
					Arrays.asList(empty));
			assertEquals(UniqueBoundedList.DEFAULT_MAX_SIZE, t.getMaxSize());
			assertTrue(t.isEmpty());
		}
	}

	@Test(expected = MaximumSizeExceededException.class)
	public void testUniqueBoundedListCollectionOfQextendsPokemonInstanceTooLarge() {
		PokemonInstanceBuilder b = new PokemonInstanceBuilder(s);
		Collection<PokemonInstance> coll = new ArrayList<>();
		for (int i = 0; i < UniqueBoundedList.DEFAULT_MAX_SIZE + 1; i++) {
			coll.add(b.makePokemon());
		}
		new UniqueBoundedList<PokemonInstance>(coll);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUniqueBoundedListCollectionOfQextendsPokemonInstanceIntIllegalArgument() {
		new UniqueBoundedList<PokemonInstance>(
				Collections.<PokemonInstance> emptyList(), -1);
	}

	@Test(expected = MaximumSizeExceededException.class)
	public void testUniqueBoundedListCollectionOfQextendsPokemonInstanceIntTooLarge() {
		PokemonInstanceBuilder b = new PokemonInstanceBuilder(s);
		int getMaxSize = 10;
		Collection<PokemonInstance> coll = new ArrayList<>();
		for (int i = 0; i < getMaxSize + 1; i++) {
			coll.add(b.makePokemon());
		}
		new UniqueBoundedList<PokemonInstance>(coll, getMaxSize);
	}

	@Test
	public void testAddItemToEmptyList() {
		UniqueBoundedList<PokemonInstance> ubl = new UniqueBoundedList<PokemonInstance>();
		PokemonInstance p = new PokemonInstanceBuilder(
				new DummySpeciesFactory().getSpeciesById(1)).makePokemon();
		ubl.add(p);
		assertTrue(ubl.contains(p));
		assertEquals(1, ubl.size());
		assertArrayEquals(new PokemonInstance[] { p }, ubl.toArray());
	}

	@Test(expected = MaximumSizeExceededException.class)
	public void testAddItemToEmptyListWithMaxSize0() {
		UniqueBoundedList<PokemonInstance> ubl = new UniqueBoundedList<PokemonInstance>(
				0);
		PokemonInstance p = new PokemonInstanceBuilder(
				new DummySpeciesFactory().getSpeciesById(1)).makePokemon();
		ubl.add(p);
	}

	@Test(expected = MaximumSizeExceededException.class)
	public void testAddItemToFullList() {
		UniqueBoundedList<PokemonInstance> ubl = new UniqueBoundedList<PokemonInstance>();
		PokemonInstanceBuilder builder = new PokemonInstanceBuilder(s);
		try {
			for (int i = 0; i < ubl.getMaxSize(); i++) {
				ubl.add(builder.makePokemon());
			}
		} catch (MaximumSizeExceededException e) {
			fail();
		}
		ubl.add(builder.makePokemon());
	}

	@Test
	public void testAddDuplicateItem() {
		UniqueBoundedList<PokemonInstance> ubl = new UniqueBoundedList<PokemonInstance>();
		PokemonInstanceBuilder builder = new PokemonInstanceBuilder(s);
		PokemonInstance p = builder.makePokemon();
		ubl.add(p);
		ubl.add(p);
		assertTrue(ubl.contains(p));
		assertEquals(1, ubl.size());
		assertArrayEquals(new PokemonInstance[] { p }, ubl.toArray());
	}

	@Test
	public void testAddDuplicateItemToFullList() {
		UniqueBoundedList<PokemonInstance> ubl = new UniqueBoundedList<PokemonInstance>();
		PokemonInstanceBuilder builder = new PokemonInstanceBuilder(s);
		PokemonInstance p = builder.makePokemon();
		ubl.add(p);
		for (int i = 0; i < ubl.getMaxSize() - 1; i++) {
			ubl.add(builder.makePokemon());
		}
		assertEquals(ubl.getMaxSize(), ubl.size());
		Object[] before = ubl.toArray();
		ubl.add(p);
		Object[] after = ubl.toArray();
		assertArrayEquals(before, after);
	}

	@Test @Ignore
	public void testAddAllCollectionOfQextendsItem() {
		fail("Not yet implemented");
	}

	@Test @Ignore
	public void testAddAllIntCollectionOfQextendsItem() {
		fail("Not yet implemented");
	}

	@Test @Ignore
	public void testAddIntItem() {
		fail("Not yet implemented");
	}

}
