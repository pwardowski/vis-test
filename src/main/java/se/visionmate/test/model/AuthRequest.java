package se.visionmate.test.model;

import lombok.Value;

@Value
public class AuthRequest {
    String username;
    String password;
}