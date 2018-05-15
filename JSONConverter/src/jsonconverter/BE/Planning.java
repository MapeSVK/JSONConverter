/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BE;

/**
 *
 * @author Pepe15224
 */
public class Planning {

    private String latestFinishDate;
    private String earliestStartDate;
    private String latestStartDate;
    private String estimatedTime;

//    public Planning(String latestFinishDate, String earliestStartDate, String latestStartDate, String estimatedTime) {
//        this.latestFinishDate = latestFinishDate;
//        this.earliestStartDate = earliestStartDate;
//        this.latestStartDate = latestStartDate;
//        this.estimatedTime = estimatedTime;
//    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getLatestStartDate() {
        return latestStartDate;
    }

    public void setLatestStartDate(String latestStartDate) {
        this.latestStartDate = latestStartDate;
    }

    public String getEarliestStartDate() {
        return earliestStartDate;
    }

    public void setEarliestStartDate(String earliestStartDate) {
        this.earliestStartDate = earliestStartDate;
    }

    public String getLatestFinishDate() {
        return latestFinishDate;
    }

    public void setLatestFinishDate(String latestFinishDate) {
        this.latestFinishDate = latestFinishDate;
    }

}
