package com.example.toolrepositoryandroid;

public class Tool {
    private String toolName;
    private String toolType;
    private String toolLocation;
    private String toolDescription;
    private String toolOwner;
    private String toolID;
    private String toolImage;
    private String userID;

    public Tool() {
    }

    public Tool(String toolName, String toolType, String toolLocation, String toolDescription, String toolOwner, String toolID, String toolImage, String userID) {
        this.toolName = toolName;
        this.toolType = toolType;
        this.toolLocation = toolLocation;
        this.toolDescription = toolDescription;
        this.toolOwner = toolOwner;
        this.toolID = toolID;
        this.toolImage = toolImage;
        this.userID = userID;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getToolLocation() {
        return toolLocation;
    }

    public void setToolLocation(String toolLocation) {
        this.toolLocation = toolLocation;
    }

    public String getToolDescription() {
        return toolDescription;
    }

    public void setToolDescription(String toolDescription) {
        this.toolDescription = toolDescription;
    }

    public String getToolOwner() {
        return toolOwner;
    }

    public void setToolOwner(String toolOwner) {
        this.toolOwner = toolOwner;
    }

    public String getToolID() {
        return toolID;
    }

    public void setToolID(String toolID) {
        this.toolID = toolID;
    }

    public String getToolImage() {
        return toolImage;
    }

    public void setToolImage(String toolImage) {
        this.toolImage = toolImage;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
