package mikera.test;

import org.junit.*;
import static org.junit.Assert.*;
import mikera.util.*;

public class TestText {
	@Test public void testWhiteSpace() {
		assertEquals("   ",TextUtils.whiteSpace(3));
		
		int n=Rand.d(10,10);
		assertEquals(n,TextUtils.whiteSpace(n).length());
	}
	
	@Test public void testRoman() {
		assertEquals("XXXIV",TextUtils.roman(34));
		
		assertEquals("-DCLXVI",TextUtils.roman(-666));
		
		assertEquals("MMMCMXCIX",TextUtils.roman(3999));
		
		assertEquals("nullus",TextUtils.roman(0));
	}
	
	@Test public void testText() {
		String st="My text";
		
		Text t1=Text.create("My text");
		Text t2=Text.create("My text");
		Text t3=Text.create(TextUtils.whiteSpace(1000));
		Text t4=Text.create(TextUtils.whiteSpace(1001));
		
		testTextObject(t1);
		testTextObject(t2);
		testTextObject(t3);
		testTextObject(t4);

		assertEquals(16,t4.countBlocks()); // low level blocks
		assertEquals(31,t4.countNodes()); // total nodes including tree
		
		for (int i=0; i<=1000; i++) {
			assertNotNull(t4.getBlock(i));
			assertTrue(t4.getBlockStartPosition(i)<=i);
		}
		
		assertEquals("y tex",t1.substring(1, 6));
		assertEquals(st,t1.toString());
		
		assertNull(t3.getBlock(-1));
		assertNull(t3.getBlock(1000));
		
		assertTrue(t1!=t2);
		assertEquals(0,t1.compareTo(t2));
		assertEquals(-1,t3.compareTo(t4));
		
		assertEquals(t1.hashCode(),t2.hashCode());
		assertTrue(t3.hashCode()!=t4.hashCode());
		assertTrue(t3.firstBlock().hashCode()==t4.firstBlock().hashCode());
		
		assertEquals(Text.concat(t1,t3),Text.concat(t2,t3));
		
		assertEquals(true,t1.isPacked());
		assertEquals(true,t4.isPacked());
	}
	
	@Test public void testTextOps() {
		Text t1=Text.create("");
		StringBuffer sb=new StringBuffer();
		
		for (int i=0; i<200; i++) {
			String s=Integer.toString(Rand.d(100));
			sb.append(s);
			t1=t1.append(s);
			
			if (Rand.d(30)==1) {
				int a=Rand.r(sb.length());
				int b=Rand.range(a, sb.length()-1);
				sb=new StringBuffer(sb.substring(a, b));
				t1=t1.subText(a, b);
			}
		}
		
		testTextObject(t1);
		
		assertEquals(t1.toString(),sb.toString());
		assertEquals(t1.hashCode(),Text.create(sb.toString()).hashCode());
		
		StringBuffer sb2=new StringBuffer();
		for (Character ch: t1) {
			sb2.append(ch);
		}
		assertEquals(sb.toString(),sb2.toString());
		
		
	}
	
	@Test public void testConcat() {
		Text t1=Text.create("AB");
		Text t2=Text.create("CD");
		
		assertEquals("ABCD",Text.concat(t1, t2).toString());
		assertEquals(t1,Text.concat(t1, Text.EMPTY));
		assertEquals(t1,Text.concat(Text.EMPTY,t1));
		
	}
	
	@Test public void testInsetr() {
		Text t1=Text.create("AB");
		Text t2=Text.create("CD");
		
		assertEquals("ACDB",t1.insert(1, t2).toString());		
	}
	
	public void testTextObject(Text t) {
		int len=t.length();
		
		assertTrue(len>=0);
		assertNull(t.getBlock(-1));
		assertNull(t.getBlock(len));
		if (len>0) {
			assertNotNull(t.getBlock(0));
			assertNotNull(t.getBlock(len-1));
		}
		t.isPacked();
		assertTrue(t.countNodes()>=t.countBlocks());
		assertEquals(len*2,Text.concat(t, t).length());
	}
}