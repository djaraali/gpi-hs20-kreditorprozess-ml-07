package ch.zhaw.gpi.creditorprocess.erp;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
    public Optional<Order> findByReferenceNumber(Long referenceNumber);
}
