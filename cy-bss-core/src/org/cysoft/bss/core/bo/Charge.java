package org.cysoft.bss.core.bo;

import org.cysoft.bss.core.model.PriceComponent;
import org.cysoft.bss.core.model.PriceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Charge {
	
	private static final Logger logger = LoggerFactory.getLogger(Charge.class);
	
	private PriceComponent component=null;
	private double qty=0.0d;
	private double price=0.0d;
	private double priceTot=0.0d;
	private double amount=0.0d;
	private double amountTot=0.0d;
	private double vat=0.0;
	private double vatAmount=0.0d;
	
	public Charge(PriceComponent component){
		this.component=component;
	}
	
	public PriceComponent getComponent() {
		return component;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getPriceTot() {
		return priceTot;
	}
	public void setPriceTot(double priceTot) {
		this.priceTot = priceTot;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public double getAmountTot() {
		return amountTot;
	}
	public void setAmountTot(double amountTot) {
		this.amountTot = amountTot;
	}
	public double getVat() {
		return vat;
	}
	public void setVat(double vat) {
		this.vat = vat;
	}
	public double getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(double vatAmount) {
		this.vatAmount = vatAmount;
	}
	
	public void calc(){
		
		logger.info("Charge.calc() >>>");

		if (priceTot!=0)
			price=priceTot*100/(100+vat);
		else
			priceTot=price+price*vat/100;
	
		amount=price;
		amountTot=priceTot;
			
		if (component.getPriceType().getCode().equals(PriceType.TYPE_USG)){
			if (component.getCode().equals(PriceComponent.CODE_USG_QXP)){
				amount=qty*price;
				amountTot=qty*priceTot;
				}
		}
			
		vatAmount=amountTot-amount;
		logger.info("Charge.calc() <<< :"+this.toString());
	}
	
	@Override
	public String toString() {
		return "Charge [component=" + component + ", qty=" + qty + ", price=" + price + ", priceTot=" + priceTot
				+ ", amount=" + amount + ", vat=" + vat + ", vatAmount=" + vatAmount + "]";
	}

	

}
