package entity.order;

import entity.CommonAttribute;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_ORDER")
public class Order extends CommonAttribute {

    @Id
    private String id;
    private String orderBy;
    private Integer price;
}
