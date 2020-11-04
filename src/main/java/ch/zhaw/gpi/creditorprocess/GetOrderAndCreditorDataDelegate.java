package ch.zhaw.gpi.creditorprocess;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import ch.zhaw.gpi.creditorprocess.erp.ErpService;

@Named("getOrderAndCreditorDataAdapter")
public class GetOrderAndCreditorDataDelegate implements JavaDelegate {

    @Autowired
    private ErpService erpService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long referenceNr = (Long) execution.getVariable("referenceNr");

        String orderAsJsonString = erpService.getOrder(referenceNr);

        Boolean orderFound = !orderAsJsonString.equals("404");

        execution.setVariable("orderFound", orderFound);

        if(orderFound){
            JSONObject orderAsJsonObject = new JSONObject(orderAsJsonString);
            execution.setVariable("orderNr", orderAsJsonObject.getLong("orderId"));
            execution.setVariable("orderAmount", orderAsJsonObject.getLong("amount"));
            execution.setVariable("costCenterMgr", orderAsJsonObject.getString("cstCtMgr"));
            JSONObject creditorAsJsonObject = orderAsJsonObject.getJSONObject("creditor");
            execution.setVariable("creditorOrderCount", creditorAsJsonObject.getInt("ordersCnt"));
            execution.setVariable("creditorInvoiceReclamationCount", creditorAsJsonObject.getInt("invoicingReclamationCnt"));
        }
    }
    
}
