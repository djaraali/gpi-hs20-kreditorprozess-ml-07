package ch.zhaw.gpi.creditorprocess.erp;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Creditor {
    @Id
    private Long creditorId;
    private String crName;
    private Integer ordersCnt;
    private Integer invoicingReclamationCnt;

    public Long getCreditorId() {
        return creditorId;
    }

    public void setCreditorId(Long creditorId) {
        this.creditorId = creditorId;
    }

    public String getCrName() {
        return crName;
    }

    public void setcrName(String crName) {
        this.crName = crName;
    }

    public Integer getOrdersCnt() {
        return ordersCnt;
    }

    public void setOrdersCnt(Integer ordersCnt) {
        this.ordersCnt = ordersCnt;
    }

    public Integer getInvoicingReclamationCnt() {
        return invoicingReclamationCnt;
    }

    public void setInvoicingReclamationCnt(Integer invoicingReclamationCnt) {
        this.invoicingReclamationCnt = invoicingReclamationCnt;
    }
    
    
}
