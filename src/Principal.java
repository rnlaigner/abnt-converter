import java.util.List;

import business.Converter;
import file.FileUtils;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String filePath = FileUtils.readFile("file.properties");
		
		//System.out.println(filePath);
		
		String fileContents = FileUtils.readFileJoinConsecutiveLines(filePath);
		
		//System.out.println(fileContents);
		
		Converter conv = new Converter();
		
		List<String> output = conv.convertABNTtoUS(fileContents);
		
		System.out.println(output);
	}

}
