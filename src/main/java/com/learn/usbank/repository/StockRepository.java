package com.learn.usbank.repository;

import com.learn.usbank.entity.Stock;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.math.BigDecimal;

public interface StockRepository extends CassandraRepository<Stock, String> {
}
