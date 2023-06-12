package com.galwap.authorisation.impl;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class RoleAssigner {

    public String getRole(String username) {
        if (username.startsWith("P")) {
            return "PREZES";
        } else if (username.startsWith("M")) {
            return "MANAGER";
        } else if (username.startsWith("p")) {
            return "PERSONEL";
        } else {
            return "KLIENT";
        }
    }
}
