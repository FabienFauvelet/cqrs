package org.acme.in.dto.teacher;

import lombok.Data;

@Data
public class CreateTeacherCommand {
    private String firstName;
    private String lastName;
}
