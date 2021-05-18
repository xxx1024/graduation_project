package com.xxx.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Register {
    private int id;
    private String name;
    private String phone;
    private String items;
    private int number;
    private String ems_number;
    private Date create_time;
    private Date update_time;
    private int uid;
}
