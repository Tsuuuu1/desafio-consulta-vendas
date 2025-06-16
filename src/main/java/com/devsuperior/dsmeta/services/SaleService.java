package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {



	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> findSales(String minDate, String maxDate, String name, Pageable pageable){


		LocalDate today = maxDate.isEmpty() ? LocalDate.now(ZoneId.systemDefault()) : LocalDate.parse(maxDate);
		LocalDate lastYear = minDate.isEmpty() ? today.minusYears(1) : LocalDate.parse(minDate);

		return repository.findSales(lastYear, today, name, pageable);

	}

	public List<SellerMinDTO> findSummary(String minDate, String maxDate){

		LocalDate today = maxDate.isEmpty() ? LocalDate.now(ZoneId.systemDefault()) : LocalDate.parse(maxDate);
		LocalDate lastYear = minDate.isEmpty() ? today.minusYears(1) : LocalDate.parse(minDate);

		return repository.findSummary(lastYear, today);


	}



}
