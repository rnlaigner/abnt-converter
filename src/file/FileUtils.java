package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

import org.apache.commons.io.IOUtils;


public class FileUtils 
{
	
	public String readFileWithoutExternalLibraries(String filename)
	{
	    String content = null;
	    File file = new File(filename); // For example, foo.txt
	    FileReader reader = null;
	    try {
	        reader = new FileReader(file);
	        char[] chars = new char[(int) file.length()];
	        reader.read(chars);
	        content = new String(chars);
	        reader.close();
	        
	        if(reader != null){
	            reader.close();
	        }
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    } 
	    return content;
	}
	
	@SuppressWarnings("deprecation")
	public static String readFile(String filename)
	{
		String everything = null;
		try(FileInputStream inputStream = new FileInputStream(filename)) {     
		    everything = IOUtils.toString(inputStream);
		    // do something with everything string
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return everything;
	}
	
	public static String readFileJoinConsecutiveLines(String filename)
	{
		String everything = null;
		BufferedReader br;
		FileReader fr;
		try {
			
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    boolean firstLine = true;
	
		    while (line != null) {
		    	
		    	//line = line.replace("\n","");
		        
		        if(line.equals(""))
		        {
		        	sb.append(System.lineSeparator());
		        	sb.append(System.lineSeparator());
		        	firstLine = true;
		        }
		        else 
		        {
		        	if(firstLine ){
		        		firstLine = false;
		        		sb.append(line);
		        	} else {
		        		sb.append(" ");
		        		sb.append(line);
		        	}
		        }
		        line = br.readLine();
		    }
		    
		    everything = sb.toString();
		    
		    //System.out.println(everything);
		    
		    br.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return everything;
		
	}
	
}
