package com.xxx.service;

import com.xxx.pojo.Admin;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    Admin login(String username, String password);
}
