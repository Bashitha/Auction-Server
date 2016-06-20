/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction_server;

import java.util.ArrayList;

/**
 *
 * @author Leshan
 */
public class Company {
    private String symbol;
    private String security_name;
    private ArrayList<Double> bid_list;
    private ArrayList<String> bidders_list;
    private double price;//current_bid

    public Company() {
    }
    
    

    public Company(String symbol, String security_name,double price ) {
        this.symbol = symbol;
        this.security_name = security_name;
        this.price = price;
        this.bid_list=new ArrayList<Double>();
        this.bidders_list=new ArrayList<String>();
        this.addBid(price);
        this.addBidder(null);
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the security_name
     */
    public String getSecurity_name() {
        return security_name;
    }

    /**
     * @param security_name the security_name to set
     */
    public void setSecurity_name(String security_name) {
        this.security_name = security_name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return  price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    public void addBid(double price){
        this.bid_list.add(price);
        
    }
    public void addBidder(String name){
        this.getBidders_list().add(name);
        
    }
    /**
     * @return the bid_list
     */
    public ArrayList<Double> getBid_list() {
        return bid_list;
    }

    /**
     * @param bid_list the bid_list to set
     */
    public void setBid_list(ArrayList<Double> bid_list) {
        this.bid_list = bid_list;
    }

    /**
     * @return the bidders_list
     */
    public ArrayList<String> getBidders_list() {
        return bidders_list;
    }
    
    public double getLastBid(){
        return bid_list.get(bid_list.size()-1);
    }
    public String getLastBidder(){
        return bidders_list.get(bidders_list.size()-1);
    }
}
