package com.kiba.framework.comm.dto.base;

import java.util.List;

public class BaseCommand {
    public String ArgIP;
    public List<String> UserRole;

    public String getArgIP() {
        return ArgIP;
    }

    public void setArgIP(String argIP) {
        ArgIP = argIP;
    }

    public List<String> getUserRole() {
        return UserRole;
    }

    public void setUserRole(List<String> userRole) {
        UserRole = userRole;
    }
}
