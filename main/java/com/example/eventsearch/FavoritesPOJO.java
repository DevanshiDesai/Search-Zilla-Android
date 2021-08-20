package com.example.eventsearch;

public class FavoritesPOJO {
    private String title;
    private String subTitle;
    private String date;
    private String segmentId;
    private String id;

    public FavoritesPOJO(String title, String subTitle, String date, String segmentId, String id) {
        this.title = title;
        this.subTitle = subTitle;
        this.date = date;
        this.segmentId = segmentId;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String imageUrl) {
        this.segmentId = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
