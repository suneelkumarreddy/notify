package in.notwork.notify.server.response;

import java.util.Objects;

/**
 * Created by insreddy1 on 8/25/2015.
 */
public class GupshupSMSResponse {
    private String mobileNumber;
    private String status;
    private String messageId;
    private String details;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GupshupSMSResponse that = (GupshupSMSResponse) o;
        return Objects.equals(mobileNumber, that.mobileNumber) &&
                Objects.equals(status, that.status) &&
                Objects.equals(messageId, that.messageId) &&
                Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobileNumber, status, messageId, details);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GupshupSMSResponse{");
        sb.append("mobileNumber='").append(mobileNumber).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", messageId='").append(messageId).append('\'');
        sb.append(", details='").append(details).append('\'');
        sb.append('}');
        return sb.toString();
    }
}