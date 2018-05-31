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
public class JSONObject {

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
    private Planning planning;

    public JSONObject(String siteName, String assetSerialNumber, String type, String externalWorkOrderId, String systemStatus, String userStatus, String createdOn, String createdBy, String name, String priority, String status, Planning planning) {
        this.assetSerialNumber = assetSerialNumber;
        this.type = type;
        this.externalWorkOrderId = externalWorkOrderId;
        this.systemStatus = systemStatus;
        this.userStatus = userStatus;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.name = name;
        this.priority = priority;
        this.siteName = siteName;
        this.status = status;
        this.planning = planning;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
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

}
