package shopping.desafio;

import java.math.BigDecimal;

public class Price {

	private BigDecimal price;
	
	
	private String currency;

	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
