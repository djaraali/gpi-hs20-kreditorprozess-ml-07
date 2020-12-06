package ch.zhaw.gpi.creditorprocess;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@Named("getOrderAndCreditorDataAdapter")
public class GetOrderAndCreditorDataDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        Long referenceNr = (Long) execution.getVariable("referenceNr");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> orderresponse = restTemplate.exchange("http://localhost:8070/api/orders/search/findByReferenceNumber?referenceNumber={referenceNr}",HttpMethod.GET, null,String.class ,  referenceNr);

        if (orderresponse.getStatusCode().equals(HttpStatus.OK)){
            
            JSONObject orderAsJsonObject = new JSONObject(orderresponse.getBody());
            ResponseEntity<String> creditorresponse = restTemplate.exchange(orderAsJsonObject.getJSONObject("_links").getJSONObject("creditor").getString("href"),HttpMethod.GET, null,String.class);
            JSONObject creditorAsJsonObject = new JSONObject(creditorresponse.getBody());
            System.out.println(orderresponse);
            System.out.println(creditorAsJsonObject+"testwort");
            execution.setVariable("orderNr", orderAsJsonObject.getLong("orderId"));
            execution.setVariable("orderAmount", orderAsJsonObject.getLong("amount"));
            execution.setVariable("costCenterMgr", orderAsJsonObject.getString("cstCtMgr"));
            execution.setVariable("creditorOrderCount", creditorAsJsonObject.getInt("ordersCnt"));
            execution.setVariable("creditorInvoiceReclamationCount", creditorAsJsonObject.getInt("invoicingReclamationCnt"));
        }
        else{
            execution.setVariable("orderFound", false);
        }
    
    }
}



