package gov.iti.jets.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Chat implements BaseEntity{
    private int id;
    private String name;
    private String picture_icon;
    private Date created_on;
    private Date modified_on;
}
