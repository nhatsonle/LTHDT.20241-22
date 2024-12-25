package model;

public class GanttChartCell {
    private int begin,end;
    private String value;

    public GanttChartCell(int begin, int end, String value) {
        this.begin = begin;
        this.end = end;
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}