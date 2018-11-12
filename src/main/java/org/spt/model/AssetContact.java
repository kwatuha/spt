package org.spt.model;
import java.util.Date;
public class AssetContact {

    private Integer id;
    private Asset asset;
    private Contact contact;
    private Date dateAssigned;
    private Date dateReturned;
    private String comments;
    private String status;
    private Date dateCreated;
    private String createdBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Asset getAsset() {
        return asset;
    }
    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Contact getContact() {
        return contact;
    }
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Date getDateAssigned() {
        return dateAssigned;
    }
    public void setDateAssigned(Date dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    public Date getDateReturned() {
        return dateReturned;
    }
    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }	

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
   
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
