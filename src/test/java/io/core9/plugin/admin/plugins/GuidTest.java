package io.core9.plugin.admin.plugins;


import java.nio.ByteBuffer;
import java.util.UUID;

import org.junit.Test;

public class GuidTest {

	@Test
	public void test() {
		Long uuid = UUID.randomUUID().getMostSignificantBits();
		System.out.println(Long.toString(uuid));
		
		System.out.println(shortUUID());
		
		System.out.println(UUID.randomUUID().toString());
	}
	
	/**
	 * Generate short UUID (13 characters)
	 * 
	 * @return short UUID
	 */
	public static String shortUUID() {
	  UUID uuid = UUID.randomUUID();
	  long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
	  return Long.toString(l, Character.MAX_RADIX).toUpperCase();
	}

}
