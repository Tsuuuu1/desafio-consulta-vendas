package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerMinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj) "
            + "FROM Sale obj JOIN obj.seller s "
            + "WHERE obj.date BETWEEN :minDate AND :maxDate AND UPPER(s.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<SaleMinDTO> findSales(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);


    @Query("SELECT new com.devsuperior.dsmeta.dto.SellerMinDTO(s.name, SUM(obj.amount)) "
            + "FROM Sale obj JOIN obj.seller s "
            + "WHERE obj.date BETWEEN :minDate AND :maxDate "
            + "GROUP BY s.name")
List<SellerMinDTO> findSummary(LocalDate minDate, LocalDate maxDate);
}