package in.notwork.notify.server.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import in.notwork.notify.client.util.NotifyConstants;
import in.notwork.notify.server.response.KapSysSMSResponse;
import in.notwork.notify.server.response.Response;
import in.notwork.notify.server.response.SMS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by insreddy1 on 8/27/2015.
 */
public class KapSysUtil {
    private static final Log log = LogFactory.getLog(KapSysUtil.class);

    public static List<KapSysSMSResponse> sendKapSysSMS(Map<String,String> config, String to, String content) throws NoSuchAlgorithmException, IOException, KeyManagementException, ParseException {
        log.info("Entering KapSysUtil sendKapSysSMS method");
        Gson gson = new Gson();
        KapsysSMS kapsysSMS = new KapsysSMS();
        //kapsysSMS.setparams("trans.kapsystem.com","Ad2c65b43d6633133355364f7e46cb8f2","KAPMSG");
        kapsysSMS.setparams(config.get(NotifyConstants.KAPSYS_URL),config.get(NotifyConstants.KAPSYS_WORKING_KEY),config.get(NotifyConstants.KAPSYS_SENDER_ID));
        KapSysSMSResponse kapSysSMSResponse=null;
        List<KapSysSMSResponse> kapSysSMSResponseList = new ArrayList<>();
            String responseJson = kapsysSMS.send_sms(to, content, NotifyConstants.KAPSYS_DLR_URL);
            log.info("KapSysUtil single message response:"+responseJson);
            JSONObject jsonObj = (JSONObject) new JSONParser().parse(responseJson);
            String status = (String)jsonObj.get(NotifyConstants.KAPSYS_STATUS);
            String message = (String)jsonObj.get(NotifyConstants.KAPSYS_RESPONSE_MESSAGE);

            if (null!=status && NotifyConstants.KAPSYS_STATUS_OK.equalsIgnoreCase(status)) {
                JSONObject dataObj = (JSONObject)jsonObj.get(NotifyConstants.KAPSYS_DATA);
                dataObj.remove(NotifyConstants.KAPSYS_GROUP_ID);

                Map<String,SMS> smsMap = gson.fromJson(dataObj.toJSONString(), new TypeToken<Map<String, SMS>>() {}.getType());

                for(Map.Entry<String,SMS> smsEntry : smsMap.entrySet()){
                    kapSysSMSResponse = new KapSysSMSResponse();
                    SMS smsObj = smsEntry.getValue();
                    kapSysSMSResponse.setMessageId(smsObj.getId());
                    kapSysSMSResponse.setMobileNumber(smsObj.getMobile());
                    kapSysSMSResponse.setStatus(smsObj.getStatus());
                    kapSysSMSResponse.setGroupId(smsObj.getId().substring(0, smsObj.getId().indexOf("-")));
                    kapSysSMSResponse.setResponseDetails(message);
                    kapSysSMSResponse.setRequestContent(content);
                    kapSysSMSResponse.setResponseCode(NotifyConstants.KAPSYS_SUCC_RESP_CODE);
                    kapSysSMSResponseList.add(kapSysSMSResponse);

                }
            } else if(null!=status && status.startsWith(NotifyConstants.KAPSYS_ERROR_CODE)){
                log.info("KapSysUtil single error occured status:"+status);
                kapSysSMSResponse = new KapSysSMSResponse();
                kapSysSMSResponse.setMobileNumber(to);
                kapSysSMSResponse.setStatus(NotifyConstants.KAPSYS_ERROR_STATUS);
                kapSysSMSResponse.setResponseCode(status);
                kapSysSMSResponse.setRequestContent(content);
                kapSysSMSResponse.setResponseDetails(message);
                kapSysSMSResponseList.add(kapSysSMSResponse);
            }
        log.info("Exiting KapSysUtil sendKapSysSMS method");
        return kapSysSMSResponseList;
    }
    public static KapSysSMSResponse messageDeliveryStatus(Map<String,String> config,String messageId) throws Exception {
        log.info("Entering KapSysUtil messageDeliveryStatus method");
        KapsysSMS kapsysSMS = new KapsysSMS();
        kapsysSMS.setparams(config.get(NotifyConstants.KAPSYS_URL),config.get(NotifyConstants.KAPSYS_WORKING_KEY),config.get(NotifyConstants.KAPSYS_SENDER_ID));
        KapSysSMSResponse kapSysSMSResponse=null;

        String responseJson = kapsysSMS.messagedelivery_status(messageId);
        log.info("KapSysUtil single message delivery status response:"+responseJson);
        JSONObject jsonObj = (JSONObject) new JSONParser().parse(responseJson);
        String status = (String)jsonObj.get(NotifyConstants.KAPSYS_STATUS);
        String message = (String)jsonObj.get(NotifyConstants.KAPSYS_RESPONSE_MESSAGE);

        if (null!=status && NotifyConstants.KAPSYS_STATUS_OK.equalsIgnoreCase(status)) {
            Gson gson = new GsonBuilder().setDateFormat(NotifyConstants.KAPSYS_DELVRY_DATE_FORMAT).create();
            Response response = gson.fromJson(responseJson,Response.class);
            kapSysSMSResponse = new KapSysSMSResponse();
            List<SMS> smsStatusList = response.getData();
            for(SMS sms : smsStatusList){
                kapSysSMSResponse.setMessageId(sms.getId());
                kapSysSMSResponse.setMobileNumber(sms.getMobile());
                kapSysSMSResponse.setStatus(sms.getStatus());
                kapSysSMSResponse.setSentTime(sms.getSenttime());
                kapSysSMSResponse.setDeliverTime(sms.getDlrtime());
            }
        }else if(null!=status && status.startsWith(NotifyConstants.KAPSYS_ERROR_CODE)){
            log.info("KapSysUtil single message delivery status error code:"+status);
            kapSysSMSResponse = new KapSysSMSResponse();
            kapSysSMSResponse.setStatus(NotifyConstants.KAPSYS_ERROR_STATUS);
            kapSysSMSResponse.setResponseCode(status);
            kapSysSMSResponse.setResponseDetails(message);
        }
        log.info("Exiting KapSysUtil messageDeliveryStatus method");
        return kapSysSMSResponse;
    }

