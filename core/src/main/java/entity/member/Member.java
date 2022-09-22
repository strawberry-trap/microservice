package entity.member;

import entity.CommonAttribute;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_MEMBER")
public class Member extends CommonAttribute {

    @Id
    private String id;
    private String name;
    private String phoneNumber;
}


