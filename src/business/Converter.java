package business;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import utils.Utils;

public class Converter {	
	
	private String convertAuthorLastName(String name)
	{	
		name = name.replace(" ","");
		return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
	}
	
	public List<String> convertABNTtoUS(String fileContents)
	{
		
		StringBuilder sb = new StringBuilder();

		Pattern p = Pattern.compile("(\\n\\r|\\n|\\r){2}");     
		/*if your text file has \r\n as the newline character then use Pattern p = Pattern.compile("\\r\\n[\\r\\n]+");*/
		String[] contentSplitted = p.split(fileContents);
		
		List<String> result = new ArrayList<String>();
		
		for(String line : contentSplitted)
		{
				line = line.replace("\n","");
			
				String[] authors = line.split(";");
				
				boolean oneAuthor = true;
				
				boolean moreThanTwoAuthors;
				
				moreThanTwoAuthors = authors.length > 2 ? true : false;
				
				for(String author : authors)
				{
					//primeiro verifico se ha caracteres minusculos na string
					boolean hasLowercase = !author.equals(author.toUpperCase());
					String str;
					if(hasLowercase)
					{
						str = buildFinalInfo(author, oneAuthor);
					}
					else
					{
						str = processAuthor(author,moreThanTwoAuthors);
						oneAuthor = false;
					}
					sb.append(str);
				}
				result.add(sb.toString());
				sb = new StringBuilder();
		}
		
		return result;
		
	}
	
	private String processAuthor(String str)
	{
		return processAuthor(str,true);
	}
	
	private String processAuthor(String str, boolean moreThanTwoAuthors)
	{
		
		StringBuilder sb = new StringBuilder();
		String parts[] = str.split(",");

		String lastName = "";

		lastName = convertAuthorLastName(parts[0]);
		
		try{
			sb.append(parts[1]);
			sb.append(" ");
			sb.append(lastName);
			if(moreThanTwoAuthors)
			{
				sb.append(",");
			}
		}
		catch(Exception e)
		{
			System.out.println("Aqui");
		}
		
		return sb.toString();
	}
	
	private String getPublicationFora(String str)
	{
		String parts[] = str.split(",");
		String fora = "";
		
		if (	str.toLowerCase().contains("workshop on") 
			 || str.toLowerCase().contains("congress on")
			 || str.toLowerCase().contains("conference on")
			 || str.toLowerCase().contains("symposium on")
			)	
		{
			String division[] = parts[0].split(" on ");
			try
			{
				fora = "in " + division[1] + "," + division[0] + " on, ";
				return fora;
			}
			catch(Exception e)
			{
				System.out.println("AQUI");
			}
		}		
		
		try
		{
			fora = parts[0].substring(1);
		}
		catch(Exception e)
		{
			System.out.println("Aqui");
		}
		return "in " + fora  + ",";
	}
	
	private String getIssuer(String str)
	{
		String parts[] = str.split(",");
		return parts[1];
	}
	
	private String getYear(String str)
	{
		String parts[] = str.split(",");
		return parts[2];
	}
	
