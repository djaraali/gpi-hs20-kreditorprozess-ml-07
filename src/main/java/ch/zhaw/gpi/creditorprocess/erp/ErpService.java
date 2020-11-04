package ch.zhaw.gpi.creditorprocess.erp;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErpService {
    @Autowired
    private OrderRepository orderRepository;

    public String getOrder(Long referenceNumber) {
        Optional<Order> order = orderRepository.findByReferenceNumber(referenceNumber);

        if(order.isPresent()){
            JSONObject orderAsJson = new JSONObject(order.get());

            return orderAsJson.toString();
        } else {
            return "404";
        }
    }
}
