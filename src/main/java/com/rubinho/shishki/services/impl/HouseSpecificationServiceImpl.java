package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.filters.HouseFilter;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.House;
import com.rubinho.shishki.model.HouseStatus;
import com.rubinho.shishki.model.HouseType;
import com.rubinho.shishki.services.HouseSpecificationService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class HouseSpecificationServiceImpl implements HouseSpecificationService {
    private static final String COST = "cost";
    private static final String HOUSE_TYPE = "houseType";
    private static final String HOUSE_STATUS = "status";
    private static final String GLAMPING = "glamping";

    @Override
    public Specification<House> filterBy(HouseFilter houseFilter) {
        return Specification
                .where(hasCost(houseFilter.cost()))
                .and(hasGlamping(houseFilter.glamping()))
                .and(hasHouseType(houseFilter.houseType()))
                .and(hasHouseStatus(houseFilter.houseStatus()));
    }

    private Specification<House> hasCost(Integer cost) {
        return ((root, query, cb) -> cost == null ? cb.conjunction() : cb.equal(root.get(COST), cost));
    }

    private Specification<House> hasHouseType(HouseType houseType) {
        return ((root, query, cb) -> houseType == null ? cb.conjunction() : cb.equal(root.get(HOUSE_TYPE), houseType));
    }

    private Specification<House> hasHouseStatus(HouseStatus houseStatus) {
        return ((root, query, cb) -> houseStatus == null ? cb.conjunction() : cb.equal(root.get(HOUSE_STATUS), houseStatus));
    }

    private Specification<House> hasGlamping(Glamping glamping) {
        return ((root, query, cb) -> glamping == null ? cb.conjunction() : cb.equal(root.get(GLAMPING), glamping));
    }
}
