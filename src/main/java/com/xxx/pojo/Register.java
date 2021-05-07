package com.xxx.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Register {
    private int id;

    private String items;
    private int number;
    private String ems_number;
    private Date create_time;
    private Date update_time;
    private int uid;

    public Register(String items, int number, String ems_number, int uid) {
        this.items = items;
        this.number = number;
        this.ems_number = ems_number;
        this.uid = uid;
    }
}
