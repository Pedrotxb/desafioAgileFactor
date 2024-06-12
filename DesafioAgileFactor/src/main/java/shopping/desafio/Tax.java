package shopping.desafio;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Tax {
private String type;
private BigDecimal value;
private String code;

public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public BigDecimal getValue() {
	return value;
}
public void setValue(String value) {
	String[] values = value.split("%");
	BigDecimal val= BigDecimal.valueOf(Double.valueOf(values[0]));
	val=val.divide(BigDecimal.valueOf(100));
	this.value = val;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	setValue(code);
	this.code = code;
}
}
