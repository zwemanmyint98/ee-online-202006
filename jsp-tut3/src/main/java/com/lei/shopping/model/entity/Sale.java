package com.lei.shopping.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Sale implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	private LocalDateTime saleDate;

	@OneToMany(mappedBy = "sale", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
	private List<SaleDetails> details = new ArrayList<>();
	
	public void addDetails(SaleDetails sd) {
		sd.setSale(this);
		details.add(sd);
	}
	
	public String getDate() {
		return null == saleDate ? "" : saleDate.format(DF);
	}
	
	public int getSubTotal() {
		return details.stream().mapToInt(d -> d.getQuentity() * d.getProduct().getPrice()).sum();
	}
	
	public int getTax() {
		return getSubTotal() / 100 * 5;
	}
	
	public int getTotal() {
		return getSubTotal() + getTax();
	}
}
