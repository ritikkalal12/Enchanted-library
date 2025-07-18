package Structural;

public class Adapter_Legacy {
	public String oldTitle;
    public String oldAuthor;
    public String oldCode;

    public Adapter_Legacy(String title, String author, String code) {
        this.oldTitle = title;
        this.oldAuthor = author;
        this.oldCode = code;
    }

    public String getOldTitle() { return oldTitle; }
    public String getOldAuthor() { return oldAuthor; }
    public String getOldCode() { return oldCode; }
}

