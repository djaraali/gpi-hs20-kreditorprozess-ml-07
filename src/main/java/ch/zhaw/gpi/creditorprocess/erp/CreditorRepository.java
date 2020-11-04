package ch.zhaw.gpi.creditorprocess.erp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditorRepository extends JpaRepository<Creditor, Long> {
    
}
