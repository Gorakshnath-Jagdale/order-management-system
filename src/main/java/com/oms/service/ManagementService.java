package com.oms.service;

import com.oms.dto.Requester;
import com.oms.dto.requests.Customer;
import com.oms.dto.requests.Manufacturer;
import com.oms.dto.requests.Product;
import com.oms.mapper.response.CustomerMapper;
import com.oms.mapper.response.ProductMapper;
import com.oms.models.repository.CustomerDetailsRepository;
import com.oms.models.repository.POMasterRepository;
import com.oms.models.repository.ProductDetailsRepository;
import com.oms.models.repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ManagementService {
    private final CustomerDetailsRepository customerDetailsRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final CustomerMapper customerMapper;
    private final ProductMapper productMapper;
    private final POMasterRepository poMasterRepository;
    private final UserDetailsRepository userDetailsRepository;

    public Customer createCustomer(Customer customer,int requesterUserId) throws Exception {
      if(customer!=null)
      {
          var customerToSave= customerMapper.customerToCustomerDetailsEntity(customer,Long.parseLong(String.valueOf(requesterUserId)));
          customerToSave.setCreatedBy(requesterUserId);
          customerToSave.setCreatedDate(new Date());
          var savedCustomer= customerDetailsRepository.save(customerToSave);
          return customerMapper.customerDetailsEntityToCustomer(savedCustomer);
      }else
      {
          throw new Exception("Invalid Input");
      }
    }
    public Customer updateCustomer(Customer customer,int requesterUserId) throws Exception {
if(customerDetailsRepository.existsById(customer.getId()))
{
    var customerToUpdate=customerDetailsRepository.getById(customer.getId());
    customerMapper.updateCustomerToCustomerDetailsEntity(customerToUpdate,customer,Long.parseLong(String.valueOf(requesterUserId)));
    customerToUpdate.setModifiedBy(requesterUserId);
    customerToUpdate.setModifiedDate(new Date());
    var updatedCustomer=customerDetailsRepository.save(customerToUpdate);
    return customerMapper.customerDetailsEntityToCustomer(updatedCustomer);
}else
{
    throw new Exception("Customer not found to update");
}
    }

    public Customer getCustomer(Long customerId) throws Exception {
        var customer=customerDetailsRepository.findById(customerId);
        if(customer.isPresent()) {
            return customerMapper.customerDetailsEntityToCustomer(customer.get());
        }else
        {
            throw new Exception("customer Not found");
        }
    }
    public List<Customer> getCustomers()
    {
      return customerMapper.customerDetailsEntityListToCustomerList(customerDetailsRepository.findAll());
    }

    public Product createProduct(Product request, int requesterUserId) throws Exception {
        if(request!=null)
        {
            var productToSave= productMapper.productToProductDetailsEntity(request,requesterUserId);
            var savedCustomer= productDetailsRepository.save(productToSave);
            return productMapper.ProductDetailsEntityToProduct(productToSave);
        }else
        {
            throw new Exception("Invalid Input");
        }
    }

    public Product updateProduct(Product request, int requesterUserId) throws Exception {
        if(productDetailsRepository.existsById(request.getId()))
        {
            var productToUpdate=productDetailsRepository.getById(request.getId());
            productMapper.updateProductToProductDetailsEntity(productToUpdate,request,requesterUserId);

            var updatedCustomer=productDetailsRepository.save(productToUpdate);
            return productMapper.ProductDetailsEntityToProduct(updatedCustomer);
        }else
        {
            throw new Exception("Product not found to update");
        }
    }

    public Product getProduct(Long productId) throws Exception {
        var product=productDetailsRepository.findById(productId);
        if(product.isPresent()) {
            return productMapper.ProductDetailsEntityToProduct(product.get());
        }else
        {
            throw new Exception("Product Not found");
        }
    }

    public List<Product> getProducts() {
        return productMapper.productDetailsEntityListToProductList(productDetailsRepository.findAll());

    }

    public List<Product> getProductsByManufacturer(String manufacturer) {
        return productMapper.productDetailsEntityListToProductList(productDetailsRepository.findByManufacturerIgnoreCase(manufacturer.trim()));

    }

    public Set<Manufacturer> getManufacturers() {
        return productDetailsRepository.findAllManufacturer();
    }

public String getFirstNameAndLastName(long userId)
{
    var user=userDetailsRepository.getById(userId);
    return user.getUserFirstName()+" "+user.getUserLastName();
}
}
