package com.bijlipay.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "notifications")
//@JsonIgnoreProperties(value = {"data"})
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notification_id")
    protected Long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "notification_region", referencedColumnName = "region_id")
    protected Region notificationRegion;

    @Column(name = "notification_title", nullable = true)
    protected String notificationTitle;

    @Column(name = "notification_reason", nullable = true)
    protected String notificationReason;

    @Column(name = "notification_redirectTo", nullable = true)
    protected String notificationRedirectTo;

    @Column(name = "notification_seen", columnDefinition = "tinyint(1) default 0")
    protected boolean notificationSeen;

    @JsonBackReference
    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    protected User user;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "notification_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    protected Date notificationDate;

    @Column(name = "notification_data", columnDefinition = "json")
    protected String data;

    public Notification() {
    }

    public Notification(Long notif_id, Region region, String notif_title, String notif_reason, String notif_redirectTo, boolean notif_seen, Date notif_date) {

        this.id = notif_id;
        this.notificationRegion = region;
        this.notificationTitle = notif_title;
        this.notificationReason = notif_reason;
        this.notificationRedirectTo = notif_redirectTo;
        this.notificationSeen = notif_seen;
        this.notificationDate = notif_date;
    }

    public boolean isNotificationSeen() {
        return notificationSeen;
    }

    public void setNotificationSeen(boolean notificationSeen) {
        this.notificationSeen = notificationSeen;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public Region getNotificationRegion() {
        return notificationRegion;
    }

    public void setNotificationRegion(Region notificationRegion) {
        this.notificationRegion = notificationRegion;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationReason() {
        return notificationReason;
    }

    public void setNotificationReason(String notificationReason) {
        this.notificationReason = notificationReason;
    }

    public String getNotificationRedirectTo() {
        return notificationRedirectTo;
    }

    public void setNotificationRedirectTo(String notificationRedirectTo) {
        this.notificationRedirectTo = notificationRedirectTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getData() {
        return data;
    }

    public void setData(String notificationData) {
        this.data = notificationData;
    }

    @Override
    public String toString() {
        return "Notification{" + "notif_id=" + id + ", region=" + notificationRegion + ", notif_title=" + notificationTitle + ", notif_reason=" + notificationReason + ", notif_redirectTo=" + notificationRedirectTo + ", notif_seen=" + notificationSeen + ", notif_date=" + notificationDate + '}';
    }

}
