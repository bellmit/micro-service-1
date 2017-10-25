package com.forezp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import zipkin.internal.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerZipkinApplicationTests {

	@Test
	public void contextLoads() {
	}

	public static void main(String[] args) {
		String lowerHex = "8df2100999aa4434";
		long l = Util.lowerHexToUnsignedLong(lowerHex);
		System.out.println(l);
		String h = Util.toLowerHex(l);
		System.out.println(h);
	}
}
