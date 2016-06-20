/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
/**
 *
 * @author Leshan
 */

    

public class ConnectionServer implements Runnable { 
    // some constants 
    public static final int WAIT_CLIENT_NAME = 0; 
    public static final int CLIENT_DONE = 1;//client name is confirmed
    public static final int COMPANY_DONE = 2;//company to bid is selected by the client

   
    // per connection variables
    private Socket mySocket; // connection socket per thread 
    private int currentState; 
    private String clientName; 
    private MainServer mainServer;
    private Company selected_company;
    private String companyName;
    private double previous_price;
    private double entered_bid;

    public ConnectionServer(MainServer mainServer) { 
		this.mySocket = null; // we will set this later 
		this.currentState = WAIT_CLIENT_NAME; 
		this.clientName = null; 
		this.mainServer = mainServer; 
		// who created me. He should give some interface 
    }

    public boolean handleConnection(Socket socket) { 
		this.mySocket = socket; 
		Thread newThread = new Thread(this); 
		newThread.start(); 
		return true; 
    }

    public void run() { // can not use "throws .." interface is different
        BufferedReader in=null; 
        PrintWriter out=null; 
        try { 
                in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(mySocket.getOutputStream()));

                String line;
                out.println("Enter Client name: ");
                for(line = in.readLine(); line != null && !line.equals("quit"); line = in.readLine()) { 	

                        switch(currentState) { 
                                case WAIT_CLIENT_NAME: 
                                        // we are waiting for login name 
                                        
                                        clientName = line;
                                        currentState = CLIENT_DONE; 
                                        out.println("Welcome "+clientName);
                                        out.println("Enter a company symbol :");
                                        break;
                                        /*****************************/
                                case CLIENT_DONE:
                                       
                                        if(mainServer.isCompanyValid(line)){
                                            currentState = COMPANY_DONE;
                                            selected_company=mainServer.toGetList().findCompany(line);
                                            companyName= selected_company.getSecurity_name();
                                            previous_price=selected_company.getPrice();
                                            out.println(companyName+"  selected.previous bid is "+previous_price);
                                            out.println("Enter your bid: ");
                                            
                                        } else{
                                            currentState=CLIENT_DONE;
                                            out.println("Enter a valid company symbol");
                                        }
                                        break;
                                case COMPANY_DONE:
                                    
                                    entered_bid=Double.parseDouble(line);
                                    if(entered_bid>previous_price){
                                        selected_company.addBid(entered_bid);
                                        selected_company.addBidder(clientName);
                                        out.println(" ");
                                        out.println(clientName+" bidded "+entered_bid);
                                    }else{
                                        out.println("Sorry.your bid can not be accepted..");
                                    }
                                    currentState = CLIENT_DONE;
                                    out.println("Enter a company symbol :");
                                    out.println();
                                    
                                    break;
                                default: 
                                        out.println("Undefined state"); 
                                        return; 
                        } // case 

                 
                out.flush(); // flush to network

	    } // for 

	    // close everything 
	    out.close(); 
	    in.close(); 
	    this.mySocket.close(); 
	} // try 	     
	catch (IOException e) { 
	    out.println(e); 
	} 
    }
}

    
    


