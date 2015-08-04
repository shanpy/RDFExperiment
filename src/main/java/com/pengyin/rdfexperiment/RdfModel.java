package com.pengyin.rdfexperiment;

import java.util.ArrayList;

public class RdfModel {
	private String hasTitle;
	private String hasDate;
	private ArrayList<String> hasAuthor;
	public String getHasTitle() {
		return hasTitle;
	}
	public void setHasTitle(String hasTitile) {
		this.hasTitle = hasTitile;
	}
	public String getHasDate() {
		return hasDate;
	}
	public void setHasDate(String hasDate) {
		this.hasDate = hasDate;
	}
	public ArrayList<String> getHasAuthor() {
		return hasAuthor;
	}
	public void setHasAuthor(ArrayList<String> hasAuthor) {
		this.hasAuthor = hasAuthor;
	}
}
