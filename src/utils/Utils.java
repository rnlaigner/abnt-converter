package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static boolean containsAuthor(String line)
	{
		Pattern p = Pattern.compile("[A-Z]+[,] ([A-Z]. [A-Z].|[A-Z].)"); 
		Matcher m = p.matcher(line);
		
		return m.find();
		
	}
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	public static boolean isNumericRegex(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
}
