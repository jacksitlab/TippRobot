package de.jacksitlab.tipprobot.tippalg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import de.jacksitlab.tipprobot.data.LigaTable;

public class TippAlgorithmFactory {

	private static final Logger LOG = Logger.getLogger(TippAlgorithmFactory.class.getName());
	
	public static final int ID_LIGABASED = 1;
	public static final int ID_TRENDBASED = 2;
	public static final int ID_TRENDANDLIGABASED = 3;
	
	@SuppressWarnings("rawtypes")
	private static HashMap<Integer, Class> algs;
	@SuppressWarnings("rawtypes")
	private static void init()
	{
		if(algs==null)
		{
			algs=new HashMap<Integer,Class>();
			algs.put(ID_LIGABASED,LigaTableBasedTippAlgorithm.class);
			algs.put(ID_TRENDBASED, TrendBasedTippAlgorithm.class);
			algs.put(ID_TRENDANDLIGABASED,TrendAndLigaTableBasedTippAlgorithm.class);
		}
		
	}
	public static TippAlgorithm getInstance(int id, LigaTable lt) {
		
		init();
		Class<?>[] cArg = new Class[] { LigaTable.class };
		Class<?> classToLoad;
        Object obj = null;
        String clsName = algs.get(id).getName();
        try {
        	
            classToLoad = Class.forName(clsName);
            obj = classToLoad.getConstructor(cArg).newInstance(lt);
        } catch (Exception e) {
            LOG.warn("failed to load class:" + e.getMessage());
        }

		return obj==null?null:(TippAlgorithm)obj;
	}

	public static boolean validate(int id) {
		init();
		if(algs.containsKey(id))
			return true;
		return false;
	}

	@SuppressWarnings("rawtypes")
	public static String getAlgorithmList() {
		init();
		List<String> list = new ArrayList<String>();
		for(Entry<Integer,Class> e: algs.entrySet())
			list.add(e.getKey()+": "+e.getValue().getSimpleName());
		return String.join("\n", list);
	}

}
