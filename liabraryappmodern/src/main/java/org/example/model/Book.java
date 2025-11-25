package org.example.model;

public class Book {

    private int id;
    private String title;
    private String author;
    private int copies;
    private boolean issued;

    public Book() {}

    public Book(int id, String title, String author, int copies, boolean issued) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.copies = copies;
        this.issued = issued;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getCopies() { return copies; }
    public void setCopies(int copies) { this.copies = copies; }

    public boolean isIssued() { return issued; }
    public void setIssued(boolean issued) { this.issued = issued; }
}
