package com.avnet.gears.codes.gimbal.store.bean;

/**
 * Created by 914889 on 3/15/15.
 */
public class FeedItemBean extends BaseServerDataBean {
    private String Id;
    private String Text;
    private String Status;
    private String Type;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public String toString() {
        return super.toString() +
                "FeedItemBean{" +
                "Id='" + Id + '\'' +
                ", Text='" + Text + '\'' +
                ", Status='" + Status + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }
}
