package model;

public class MonthAPT {
    private String monthName;
    private int totalCount;

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getMonthName() {
        return monthName;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public MonthAPT(String monthName, int totalCount) {
        this.monthName = monthName;
        this.totalCount = totalCount;
    }
}
