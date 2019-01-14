package tipprobot;

import static org.junit.Assert.*;

import org.junit.Test;

import de.jacksitlab.tipprobot.data.Helper;

public class StringCorrelationTest {

	@Test
	public void test() {
		float f=0;
		String s1="1.FC Nürnberg";
		String s2="1.FC Nürnberg";
		f=Helper.correlate(s1, s2);
		System.out.println(String.format("%s : %s => %2.2f",s1,s2,f ));
		s2="1. FC Nürnberg";
		f=Helper.correlate(s1, s2);
		System.out.println(String.format("%s : %s => %2.2f",s1,s2,f ));
		s2="1.FC Köln";
		f=Helper.correlate(s1, s2);
		System.out.println(String.format("%s : %s => %2.2f",s1,s2,f ));
		s2="Bayern München";
		f=Helper.correlate(s1, s2);
		System.out.println(String.format("%s : %s => %2.2f",s1,s2,f ));
		
	}

}
