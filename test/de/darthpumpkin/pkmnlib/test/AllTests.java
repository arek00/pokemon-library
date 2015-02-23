package de.darthpumpkin.pkmnlib.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PokemonSpeciesTest.class, PokemonInstanceBuilderTest.class,
		PokemonInstanceTest.class })
public class AllTests {

}