package com.test.trading.model;

public class TradeId implements Comparable<TradeId>{
	
	
	
	public TradeId(String id, Integer version) {
		super();
		this.id = id;
		this.version = version;
	}

	private String id;
	private Integer version;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	

	@Override
	public String toString() {
		return  id + "-" + version ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		TradeId other = (TradeId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	public int compareTo(TradeId tradeId) {
		if(id.equalsIgnoreCase(tradeId.getId())) {
			if(version.equals(tradeId.getVersion()))
				return 0;
			else if(version>tradeId.getVersion())
				return 1;
			else if(version<tradeId.getVersion())
				return -1;
		}
		return 0;
	}
}
