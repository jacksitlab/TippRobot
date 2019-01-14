package de.jacksitlab.tipprobot.http;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HtmlDocument {

	private final Document doc;
	public HtmlDocument(File in) throws IOException
	{
		this.doc = Jsoup.parse(in, "UTF-8", "");
		
	}
	public Elements getElementsByClass(String className) {
		return this.doc.getElementsByClass(className);
	}
	public Elements select(String query)
	{
		return this.doc.select(query);
	}
	
}
