package shopping.desafio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Product {
	
	private int market;
	private int id;
	private String name;
	private String author;
	private String editor;
	private String createddate;
	private String updateddate;
	private Price price = new Price();
    private  List<Categorie> categorie = new ArrayList<Categorie>();
    ShoppingDB conn = new ShoppingDB();
  
    public void setCategories(List<Categorie> categories) {
    	this.categorie=categories;
    }
    
    public List<Categorie> getCategories(){
		return this.categorie;	
    }
    public String getCategoriesString() {
    	String categories= "";
    	for(Categorie cat : categorie) {
    		categories += cat.getName()+" ";
    	}
    	
    	return categories;
    }
    
    public double getPriceValue() {
		return price.getPrice();
	}
    public String getPrice() {
    	String price = String.valueOf(getPriceValue());
    	
		return price +" "+ this.price.getCurrency();
	}
	public void setPrice(double price) {
		this.price.setPrice(price);
	}
	public void setPriceCurrency(String currency) {
		this.price.setCurrency(currency);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMarket() {
		return market;
	}
	public void setMarket(int market) {
		this.market = market;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public String getUpdateddate() {
		return updateddate;
	}
	public void setUpdateddate(String updateddate) {
		this.updateddate = updateddate;
	}
	
}
