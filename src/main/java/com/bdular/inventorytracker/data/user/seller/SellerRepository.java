package com.bdular.inventorytracker.data.user.seller;

import com.bdular.inventorytracker.data.user.seller.data.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;

@Repository
public interface SellerRepository extends MongoRepository<Seller, String> {

    Seller findSellerByEmailEquals(@Email String email);

}
