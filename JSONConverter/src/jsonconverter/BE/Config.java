/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BE;

/**
 *
 * @author Samuel
 */
public class Config {

    private int cinfig_id;
    private String siteName;
    private String assetSerialNumber;
    private String type;
    private String externalWorkOrderId;
    private String systemStatus;
    private String userStatus;
    private String createdOn;
    private String createdBy;
    private String name;
    private String priority;
    private String status;
    private String latestFinishDate;
    private String earliestStartDate;
    private String latestStartDate;
    private String estimatedTime;
    private String configName;
    private boolean privacy;
    private String creatorName;

    public Config(int cinfig_id, String siteName, String assetSerialNumber, String type, 
                  String externalWorkOrderId, String systemStatus, String userStatus, String createdOn, 
                  String createdBy, String name, String priority, String status, String latestFinishDate, 
                  String earliestStartDate, String latestStartDate, String estimatedTime, String configName,
                  boolean privacy, String creatorName) {
        this.cinfig_id = cinfig_id;
        this.siteName = siteName;
        this.assetSerialNumber = assetSerialNumber;
        this.type = type;
        this.externalWorkOrderId = externalWorkOrderId;
        this.systemStatus = systemStatus;
        this.userStatus = userStatus;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.name = name;
        this.priority = priority;
        this.status = status;
        this.latestFinishDate = latestFinishDate;
        this.earliestStartDate = earliestStartDate;
        this.latestStartDate = latestStartDate;
        this.estimatedTime = estimatedTime;
        this.configName = configName;
        this.privacy = privacy;
        this.creatorName = creatorName;
    }

    public boolean isPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
    
    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(String systemStatus) {
        this.systemStatus = systemStatus;
    }

    public String getExternalWorkOrderId() {
        return externalWorkOrderId;
    }

    public void setExternalWorkOrderId(String externalWorkOrderId) {
        this.externalWorkOrderId = externalWorkOrderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssetSerialNumber() {
        return assetSerialNumber;
    }

    public void setAssetSerialNumber(String assetSerialNumber) {
        this.assetSerialNumber = assetSerialNumber;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getCinfig_id() {
        return cinfig_id;
    }

    public void setCinfig_id(int cinfig_id) {
        this.cinfig_id = cinfig_id;
    }

    @Override
    public String toString() {
        return configName;
    }

}
