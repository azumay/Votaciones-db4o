package controller;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.Normalizer;

public class Controller {

	
	
	public Controller() {
		
	
	}
	
	
	
	
	public String upperText(String text) {
		
		Matcher match = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(text);
		
		StringBuffer strbf = new StringBuffer();
		while(match.find()) 
	      {
	         match.appendReplacement(strbf, match.group(1).toUpperCase() + match.group(2).toLowerCase());
	      }
		text = strbf.toString();
		
		
		return text;
	}
	
		public String formeteoTexto(String text) {
		
			
			
			text = Normalizer.normalize(text, Normalizer.Form.NFD);
			text = text.replaceAll("[^\\p{ASCII}]", "");
			
			String result = upperText(text);
			
		return result;
	    
	}
	
	
}
