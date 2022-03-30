package com.example.paymentmanager.ModelData;

import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    public Payment getPaymentByIdCDAndIdUser(Integer IdCD, Integer IdUser);
    public Iterable<Payment> getPaymentsByIdUser(Integer IdUser);
    public Iterable<Payment> getPaymentsByIdCD(Integer IdCD);
    public Payment getPaymentByIdPayment(Integer idPayment);
}
