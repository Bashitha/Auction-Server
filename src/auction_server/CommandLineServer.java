/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction_server;

/**
 *
 * @author Leshan
 */
public class CommandLineServer {
    public CommandLineServer(){
		CompanyDB allowedCompanies = new CompanyDB("stocks.csv","Symbol","Security Name","Price ");
                new StockGUI(allowedCompanies).setVisible(true);
		MainServer mainServer = new MainServer(MainServer.BASE_PORT,allowedCompanies); 
		mainServer.server_loop(); 
    }
}
