package com.theom.challenge.model;

import lombok.Data;
import java.io.Serializable;

/**
 * POJO for User
 */
@Data
public class User implements Serializable {
    private String uuid;
    private String name;
    private String email;
}