	private String buildFinalInfo(String str, boolean oneAuthor)
	{
		String article = "";
		String fora = "";
		String issuer = "";
		String year = "";
		String author_ = "";
		int index = 0;
		
		StringBuilder sb = new StringBuilder();
		String parts[] = str.split("\\.");
		
		String commaAnd = " and ";
		
		//tornar generico, enquanto houver autor...
		
		if(parts[1].substring(1).length() == 1)
		{
			if(parts.length == 7)
			{
				
				if(oneAuthor)
				{
					author_ = processAuthor(parts[index] + "." + parts[index+1] + ".");
				}
				else
				{
					
					author_ = commaAnd + processAuthor(parts[index] + "." + parts[index+1] + ".");
				}
				
				article = getArticleName(parts[index+2]);
				
		
				fora = getPublicationFora( parts[index+3] );
				
				if (parts[index+4].substring(1).length() == 1)
				{
					issuer = "";
				}
				else if (parts[index+4].split(",")[1].substring(1).equals("p"))
				{
					issuer = " vol. " + parts[index+4].split(",")[0] + ", ";
				}
				else {
					issuer = parts[index+4].split(",")[0] + ",";
				}
				
				//quebra o 5 para pegar as paginas
				String[] pageMonth = parts[index+5].split(",");
				
				String page = pageMonth[0];
				
				String month = getMonth(pageMonth[1]);
				
				year = month + " " + parts[index+6] + ", pp." + page + ".";	
				
			}
			else if(parts.length == 6)
			{
				if(oneAuthor)
				{
					author_ = processAuthor(parts[index] + "." + parts[index+1] + ".");
				}
				else
				{
					author_ = commaAnd + processAuthor(parts[index] + "." + parts[index+1] + ".");
				}
				
				article = getArticleName(parts[index+2]);
				
				//adiciona ponto apenas se houver issuer
				fora = getPublicationFora(parts[index+3]);
				
				//get issuer for length == 5
				String[] issuerArray = parts[index+4].split(",");
				
				issuer = issuerArray[0]  + ",";
				
				year = parts[index+5] + ".";;	
				
			}
			else if(parts.length == 5)
			{
				if(oneAuthor)
				{
					author_ = processAuthor(parts[index] + "." + parts[index+1] + ".");
				}
				else
				{
					author_ = commaAnd + processAuthor(parts[index] + "." + parts[index+1] + ".");
				}
				
				article = getArticleName(parts[index+2]);
				
				//get issuer for length == 5
				String[] publicationArray = parts[index+3].split(",");
				
				fora = getPublicationFora( publicationArray[0] );
				
				issuer = publicationArray[1] + ",";
				
				year = parts[index+4] + ".";
				
			}
			else
			{
				if(oneAuthor)
				{
					author_ = processAuthor(parts[index] + "." + parts[index+1] + ".");
				}
				else
				{
					author_ = commaAnd + processAuthor(parts[index] + "." + parts[index+1] + ".");
				}
				
				article = getArticleName(parts[index+2]);
				
		
				fora = getPublicationFora(parts[index+3]);
				
				issuer = getIssuer(parts[index+3] + ",");
				
				
				
				year = getYear(parts[index+3]) + ".";
				
			}
		}
		else
		{	
			if(parts.length == 8)			
			{
				if(oneAuthor)
				{
					author_ = processAuthor(parts[index] + ".");
				}
				else
				{
					author_ = " and " + processAuthor(parts[index] + ".");
				}
				
				article = getArticleName(parts[index+1]);
				
				fora = getPublicationFora( parts[index+2] );
				
				issuer = parts[index+3].split(",")[1] + ",";
				
				//quebra o 5 para pegar as paginas
				String[] pageMonth = parts[index+4].split(",");
				
				String page = pageMonth[0];
				
				String month = getMonth(pageMonth[1]);
				
				year = month + " " + parts[index+5] + ", pp." + page + ".";
			}
			else if(parts.length == 7)			
			{
				if(oneAuthor)
				{
					author_ = processAuthor(parts[index] + ".");
				}
				else
				{
					author_ = " and " + processAuthor(parts[index] + ".");
				}
				
				article = getArticleName(parts[index+1]);
				
				fora = getPublicationFora( parts[index+2] );
				
				issuer = parts[index+3].split(",")[0]; 
				
				if(Utils.isNumeric( issuer ))
				{
					issuer = " vol. " + parts[index+3].split(",")[0] + ", no. " + parts[index+4].split(",")[0] + ", "; 
				}
				else if ( issuer.substring(1).equals("p")  )
				{
					issuer = parts[index+3].split(",")[0] + ",";
				}
				else
				{
					issuer = issuer + ",";
				}
				
				//quebra o 5 para pegar as paginas
				String[] pageMonth = parts[index+5].split(",");
				
				String page = pageMonth[0];
				
				String month = getMonth(pageMonth[1]);
				
				year = month + " " + parts[index+6] + ", pp." + page + ".";
			}
			else if(parts.length == 6)			
			{
				if(oneAuthor)
				{
					author_ = processAuthor(parts[index] + ".");
				}
				else
				{
					author_ = " and " + processAuthor(parts[index] + ".");
				}
				
				article = getArticleName(parts[index+1]);
		
				fora = getPublicationFora( parts[index+2] );
				
				issuer = parts[index+3].split(",")[1]; 
				
				if(Utils.isNumeric( issuer ))
				{
					issuer = " vol. " + parts[index+3].split(",")[0] + ", no. " + issuer + ", "; 
				}
				else if ( issuer.substring(1).equals("p")  )
				{
					issuer = parts[index+3].split(",")[0] + ",";
				}
				else
				{
					issuer = issuer + ",";
				}
				
				//quebra o 5 para pegar as paginas
				String[] pageMonth = parts[index+4].split(",");
				
				String page = pageMonth[0];
				
				String month = getMonth(pageMonth[1]);
				
				year = month + " " + parts[index+5] + ", pp." + page + ".";	
				
			}
			else if(parts.length == 5)
			{
				if(oneAuthor)
				{
					author_ = processAuthor(parts[index] + ".");
				}
				else
				{
					author_ = " and " + processAuthor(parts[index] + ".");
				}
				
				article = getArticleName(parts[index+1]);
				
				fora = getPublicationFora(parts[index+2]);
				
				String[] issuerArray = parts[index+2].split(",");
				
				if(issuerArray.length > 1)
				{
					if(issuerArray[1].substring(1).equals("v"))
					{
						issuer = " vol. " + parts[index+3].split(",")[0] + ", no. " + parts[index+3].split(",")[1] + ", "; 
					
						String[] pageYear = parts[index+4].split(",");
						
						String page = pageYear[0];

						String year_ = pageYear[1];
						
						year = year_ + ", pp." + page + ".";	
					}
					else
					{
						issuer = issuerArray[1] + ",";
						year = parts[index+4] + ", pp. " + parts[index+3].split(",")[0] + ".";
					}
				}
				else
				{
					//get issuer for length == 5
					issuerArray = parts[index+3].split(",");
					issuer = issuerArray[0] + ",";
					year = parts[index+4] + ".";
				}
				
				
			}
			else if(parts.length == 4)
			{
				if(oneAuthor)
				{
					author_ = processAuthor(parts[index] + ".");
				}
				else
				{
					author_ = " and " + processAuthor(parts[index] + ".");
				}
				
				article = getArticleName(parts[index+1]);
				
				String[] info = parts[index+2].split(",");
				
				fora = getPublicationFora( info[0] );
				
				issuer = info[1] + ",";
				
				String info_[] = parts[index+3].split(",");
				
				try
				{
					if (info_.length > 1){
						year = info_[2] + ", pp." + info_[0];
					}
					else {
						year = info_[0].substring(1);
					}
					year = year + ".";
				}
				catch(Exception e)
				{
					System.out.println("AQUI!");
				}
			}
			else if(parts.length == 3)
			{
				if(oneAuthor)
				{
					author_ = processAuthor(parts[index] + ".");
				}
				else
				{
					author_ = " and " + processAuthor(parts[index] + ".");
				}
				
				article = getArticleName(parts[index+1]);
				
				String[] info = parts[index+2].split(",");
				
				fora = getPublicationFora( info[0] );
				
				issuer = info[1] + ",";
				try{
					year = info[3];
				}
				catch(Exception e)
				{
					year = info[2];
				}
				
				year = year + ".";
			}
			else
			{
				if(oneAuthor)
				{
					author_ = processAuthor(parts[index] + ".");
				}
				else
				{
					author_ = " and " + processAuthor(parts[index] + ".");
				}
				article = getArticleName(parts[index+1]);
				
				fora = getPublicationFora(parts[index+2]);
				
				
				try{
					issuer = getIssuer(parts[index+2]) + ",";
				}
				catch(Exception e)
				{
					System.out.println("Aqui");
				}
				
				year = parts[index+3] + ".";
				
				
			}
		}
		
		sb.append(author_);
		sb.append(article);
		sb.append(fora);
		sb.append(issuer);
		sb.append(year);
		
		return sb.toString();
	}
	
	private String getArticleName(String str)
	{
		int i = 0;
		while(str.charAt(i) == ' ')
		{
			i++;
		}

		return " \"" + str.substring(i) + ",\" ";
	}
	
	private String getMonth(String str)
	{
		str = str.replace(" ","");
		switch (str) {
			
			case "jan": return "January"; 
			case "fev": return "February";
			case "mar": return "March";
			case "abr": return "April";
			case "mai": return "May";
			case "jun": return "June";
			case "jul": return "July";
			case "ago": return "August";
			case "set": return "September";
			case "out": return "October";
			case "nov": return "November";
			case "dez": return "December";
			default: return "";
		}
	}

}
