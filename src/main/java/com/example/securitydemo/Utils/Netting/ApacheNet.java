package com.example.securitydemo.Utils.Netting;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.NetMask;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class ApacheNet {

    private static final String ALLOWED_DASH_BOARD_CIDR = "server.allowed_dashboard_CIDR";

    //    @Value("${" + ALLOWED_DASH_BOARD_CIDR + ":'0.0.0.0/0'}")
    private String allowedDashboardCIDR = "13.197.23.11/16,87.22.34.66/8,45.32.35.118/8";

    final String remoteIPAddress = "45.32.35.118";

    public static void main(String[] args) {

        ApacheNet apacheNet = new ApacheNet();
        log.info("status " + apacheNet.checkCIDRList(apacheNet.fillFromInput()));
    }

    public Queue<NetMask> fillFromInput() {
        final String input = allowedDashboardCIDR;
        log.info("allowed " + input);
        final Queue<NetMask> target = new ConcurrentLinkedQueue<>();
        NetMask nm;
        for (final String s : input.split("\\s*,\\s*")) {
            try {
                nm = new NetMask(s);
                target.add(nm);
            } catch (IllegalArgumentException e) {
//                messages.add(s + ": " + e.getMessage());
                System.out.println("error ");
            }
        }
        return target;
    }

    public boolean checkCIDRList(Queue<NetMask> allowedCIDRList) {
        try {
            InetAddress addr = InetAddress.getByName(remoteIPAddress);
            log.info("inet " + addr + " " + addr.getAddress()[0]);
            for (final NetMask nm : allowedCIDRList) {
                if (nm.matches(addr)) {
                    log.info("found match at " + nm);
                    return true;
                }
            }
        } catch (UnknownHostException e) {
            // This should be in the 'could never happen' category but handle it
            // to be safe.
            log.info("error", e);
        }
        return false;
    }
}
