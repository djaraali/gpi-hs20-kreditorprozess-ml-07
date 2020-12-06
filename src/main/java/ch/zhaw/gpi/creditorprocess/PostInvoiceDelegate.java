package ch.zhaw.gpi.creditorprocess;


import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.time.*; 
import java.util.Date; 

@Named("PostInvoiceAdapter")
public class PostInvoiceDelegate implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) throws Exception{
        
        long referenceNr = (Long) execution.getVariable("referenceNr");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> orderresponse = restTemplate.exchange("http://localhost:8070/api/orders/search/findByReferenceNumber?referenceNumber={referenceNr}",HttpMethod.GET, null,String.class ,  referenceNr);

        if(orderresponse.getStatusCode().equals(HttpStatus.OK)){

            JSONObject orderAsJsonObject = new JSONObject(orderresponse.getBody());
            ResponseEntity<String> creditorresponse = restTemplate.exchange(orderAsJsonObject.getJSONObject("_links").getJSONObject("creditor").getString("href"),HttpMethod.GET, null,String.class);
            ResponseEntity<String> invoiceresponse = restTemplate.exchange(orderAsJsonObject.getJSONObject("_links").getJSONObject("invoice").getString("href"),HttpMethod.GET, null,String.class);
            JSONObject creditorAsJsonObject = new JSONObject(creditorresponse.getBody());
            JSONObject invoiceAsJsonObject = new JSONObject(invoiceresponse.getBody());
            String OrderURL = orderAsJsonObject.getJSONObject("_links").getJSONObject("self").getString("href");
            
            
            String invoiceid    = invoiceAsJsonObject.getJSONObject("_links").getJSONObject("self").getString("href");
            String creditorName =  creditorAsJsonObject.getString("crName");
            Long   invoiceNumber = Long.parseLong(invoiceid.substring(invoiceid.lastIndexOf("/")));
            Long   invoiceAmount = orderAsJsonObject.getLong("amount");
            Date olddateOfInvoice = new Date(2020-11-24);
            Date olddateDue = new Date(2020-11-24);
        

            LocalDate dateOfInvoice = LocalDate.ofInstant(olddateOfInvoice.toInstant(), ZoneId.systemDefault());
            LocalDate dateDue = LocalDate.ofInstant(olddateDue.toInstant(), ZoneId.systemDefault());
           


            System.out.println(orderresponse);
            System.out.println(creditorAsJsonObject+"testwort");
           // ResponseEntity<String> orderresponse = restTemplate.exchange("http://localhost:8070/api/orders/search/findByReferenceNumber?referenceNumber={referenceNr}",HttpMethod.GET, null,String.class ,  referenceNr);
            execution.setVariable("orderAmount", orderAsJsonObject.getLong("amount"));
            execution.setVariable("costCenterMgr", orderAsJsonObject.getString("cstCtMgr"));
            execution.setVariable("orderFound", true);
            execution.setVariable("creditorOrderCount", creditorAsJsonObject.getLong("ordersCnt"));
            execution.setVariable("creditorInvoiceReclamationCount", creditorAsJsonObject.getLong("invoicingReclamationCnt"));

        }
        else{
            


        }



    }
}

