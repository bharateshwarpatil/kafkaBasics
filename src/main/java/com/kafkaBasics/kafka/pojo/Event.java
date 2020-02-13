package com.kafkaBasics.kafka.pojo;


public class Event  {

    private String type;

    private String rawData;

    private String tags;

    private int index; //really :)

    public Event(){}

    public Event(String rawData, String tags) {
        this.type = type;
        this.rawData = rawData;
        this.tags = tags;
    }

    public Event(String type, String rawData, String tags,int index) {
        this(rawData,tags);
        this.type = type;
        this.index=index;
    }


    public void setType(String type) {
        this.type = type;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getType() {
        return type;
    }

    public String getRawData() {
        return rawData;
    }

    public String getTags() {
        return tags;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Event{" +
                "type='" + type + '\'' +
                ", rawData='" + rawData + '\'' +
                ", tags='" + tags + '\'' +
                ", index=" + index +
                '}';
    }
}
