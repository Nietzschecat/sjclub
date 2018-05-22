package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.service.LicenseService;

public class LicenseServiceImpl implements LicenseService {
    @Override
    public byte[] getLicense(String sn, String siteUrl) {
        System.out.println("远程》》》》"+sn+siteUrl);
        return new byte[0];
    }
}
