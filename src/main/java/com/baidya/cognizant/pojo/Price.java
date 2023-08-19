package com.baidya.cognizant.pojo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Price {
    private long id;
    private String event;
    private float price;
    private float tax;
}
