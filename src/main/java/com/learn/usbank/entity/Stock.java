package com.learn.usbank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("stocks")
public class Stock {

    private Long stockId;
    @PrimaryKey
    private String symbol;
    private String companyName;
    private double price;
    private String sector;
    private String exchange;
    private String currency;
    private double openPrice;
    private double closePrice;
    private boolean active;
    private String lastUpdatedDate;
}
