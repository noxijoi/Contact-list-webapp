package contactsapp.command.GETCommands.dto;

public class PageInfo {
    public int prevPageN;
    public int nextPageN;
    public int lastPageN;

    public PageInfo(Integer pageN, int recordsNum, int size) {
        this.prevPageN = pageN > 1 ? pageN - 1 : 1;
        this.lastPageN = recordsNum % size == 0 ? recordsNum / size : recordsNum / size + 1;
        this.nextPageN = this.lastPageN > pageN ? pageN + 1 : pageN;
    }
}
