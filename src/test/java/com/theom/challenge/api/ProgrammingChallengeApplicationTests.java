package com.theom.challenge.api;

import com.theom.challenge.common.LRUCache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

@SpringBootTest
class ProgrammingChallengeApplicationTests {

	LRUCache cache;
	private static final String A = "A";
	private static final String B = "B";
	private static final String C = "C";
	private static final String D = "D";

	private static void assertMiss(LRUCache<String, String> cache, String key) {
		assertNull(cache.get(key));
	}

	private static void assertHit(LRUCache<String, String> cache, String key, String value) {
		assertThat(cache.get(key), is(value));
	}

	private static void assertSnapshot(LRUCache<String, String> cache, String... keysAndValues) {
		List<String> actualKeysAndValues = new ArrayList<>();
		for (Map.Entry<String, String> entry : cache.entrySet()) {
			actualKeysAndValues.add(entry.getKey());
			actualKeysAndValues.add(entry.getValue());
		}
		assertEquals(Arrays.asList(keysAndValues), actualKeysAndValues);
	}

	@Test
	public void logic() {
		cache = new LRUCache<String, String>(1000);

		cache.put("a", A);
		assertHit(cache, "a", A);
		cache.put("b", B);
		assertHit(cache, "a", A);
		assertHit(cache, "b", B);
		assertSnapshot(cache, "a", A, "b", B);

		cache.put("c", C);
		assertHit(cache, "a", A);
		assertHit(cache, "b", B);
		assertHit(cache, "c", C);
		assertSnapshot(cache, "a", A, "b", B, "c", C);

		cache.put("d", D);
		assertHit(cache, "b", B);
		assertHit(cache, "c", C);
		assertHit(cache, "d", D);
		assertHit(cache, "b", B);
		assertHit(cache, "c", C);
		assertSnapshot(cache, "a", A, "d", D, "b", B, "c", C);
		cache.remove("d");
		assertMiss(cache, "d");

	}

	@Test
	public void cannotPutNullKey() {
		try {
			cache.put(null, "a");
			fail();
		} catch (NullPointerException expected) {
			// nothing
		}
	}

	@Test
	public void cannotPutNullValue() {
		try {
			cache.put("a", null);
			fail();
		} catch (NullPointerException expected) {
			// nothing
		}
	}

	@Test
	public void evictionWithSingletonCache() {
		LRUCache<String, String> cache = new LRUCache<>(1);
		cache.put("a", A);
		cache.put("b", B);
		assertSnapshot(cache, "b", B);
	}

	@Test
	public void removeOneItem() {
		LRUCache<String, String> cache = new LRUCache<>(1);
		cache.put("a", A);
		cache.put("b", B);
		assertNull(cache.remove("a"));
		assertSnapshot(cache, "b", B);
	}

	@Test
	public void cannotRemoveNullKey() {
		try {
			cache.remove(null);
			fail();
		} catch (NullPointerException expected) {
			// nothing
		}
	}

	@Test
	public void throwsWithNullKey() {
		try {
			cache.get(null);
			fail("Expected NullPointerException");
		} catch (NullPointerException e) {
			// nothing
		}
	}

}
