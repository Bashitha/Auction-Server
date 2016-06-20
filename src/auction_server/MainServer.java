/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction_server;

 import java.io.IOException;
import static java.lang.System.out;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Leshan
 */  
   


public class MainServer { 

    /* Some constants */     
    public static final int BASE_PORT = 2000;  // do not change    


    /* local data for the server 
     * Every main server is defined in terms of the port it 
     * listens and the database of allowed users 
     */ 
    private ServerSocket serverSocket=null;  // server Socket for main server 
    private CompanyDB allowedCompanies=null;     // companies in the server

    public MainServer(int socket, CompanyDB users) {
		this.allowedCompanies = users; 

		try { 
			this.serverSocket = new ServerSocket(socket); 
		} catch (IOException e) { 
			System.out.println(e); 
		}
    }

    /* each server will provide the following functions to 
     * the public. Note that these are non-static 
     */ 
   
    	
    /*to detrmine whether the company entered by the client is a valid company*/
    public boolean isCompanyValid(String symbol) { 

	return this.allowedCompanies.getAllCompanies().get(symbol)!=null;
    }

    /*to access all the allowed companies*/
    public CompanyDB toGetList(){
        return allowedCompanies;
    }

   

    public void server_loop() { 
	try { 
	    while(true) { 
			Socket socket = this.serverSocket.accept(); 
			ConnectionServer worker = new ConnectionServer(this); 
			worker.handleConnection(socket); 
	    }
	} catch(IOException e) { 
	    out.println(e);
	}
    }// end server_loop 
}


	





    
