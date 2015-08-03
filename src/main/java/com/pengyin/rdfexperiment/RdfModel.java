package com.pengyin.rdfexperiment;

public class RdfModel {
	private String hasTitle;
	private String hasDate;
	private String hasAuthor;
	private String editedBy;
	private String publishedBy;
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
	public String getHasAuthor() {
		return hasAuthor;
	}
	public void setHasAuthor(String hasAuthor) {
		this.hasAuthor = hasAuthor;
	}
	public String getEditedBy() {
		return editedBy;
	}
	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}
	public String getPublishedBy() {
		return publishedBy;
	}
	public void setPublishedBy(String publishedBy) {
		this.publishedBy = publishedBy;
	}
}
