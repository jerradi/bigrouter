package com.undersnow.apis.bigRouter;

import com.undersnow.apis.bigRouter.utils.Channel;
import com.undersnow.apis.bigRouter.utils.M3UParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SpringBootApplication
public class BigRouterApplication {


	public static void main(String[] args) {
		SpringApplication.run(BigRouterApplication.class, args);
	}

	public static void mainss(String[] args) {
		String  patternString  = "#EXTINF:(?<index>[A-Z0-9a-z\\-]+),(?<name>.+)$" , s = "#EXTINF:-1,Star TV HD";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(s);
		System.out.println("Hello");
		if (matcher.find()) {
			System.out.println("name: " + matcher.group("name"));
			System.out.println("index: " + matcher.group("index"));
		}
	}

	public static void madin(String[] args) {
		File f = new File("/home/undersnow/Downloads/ar_281019_iptvgratuit_xyz-1-1.m3u");
		List<Channel> channels =  new ArrayList<>();
		try (InputStream is = new FileInputStream(f)) {
			channels.addAll( new M3UParser().parseFile(is).getPlaylistItems().stream().map(x->x.toChannel()).collect(Collectors.toList()));

			System.out.println("channels :" +channels.size());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("");

		}
	}
}

@RestController
class BigController {
 

	@GetMapping(path = "/addedItems")
	public List<Channel> addedItems (){
		return  channels;
	}

	
	@GetMapping(path = "/clearAll")
	public Boolean clearAll (){
		  channels.clear();
		  return true;
	}
	private   List<Channel> channels = new ArrayList<>();

 

	private boolean isLinkOk(String uri) {
		return true;
	}
		private boolean isLinksOk(String uri) {
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			con.setInstanceFollowRedirects(false);

			return con.getResponseCode() < 400;
		} catch (Exception e) {
			 return false;
		}
	
	}

	@PostMapping("/upload") // //new annotation since 4.3
	public String singleFileUpload(@RequestParam("file") MultipartFile file,

								   RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			return "Please select a file to upload";
		}
		int size = channels.size();
		try (InputStream is =  file.getInputStream()) {
			 new M3UParser().parseFile(is).getPlaylistItems().parallelStream().filter(x->isLinkOk(x.getItemUrl())).map(x->x.toChannel()).forEach(x->channels.add(x)) ;

		} catch (IOException e) {
			e.printStackTrace();

		}

		return "You successfully uploaded '" + file.getOriginalFilename() + "'  , " +(channels.size()-size) + " Item added";
	}
}

 