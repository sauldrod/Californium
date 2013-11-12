
package ch.ethz.inf.vs.californium.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.ethz.inf.vs.californium.Utils;
import ch.ethz.inf.vs.californium.coap.BlockOption;

/**
 * This test tests the functionality of the class BlockOption. BlockOption
 * converts the parameters SZX, M, NUM (defined in the draft) to a byte array
 * and extracts these parameters vice-versa form a specified byte array.
 */
public class BlockOptionTest {

	@Before
	public void setupServer() {
		System.out.println("\nStart "+getClass().getSimpleName());
	}
	
	@After
	public void shutdownServer() {
		System.out.println("End "+getClass().getSimpleName());
	}
	
	/**
	 * Tests that the class BlockOption converts the specified parameters to the
	 * correct byte array
	 */
	@Test
	public void testGetValue() {
		System.out.println("Test getValue()");
		assertArrayEquals(toBytes(0, false, 0), b(0x0));
		assertArrayEquals(toBytes(0, false, 1), b(0x10));
		assertArrayEquals(toBytes(0, false, 15), b(0xf0));
		assertArrayEquals(toBytes(0, false, 16), b(0x01, 0x00));
		assertArrayEquals(toBytes(0, false, 79), b(0x04, 0xf0));
		assertArrayEquals(toBytes(0, false, 113), b(0x07, 0x10));
		assertArrayEquals(toBytes(0, false, 26387), b(0x06, 0x71, 0x30));
		assertArrayEquals(toBytes(0, false, 1048575), b(0xff, 0xff, 0xf0));
		assertArrayEquals(toBytes(7, false, 1048575), b(0xff, 0xff, 0xf7));
		assertArrayEquals(toBytes(7, true, 1048575), b(0xff, 0xff, 0xff));
	}

	/**
	 * Tests that the class BlockOption correctly converts the given parameter
	 * to a byte array and back to a BlockOption with the same parameters as
	 * originally.
	 */
	@Test
	public void testCombined() {
		System.out.println("Test  setValue()");
		testCombined(0, false, 0);
		testCombined(0, false, 1);
		testCombined(0, false, 15);
		testCombined(0, false, 16);
		testCombined(0, false, 79);
		testCombined(0, false, 113);
		testCombined(0, false, 26387);
		testCombined(0, false, 1048575);
		testCombined(7, false, 1048575);
		testCombined(7, true, 1048575);
	}

	/**
	 * Converts a BlockOption with the specified parameters to a byte array and
	 * back and checks that the result is the same as the original.
	 */
	private void testCombined(int szx, boolean m, int num) {
		BlockOption block = new BlockOption(szx, m, num);
		BlockOption copy = new BlockOption(block.getValue());
		assertEquals(block.getSzx(), copy.getSzx());
		assertEquals(block.isM(), copy.isM());
		assertEquals(block.getNum(), copy.getNum());
		System.out.println(Utils.toHexString(block.getValue()) +" == " 
			+ "(szx="+block.getSzx()+", m="+block.isM()+", num="+block.getNum()+")");
	}

	/**
	 * Helper function that creates a BlockOption with the specified parameters
	 * and serializes them to a byte array.
	 */
	private byte[] toBytes(int szx, boolean m, int num) {
		byte[] bytes = new BlockOption(szx, m, num).getValue();
		 System.out.println("(szx="+szx+", m="+m+", num="+num+") => "
				 + Utils.toHexString(bytes));
		return bytes;
	}

	/**
	 * Helper function that converts an int array to a byte array.
	 */
	private byte[] b(int... a) {
		byte[] ret = new byte[a.length];
		for (int i = 0; i < a.length; i++)
			ret[i] = (byte) a[i];
		return ret;
	}

}