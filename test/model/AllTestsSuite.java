package model;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ CardTest.class, DeckTest.class, HandTest.class})
public class AllTestsSuite {

}
