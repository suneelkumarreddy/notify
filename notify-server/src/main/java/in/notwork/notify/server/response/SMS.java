package in.notwork.notify.server.response;

import java.util.Date;

/**
 * Created by insreddy1 on 8/30/2015.
 */
public class SMS{
    private String group_id;
    private String id;
    private String customid;
    private String customid1;
    private String customid2;
    private String mobile;
    private String status;
    private Date senttime;
    private Date dlrtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomid() {
        return customid;
    }

    public void setCustomid(String customid) {
        this.customid = customid;
    }

    public String getCustomid1() {
        return customid1;
    }

    public void setCustomid1(String customid1) {
        this.customid1 = customid1;
    }

    public String getCustomid2() {
        return customid2;
    }

    public void setCustomid2(String customid2) {
        this.customid2 = customid2;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public Date getSenttime() {
        return senttime;
    }

    public void setSenttime(Date senttime) {
        this.senttime = senttime;
    }

    public Date getDlrtime() {
        return dlrtime;
    }

    public void setDlrtime(Date dlrtime) {
        this.dlrtime = dlrtime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SMS{");
        sb.append("group_id='").append(group_id).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", customid='").append(customid).append('\'');
        sb.append(", customid1='").append(customid1).append('\'');
        sb.append(", customid2='").append(customid2).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", senttime='").append(senttime).append('\'');
        sb.append(", dlrtime='").append(dlrtime).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
