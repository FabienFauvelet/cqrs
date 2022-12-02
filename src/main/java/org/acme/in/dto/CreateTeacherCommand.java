package org.acme.in.dto;

import lombok.Data;

@Data
public class CreateTeacherCommand {
    private String firstName;
    private String lastName;
}
