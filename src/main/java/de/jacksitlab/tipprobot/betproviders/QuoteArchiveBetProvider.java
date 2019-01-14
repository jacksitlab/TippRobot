package de.jacksitlab.tipprobot.betproviders;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchBetQuote;
import de.jacksitlab.tipprobot.data.MatchBetQuoteCollection;
import de.jacksitlab.tipprobot.data.MatchCollection;
import de.jacksitlab.tipprobot.data.TeamCollection;
import de.jacksitlab.tipprobot.http.BaseHTTPClient;
import de.jacksitlab.tipprobot.http.BaseHTTPResponse;
import de.jacksitlab.tipprobot.http.HtmlDocument;

public class QuoteArchiveBetProvider extends BaseHTTPClient implements BaseBetProvider {

	private static final String BASEURL = "https://www.wettportal.com/";
	private static String URI = "/lib/ajax/getArchivedEvents.php?partner=wettportal&lang=de&sport_id=4&region_id=11&league_id=%d&fromdate=%s&tilldate=%s&team=";
	private static String RES_TESTFILENAME = "betarchivetest.html";
	private static final int LIGA1_ID = 20;
	private static final String FROMDATE = "08%2F01%2F2018";
	private static final String TILLDATE = "01%2F05%2F2019";
	private final MatchCollection matches;
	private final TeamCollection teams;
	private static final String matchRegex = "([a-zA-Z0-9\\u00C0-\\u017F. ]*) - ([a-zA-Z0-9\\u00C0-\\u017F. ]*)";
	private static final String timeRegex = "([0-9]+):([0-9]+)";
	private static final String dateRegex = "([0-9]+).([0-9]+).([0-9]+)";
	private static final String quoteRegex =  "^([0-9]+\\.*[0-9]*)[ ]*\\(.*\\)$";

	public QuoteArchiveBetProvider(MatchCollection matches, TeamCollection teams) {
		super(BASEURL, false);
		this.matches = matches;
		this.teams = teams;
	}

	@Override
	public MatchBetQuoteCollection getBets(int liga, int gameday) {

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "text/html, */*; q=0.01");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Accept-Language", "de-DE,de;q=0.9,en-US;q=0.8,en;q=0.7");
		// Cookie: PHPSESSID=i4otqmoqt54qt1iqa6rps7gj80; cookieconsent_status=dismiss
		headers.put("DNT", "1");
		headers.put("Referer", "https://www.wettportal.com/quotenarchiv/");
		headers.put("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36 Vivaldi/2.2.1388.37");
		BaseHTTPResponse response = null;
		try {
			response = this.sendRequest(String.format(URI, LIGA1_ID, FROMDATE, TILLDATE), "GET", "", headers);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (response != null) {
			MatchBetQuoteCollection c = parseSite(response, this.matches, this.teams);
		}
		return null;
	}

	private MatchBetQuoteCollection parseSite(BaseHTTPResponse response, MatchCollection mc, TeamCollection tc) {
		// TODO Auto-generated method stub
		return null;
	}

	public static MatchBetQuoteCollection parseTestSite(MatchCollection mc, TeamCollection tc) {
		MatchBetQuoteCollection c = new MatchBetQuoteCollection();
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL url = classloader.getResource(RES_TESTFILENAME);
		HtmlDocument doc = null;
		try {
			doc = new HtmlDocument(new File(url.toURI()));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (doc != null) {
			final Pattern matchPattern = Pattern.compile(matchRegex);
			final Pattern quotePattern = Pattern.compile(quoteRegex);
			final Pattern datePattern = Pattern.compile(dateRegex);
			final Pattern timePattern = Pattern.compile(timeRegex);
			Matcher matcher;
			// ".table-type-liga-1 > tbody > tr"
			Elements tables = doc.select(".table-type-liga-1");
			Elements trs, tds;
			Date date;
			SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			for (Element table : tables) {
				date = null;
				tds = table.select("thead > tr > th");
				if (!tds.isEmpty()) {
					for (Element td : tds) {
						matcher = datePattern.matcher(td.text());
						if (matcher.find()) {
							try {
								date = df.parse(td.val());
							} catch (ParseException e) {
							}
							break;
						}
					}
				}
				trs = table.select("tbody > tr");
				if (!trs.isEmpty()) {
					Match match = null;
					String qHome = null, qDraw = null, qGuest = null;
					for (Element tr : trs) {
						tds = tr.select("td");
						if (!tds.isEmpty()) {
							for (Element td : tds) {
								Elements a = td.select("a");
								if (!a.isEmpty()) {
									matcher = matchPattern.matcher(a.get(0).text());
									if (matcher.find()) {
										String home = matcher.group(1);
										String guest = matcher.group(2);
										match = mc.find(tc.find(home), tc.find(guest));
									}
								} else {
									matcher = quotePattern.matcher(td.text());
									if (matcher.find()) {
										if (qHome == null)
											qHome = matcher.group(1);
										else if (qDraw == null)
											qDraw = matcher.group(1);
										else if (qGuest == null)
											qGuest = matcher.group(1);
									}
								}
							}
							if(match!=null && qHome!=null && qDraw!=null && qGuest!=null)
							{
								try {
									MatchBetQuote b = new MatchBetQuote(match, 
											Float.parseFloat(qHome),
											Float.parseFloat(qDraw),
											Float.parseFloat(qGuest));
									c.add(b);
								}
								catch (Exception e) {
									
								}
							}
						}
					}
				}
			}
		}
		return c;
	}
}
