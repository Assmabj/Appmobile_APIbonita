package com.example.isibpmn.data.model;

public class ListeProcess {

    private String displayDescription;
    private  String deploymentDate;

    private String displayName;
    private String name;
    private String  description;
    private String   deployedBy;
    private String   id;
    private String   activationState;
    private String   version;
    private String   configurationState;
    private String   last_update_date;
    private String  actorinitiatorid;

    public String getDisplayDescription() {
        return displayDescription;
    }

    public void setDisplayDescription(String displayDescription) {
        this.displayDescription = displayDescription;
    }

    public String getDeploymentDate() {
        return deploymentDate;
    }

    public void setDeploymentDate(String deploymentDate) {
        this.deploymentDate = deploymentDate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeployedBy() {
        return deployedBy;
    }

    public void setDeployedBy(String deployedBy) {
        this.deployedBy = deployedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivationState() {
        return activationState;
    }

    public void setActivationState(String activationState) {
        this.activationState = activationState;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getConfigurationState() {
        return configurationState;
    }

    public void setConfigurationState(String configurationState) {
        this.configurationState = configurationState;
    }

    public String getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }

    public String getActorinitiatorid() {
        return actorinitiatorid;
    }

    public void setActorinitiatorid(String actorinitiatorid) {
        this.actorinitiatorid = actorinitiatorid;
    }
}
