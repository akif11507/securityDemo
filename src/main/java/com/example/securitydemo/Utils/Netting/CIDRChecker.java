package com.example.securitydemo.Utils.Netting;

import org.apache.commons.net.util.SubnetUtils;

import java.util.Arrays;

public class CIDRChecker {

    public static void main(String[] args) {

        String xfHeader = " 52.11";
        String whiteList = "54.178.112.255, 46.51.233.55, 54.248.234.247, 13.112.200.5,52.199.166.199,13.114.157.255,18.180.230.201,54.168.186.47,54.230.65.17,54.230.65.38";
        boolean status = Arrays.asList(whiteList.split(",")).contains(xfHeader);
        System.out.println("status: " + status);


//        String ipToCheck = "192.167.1.35";
//        String cidrRange = "192.168.1.0/24";
//
//        if (isIPInCIDR(ipToCheck, cidrRange)) {
//            System.out.println(ipToCheck + " is in the CIDR range " + cidrRange);
//        } else {
//            System.out.println(ipToCheck + " is not in the CIDR range " + cidrRange);
//        }
    }

    private static boolean isIPInCIDR(String ip, String cidr) {
        SubnetUtils subnetUtils = new SubnetUtils(cidr);
        return subnetUtils.getInfo().isInRange(ip);
//        try {
//        } catch (IllegalArgumentException e) {
//            // Handle invalid CIDR notation
//            return false;
//        }
    }


}
