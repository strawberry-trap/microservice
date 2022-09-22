package message.domain;

import entity.CommonAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_MESSAGE")
public class Message extends CommonAttribute {

    @Id
    private String id;
    private Boolean published;
    private LocalDateTime publishedAt;
    private String eventType;
    private String description;

    public Message(String eventType, String description) {
        this.published = false;
        this.publishedAt = LocalDateTime.now();
        this.eventType = eventType;
        this.description = description;
    }
}