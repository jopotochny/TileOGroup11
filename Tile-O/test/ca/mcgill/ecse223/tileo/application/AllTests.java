package ca.mcgill.ecse223.tileo.application;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.ecse223.tileo.controller.TileODesignControllerTest;
import ca.mcgill.ecse223.tileo.controller.TileOPlayControllerTest;
import ca.mcgill.ecse223.tileo.persistence.PersistenceTest;




@RunWith(Suite.class)
@SuiteClasses({ TileOPlayControllerTest.class, TileODesignControllerTest.class, PersistenceTest.class})public class AllTests {

}