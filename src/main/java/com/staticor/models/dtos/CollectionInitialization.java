package com.staticor.models.dtos;

public class CollectionInitialization {

    private String userId;
    private String collectionName;
    private String collectionDesc;
    private String connectionName;
    private String connectionDesc;
    private String connectionHost;
    private String connectionPort;
    private String connectionDbName;
    private String connectionUsername;
    private String connectionPassword;
    private Long databaseId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionDesc() {
        return collectionDesc;
    }

    public void setCollectionDesc(String collectionDesc) {
        this.collectionDesc = collectionDesc;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getConnectionDesc() {
        return connectionDesc;
    }

    public void setConnectionDesc(String connectionDesc) {
        this.connectionDesc = connectionDesc;
    }

    public String getConnectionHost() {
        return connectionHost;
    }

    public void setConnectionHost(String connectionHost) {
        this.connectionHost = connectionHost;
    }

    public String getConnectionPort() {
        return connectionPort;
    }

    public void setConnectionPort(String connectionPort) {
        this.connectionPort = connectionPort;
    }

    public String getConnectionDbName() {
        return connectionDbName;
    }

    public void setConnectionDbName(String connectionDbName) {
        this.connectionDbName = connectionDbName;
    }

    public String getConnectionUsername() {
        return connectionUsername;
    }

    public void setConnectionUsername(String connectionUsername) {
        this.connectionUsername = connectionUsername;
    }

    public String getConnectionPassword() {
        return connectionPassword;
    }

    public void setConnectionPassword(String connectionPassword) {
        this.connectionPassword = connectionPassword;
    }

    public Long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }
}
