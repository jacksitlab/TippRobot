package de.jacksitlab.tipprobot.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Helper {
	
	
	private static class CharCountMap extends HashMap<String,Integer>{
		
		public CharCountMap(String s)
		{
			for(char c:s.toCharArray())
			{
				String sc=String.valueOf(c).trim();
				if(sc.length()<=0)	//filter spaces
					continue;
				if(!this.containsKey(sc))
					this.put(sc, 1);
				else
					this.put(sc, this.get(sc)+1);
			}
		}
	}
	/**
	 * get correlation of 2 strings
	 * return a value [0;1]
	 * 0=> no match, 1=> absolute match
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static float correlate(String s1,String s2)
	{
		float f=0;
		if(s1.equals(s2))
			f=1;
		else
		{
			String[] h1=s1.split(" ");
			String[] h2=s2.split(" ");
			List<String> hh = new ArrayList<String>();
			for(String s:h1.length>h2.length?h2:h1);
			float dmatch=1.0f/(float)Math.max(h1.length, h2.length);
			for(String h:h1.length>h2.length?h1:h2)
			{
				if(hh.contains(h))
					f+=dmatch;
			}
		}
		if(f<0.5f)
		{
			float f2=0;
			CharCountMap cm1=new CharCountMap(s1);
			CharCountMap cm2 = new CharCountMap(s2);
			float dmatch=0.9f/(float)Math.max(cm1.size(),cm2.size());
			CharCountMap cmh=cm1.size()>cm2.size()?cm1:cm2;
			for(Entry<String,Integer> entry:cm1.size()>cm2.size()?cm2.entrySet():cm1.entrySet()) {
				if(cmh.containsKey(entry.getKey()))
				{
					int d=Math.min(entry.getValue(),cmh.get(entry.getKey()));
					int m=Math.max(entry.getValue(),cmh.get(entry.getKey()));
					f2+=dmatch*(float)d/(float)m;
				}
			}
			if(f2>f)
				f=f2;
		}
		return f;
	}

}
