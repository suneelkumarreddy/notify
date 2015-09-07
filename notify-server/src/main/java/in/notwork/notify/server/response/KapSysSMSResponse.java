package in.notwork.notify.server.response;

import java.util.Date;
import java.util.Objects;

/**
 * Created by insreddy1 on 8/27/2015.
 */
public class KapSysSMSResponse {

    private String mobileNumber;
    private String messageId;
    private String status;
    private String responseCode;
    private String groupId;
    private String responseDetails;
    private String requestContent;
    private Date sentTime;
    private Date deliverTime;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }


    public String getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(String responseDetails) {
        this.responseDetails = responseDetails;
    }


    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KapSysSMSResponse that = (KapSysSMSResponse) o;
        return Objects.equals(mobileNumber, that.mobileNumber) &&
                Objects.equals(messageId, that.messageId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(responseCode, that.responseCode) &&
                Objects.equals(groupId, that.groupId) &&
                Objects.equals(sentTime, that.sentTime) &&
                Objects.equals(deliverTime, that.deliverTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobileNumber, messageId, status, responseCode, groupId, sentTime,deliverTime );
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KapSysSMSResponse{");
        sb.append("mobileNumber='").append(mobileNumber).append('\'');
        sb.append(", messageId='").append(messageId).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", responseCode='").append(responseCode).append('\'');
        sb.append(", groupId='").append(groupId).append('\'');
        sb.append(", sentTime='").append(sentTime).append('\'');
        sb.append(", deliverTime='").append(deliverTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
