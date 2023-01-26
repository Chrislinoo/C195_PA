package model;

public class TypeAPT {
    private String typeReport;
    private int totalCount;

    public String getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(String typeReport) {
        this.typeReport = typeReport;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public TypeAPT(String typeReport, int totalCount) {
        this.typeReport = typeReport;
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return typeReport;
    }
}
