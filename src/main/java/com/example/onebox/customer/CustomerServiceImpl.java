package com.example.onebox.customer;

import com.example.onebox.audit.AuditService;
import com.example.onebox.common.exception.EntityNotFoundException;
import com.example.onebox.common.exception.InvalidDataException;
import com.example.onebox.config.mapper.CustomerEntityMapper;
import com.example.onebox.customer.model.CustomerDto;
import com.example.onebox.customer.model.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private AuditService auditService;

    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    private final Map<Long, CustomerEntity> customers = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(1);

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException if no customers are found
     */
    @Override
    public List<CustomerEntity> getAllCustomers() {
        if(customers.isEmpty())
            throw new EntityNotFoundException("No customers found");

        return customers.values().stream()
                .filter(customer -> !customer.getAudit().getAudDeleted())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException if the customer is not found
     */
    @Override
    public CustomerEntity getCustomer(Long id) {
        if (!customers.containsKey(id) || customers.get(id).getAudit().getAudDeleted())
            throw new EntityNotFoundException("Customer with id " + id + " not found");

        return customers.get(id);
    }

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException if the customer is not found
     */
    @Override
    public void delete(Long id) {
        if (!customers.containsKey(id) || customers.get(id).getAudit().getAudDeleted())
            throw new EntityNotFoundException("Customer with id " + id + " not found");

        customers.get(id).setAudit(auditService.deleteAudit(customers.get(id).getAudit()));
    }

    /**
     * {@inheritDoc}
     * @throws InvalidDataException if the customer data is invalid
     */
    @Override
    public CustomerEntity save(CustomerDto dto) {

        isValidCustomer(dto);

        CustomerEntity customerEntity;

        if (dto.getId() != null) {
            customerEntity = getCustomer(dto.getId());
            customerEntity.setAudit(auditService.editAudit(customerEntity.getAudit()));
        } else {
            customerEntity = new CustomerEntity();
            customerEntity.setId(generateCustomerId());
            customerEntity.setAudit(auditService.newAudit());
        }

        customers.put(customerEntity.getId(), customerEntity);

        return customerEntityMapper.partialUpdate(dto, customerEntity);

    }

    private void isValidCustomer(CustomerDto dto) {

        if(isNullOrEmpty(dto.getFirstname()))
            throw new InvalidDataException("Customer name is required");

    }

    private static long generateCustomerId() {
        return idCounter.getAndIncrement();
    }

    private Boolean isNullOrEmpty(String item) {

        return item == null || item.equals("");
    }

}
