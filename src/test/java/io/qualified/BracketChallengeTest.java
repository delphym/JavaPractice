package io.qualified;

import static org.junit.Assert.*;

import org.junit.Test;

public final class BracketChallengeTest {
	@Test
	public void testPairedSquareBrackets() {
		assertTrue(BracketChallenge.check("[]"));
		assertFalse(BracketChallenge.check("]"));
		assertFalse(BracketChallenge.check("["));
		assertTrue(BracketChallenge.check("<[({})]>"));
		assertTrue(BracketChallenge.check("[[[]]]"));
		assertTrue(BracketChallenge.check("/D${F}DFS{/}.(ddd)"));
		assertFalse(BracketChallenge.check("{(])})"));
		assertFalse(BracketChallenge.check("]xyz["));  	}
}