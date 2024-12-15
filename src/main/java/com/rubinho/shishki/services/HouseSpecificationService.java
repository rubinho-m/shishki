package com.rubinho.shishki.services;

import com.rubinho.shishki.filters.HouseFilter;
import com.rubinho.shishki.model.House;
import org.springframework.data.jpa.domain.Specification;

public interface HouseSpecificationService {
    Specification<House> filterBy(HouseFilter houseFilter);
}
