package com.fmjava.service;
import com.fmjava.core.pojo.address.Address;

import java.util.List;

public interface AddressService {
    public List<Address> findListByLoginUser(String userName);
}
