package contactsapp.command.GETCommands.dto;

public class PageInfo {
    public int currentPage;
    public int prevPageN;
    public int nextPageN;
    public int lastPageN;
    public int pageN;
    public int recordsN;

    public PageInfo(Integer currentPage, int recordsNum, int size) {
        this.prevPageN = currentPage > 1 ? currentPage - 1 : 1;
        this.lastPageN = recordsNum % size == 0 ? recordsNum / size : recordsNum / size + 1;
        this.nextPageN = this.lastPageN > currentPage ? currentPage + 1 : currentPage;
        this.recordsN = recordsNum;
        this.pageN = lastPageN;
        this.currentPage = currentPage;
    }
}

