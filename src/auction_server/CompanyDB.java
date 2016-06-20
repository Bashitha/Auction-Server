/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction_server;

import java.io.*;
import java.util.*;

/**
 *
 * @author Leshan
 */
public class CompanyDB {
    
    private  Map<String, Company> companyList; 
    private String [] fields; 

    public CompanyDB(String csvFile, String symbol, String securityName ,String price)  { 
		FileReader fileRd=null; 
		BufferedReader reader=null; 

		try { 
			fileRd = new FileReader(csvFile); 
			reader = new BufferedReader(fileRd); 

			/* read the CSV file's first line which has 
			 * the names of fields. 
			 */
			String header = reader.readLine(); 
			fields = header.split(",");// keep field names 

			// find where the key and the value are 
			int symbolIndex = findIndexOf(symbol); 
                        
			int securityNameIndex = findIndexOf(securityName);
                        
                        int priceIndex = findIndexOf(price);
                        

			if(symbolIndex == -1 || securityNameIndex == -1 || priceIndex == -1) 
			throw new IOException("CSV file does not have data"); 
			// note how you can throw a new exception 

			// get a new hash map
			companyList = new HashMap<String, Company>(); 

			/* read each line, getting it split by , 
			 * use the indexes to get the key and value 
			 */
			String [] tokens;
                        Company company=null;
			for(String line = reader.readLine(); line != null; line = reader.readLine()) { 
				tokens = line.split(",");
                                try{
                                    company = new Company(tokens[symbolIndex],tokens[securityNameIndex],Double.parseDouble(tokens[priceIndex]));
                                    
                                    companyList.put(tokens[symbolIndex], company);
                                }catch(NumberFormatException e){
                                    
                                }
			}
			                 
			if(fileRd != null) fileRd.close();
			if(reader != null) reader.close();
			
			// I can catch more than one exceptions 
		} catch (IOException e) { 
			System.out.println(e);
			System.exit(-1); 
		} catch (ArrayIndexOutOfBoundsException e) { 
			System.out.println("Malformed CSV file");
			System.out.println(e);
		}
    }

    private int findIndexOf(String key) { 
	for(int i=0; i < fields.length; i++) 
	    if(fields[i].equals(key)) return i; 
	return -1; 
    }
	
    //to find a company 
    public Company findCompany(String key) { 
		return companyList.get(key); 
    }
    
    public HashMap getAllCompanies(){
        return (HashMap) companyList;
    }

}
	    


