package mikera.test;

import org.junit.*;
import static org.junit.Assert.*;
import mikera.util.*;
import mikera.engine.*;

public class TestBits {
	@Test public void testRoundUp() {
		assertEquals(16,Bits.roundUpToPowerOfTwo(15));
		assertEquals(16,Bits.roundUpToPowerOfTwo(16));
		assertEquals(32,Bits.roundUpToPowerOfTwo(17));
		assertEquals(1,Bits.roundUpToPowerOfTwo(1));
		assertEquals(0,Bits.roundUpToPowerOfTwo(0));
	}
	
	@Test public void testRoundDown() {
		assertEquals(16,Bits.roundDownToPowerOfTwo(17));
		assertEquals(16,Bits.roundDownToPowerOfTwo(31));
		assertEquals(32,Bits.roundDownToPowerOfTwo(33));
		assertEquals(2,Bits.roundDownToPowerOfTwo(2));
		assertEquals(1,Bits.roundDownToPowerOfTwo(1));
		assertEquals(0,Bits.roundDownToPowerOfTwo(0));
	}
	
	@Test public void testSigBits() {
		assertEquals(1,Bits.significantSignedBits(0));
		assertEquals(1,Bits.significantSignedBits(-1));

		assertEquals(4,Bits.significantSignedBits(4));
		assertEquals(3,Bits.significantSignedBits(3));
		
		assertEquals(3,Bits.significantSignedBits(-4));

	}
	
	@Test public void testTrailingZeros() {
		assertEquals(0,Bits.countTrailingZeros(0x1L));
		assertEquals(1,Bits.countTrailingZeros(2));
		assertEquals(16,Bits.countTrailingZeros(0xFFFF0000));
		assertEquals(0,Bits.countTrailingZeros(-1));
		assertEquals(0,Bits.countTrailingZeros(-1L));
		assertEquals(32,Bits.countTrailingZeros(0xFFFFFFFF00000000L));
		assertEquals(48,Bits.countTrailingZeros(0xFFFF000000000000L));
		assertEquals(64,Bits.countTrailingZeros(0L));
		assertEquals(32,Bits.countTrailingZeros(0));

	}
	
	@Test public void testLeadingZeros() {
		assertEquals(63,Bits.countLeadingZeros(0x1L));
		assertEquals(30,Bits.countLeadingZeros(2));
		assertEquals(0,Bits.countLeadingZeros(-1));
		assertEquals(0,Bits.countLeadingZeros(-1L));
		assertEquals(32,Bits.countLeadingZeros(0x00000000FFFFFFFFL));
		assertEquals(48,Bits.countLeadingZeros(0xFFFFL));
		assertEquals(64,Bits.countLeadingZeros(0L));
		assertEquals(32,Bits.countLeadingZeros(0));

	}
	
	@Test public void testLowestSetBit() {
		assertEquals(0,Bits.lowestSetBit(0));
		assertEquals(1,Bits.lowestSetBit(0x1FFF));
		assertEquals(0x80000000,Bits.lowestSetBit(0x80000000));
		assertEquals(2,Bits.lowestSetBit(6));
	}

	
	
	
	@Test public void testBitGrid1() {
		BitGrid bg=new BitGrid(0,0,0);
		assertEquals(4,bg.width());
		assertEquals(4,bg.height());
		assertEquals(2,bg.depth());
		assertEquals(21,bg.bitPos(1, 1, 1));
		assertEquals(1,bg.dataLength());
		
		bg.set(1,1,1,1);
		assertEquals(0,bg.get(0, 0, 0));
		bg.set(0,0,0,1);
		assertEquals(1,bg.get(0, 0, 0));
		bg.set(0,0,0,0);
		assertEquals(0,bg.get(0, 0, 0));
		assertEquals(1,bg.get(1, 1, 1));
		assertEquals(1,bg.countSetBits());
		
		bg.set(-10,-10,-10,1);
		assertEquals(0,bg.get(0, 0, 0));
		assertEquals(1,bg.get(-10, -10, -10));
		assertEquals(1,bg.get(1, 1, 1));
		assertEquals(2,bg.countSetBits());
		
		bg.clear();
		assertEquals(0,bg.dataLength());
		bg.set(Rand.d(100),Rand.d(100),-Rand.d(100),1);
		assertEquals(1,bg.dataLength());
	}
	
	public class TestVisitor extends PointVisitor<Integer> {
		int count=0;
		int tcount=0;
		@Override
		public boolean visit(int x, int y, int z, Integer value) {
			count+=value;
			tcount++;
			return false;
		}		
	}
	
	@Test public void testCountBits() {
		assertEquals(1,Bits.countSetBits(0x00010000));
		assertEquals(32,Bits.countSetBits(-1));
		assertEquals(8,Bits.countSetBits(0xF000000F));

	}
	
	@Test public void testBitGridVisitors() {
		BitGrid bg=new BitGrid(0,0,0);
		
		bg.set(0,0,0,1);
		bg.set(10,10,-10,1);
		
		TestVisitor pv=new TestVisitor();
		
		bg.visitBits(pv);
		assertEquals(2,pv.count);
		assertTrue(2<pv.tcount);
		

	}
}
