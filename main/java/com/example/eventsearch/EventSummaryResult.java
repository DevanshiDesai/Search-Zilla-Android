package com.example.eventsearch;

public class EventSummaryResult {
    private String index;
    private String date;
    private String name;
    private String category;
    private String venue;
    private String id;
    private String toolTipName;
    private String toolTipReq;
    private String segmentId;

    public EventSummaryResult(String index, String date, String name, String category, String venue, String id, String toolTipName, String toolTipReq) {
        this.index = index;
        this.date = date;
        this.name = name;
        this.category = category;
        this.venue = venue;
        this.id = id;
        this.toolTipName = toolTipName;
        this.toolTipReq = toolTipReq;
    }

    public String getToolTipReq() {
        return toolTipReq;
    }

    public void setToolTipReq(String toolTipReq) {
        this.toolTipReq = toolTipReq;
    }

    public String getToolTipName() {
        return toolTipName;
    }

    public void setToolTipName(String toolTipName) {
        this.toolTipName = toolTipName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }
}
