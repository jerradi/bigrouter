package com.undersnow.apis.bigRouter.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fedor on 25.11.2016.
 */

public class M3UParser {

    private final String EXT_M3U = "#EXTM3U";
    private final String EXT_PLAYLIST_NAME = "#PLAYLIST";
    private final String EXT_INF = "#EXTINF";
    private final String EXT_LOGO = "tvg-logo";
    private final String EXT_URL = "http://";

    public String convertStreamToString(InputStream is) {
        try {
            return new Scanner(is).useDelimiter("\\A").next();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public M3UPlaylist parseFile(InputStream inputStream) throws FileNotFoundException {
        M3UPlaylist m3UPlaylist = new M3UPlaylist();
        List<M3UItem> playlistItems = new ArrayList<>();
        Scanner in = new Scanner(inputStream);
        while (in.hasNext()) {
			String s= in.nextLine().trim();
			if(s.contains(EXT_M3U)) continue;
			if(s.startsWith(EXT_INF)) {
				
				  M3UItem playlistItem = new M3UItem();
				String  patternString  = "#EXTINF:(?<index>[A-Z0-9a-z\\-]+),(?<name>.+)$"  ;
				Pattern pattern = Pattern.compile(patternString);
				Matcher matcher = pattern.matcher(s);
			 
				if (matcher.find()) {
					   playlistItem.setItemName( matcher.group("name"));
				 
//					System.out.println("index: " + matcher.group("index"));
				}
				
	                
	               
	                while(in.hasNext() && (s=in.nextLine()).startsWith("#")) {
	    				in.hasNextLine();
	    			}
	                if(in.hasNextLine())  { playlistItem.setItemUrl(s); playlistItems.add(playlistItem);}
			}
			
		}
        
      
        m3UPlaylist.setPlaylistItems(playlistItems);
        return m3UPlaylist;
    }
}
