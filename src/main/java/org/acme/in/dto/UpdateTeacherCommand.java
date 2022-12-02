package org.acme.in.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UpdateTeacherCommand extends CreateTeacherCommand {
    private UUID teacherId;
}
