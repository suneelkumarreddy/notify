package in.notwork.notify.server.response;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by insreddy1 on 8/30/2015.
 */
public class Response{
    private String status;
    private String message;
    private List<SMS> data = new ArrayList<>();
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SMS> getData() {
        return data;
    }

    public void setData(List<SMS> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Response{");
        sb.append("status='").append(status).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", data='").append(data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