    public static List<KapSysSMSResponse> bulkMsgDeliveryStatus(Map<String,String> config,String groupId) throws Exception {
        log.info("Entering KapSysUtil bulkMsgDeliveryStatus method");
        List<KapSysSMSResponse> kapSysSMSResponseList = new ArrayList<KapSysSMSResponse>();
        KapsysSMS kapsysSMS = new KapsysSMS();
        kapsysSMS.setparams(config.get(NotifyConstants.KAPSYS_URL),config.get(NotifyConstants.KAPSYS_WORKING_KEY),config.get(NotifyConstants.KAPSYS_SENDER_ID));
        KapSysSMSResponse kapSysSMSResponse=null;
        String responseJson = kapsysSMS.groupdelivery_status(groupId);
        log.info("KapSysUtil bulk message delivery status response:"+responseJson);
        JSONObject jsonObj = (JSONObject) new JSONParser().parse(responseJson);
        String status = (String)jsonObj.get(NotifyConstants.KAPSYS_STATUS);
        String message = (String)jsonObj.get(NotifyConstants.KAPSYS_RESPONSE_MESSAGE);

        if (null!=status && NotifyConstants.KAPSYS_STATUS_OK.equalsIgnoreCase(status)) {
            Gson gson = new GsonBuilder().setDateFormat(NotifyConstants.KAPSYS_DELVRY_DATE_FORMAT).create();
            Response response = gson.fromJson(responseJson,Response.class);
            //System.out.println(response);
            List<SMS> smsStatusList = response.getData();
            for(SMS sms : smsStatusList){
                kapSysSMSResponse = new KapSysSMSResponse();
                kapSysSMSResponse.setMessageId(sms.getId());
                kapSysSMSResponse.setMobileNumber(sms.getMobile());
                kapSysSMSResponse.setStatus(sms.getStatus());
                kapSysSMSResponse.setSentTime(sms.getSenttime());
                kapSysSMSResponse.setDeliverTime(sms.getDlrtime());
                kapSysSMSResponseList.add(kapSysSMSResponse);
            }
        }else if(null!=status && status.startsWith(NotifyConstants.KAPSYS_ERROR_CODE)){
            log.info("KapSysUtil bulk message delivery status error code:"+status);
            kapSysSMSResponse = new KapSysSMSResponse();
            kapSysSMSResponse.setStatus(NotifyConstants.KAPSYS_ERROR_STATUS);
            kapSysSMSResponse.setResponseCode(status);
            kapSysSMSResponse.setResponseDetails(message);
            kapSysSMSResponseList.add(kapSysSMSResponse);
        }
        log.info("Exiting KapSysUtil bulkMsgDeliveryStatus method");
        return kapSysSMSResponseList;
    }

    public static void main(String args[]) throws Exception{
        /*Config config = new Config("trans.kapsystem.com","Ad2c65b43d6633133355364f7e46cb8f2","KAPMSG");
        KapSysUtil.messageDeliveryStatus(config, "1544782324-1");
        List<KapSysSMSResponse> kapSysSMSResponseList = KapSysUtil.bulkMsgDeliveryStatus(config, "1544922674");
        System.out.println(kapSysSMSResponseList);*/
    }
}


