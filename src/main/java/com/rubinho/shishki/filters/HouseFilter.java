package com.rubinho.shishki.filters;

import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.HouseStatus;
import com.rubinho.shishki.model.HouseType;

public record HouseFilter(
        Glamping glamping,
        HouseType houseType,
        HouseStatus houseStatus,
        Integer cost
) {
}
