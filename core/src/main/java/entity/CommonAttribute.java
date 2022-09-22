package entity;

import lombok.Data;

import java.time.Instant;

@Data
public class CommonAttribute {
    private Instant createdAt = Instant.now();
    private Instant updatedAt;
}
