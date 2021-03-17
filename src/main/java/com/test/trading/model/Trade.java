package com.test.trading.model;

import java.io.Serializable;
import java.util.Date;

public class Trade implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TradeId tradeId;
	private String counterPartyId;
	private String bookId;
	private Date maturityDate;
	private Date createdDate;
	private Character expired;

	
	public TradeId getTradeId() {
		return tradeId;
	}
	
	public void setTradeId(TradeId tradeId) {
		this.tradeId = tradeId;
	}
	
	public String getCounterPartyId() {
		return counterPartyId;
	}

	public void setCounterPartyId(String counterPartyId) {
		this.counterPartyId = counterPartyId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Character getExpired() {
		return expired;
	}

	public void setExpired(Character expired) {
		this.expired = expired;
	}
	
	public boolean isValid() {		
		Date date=new Date(); 
		if(this.getMaturityDate().compareTo(date)<0) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return tradeId.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((maturityDate == null) ? 0 : maturityDate.hashCode());
		result = prime * result + ((tradeId == null) ? 0 : tradeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (maturityDate == null) {
			if (other.maturityDate != null)
				return false;
		} else if (!maturityDate.equals(other.maturityDate))
			return false;
		if (tradeId == null) {
			if (other.tradeId != null)
				return false;
		} else if (!tradeId.equals(other.tradeId))
			return false;
		return true;
	}


	
	

}
