package com.ita.if103java.ims.service;

import com.ita.if103java.ims.dto.UsefulWarehouseDto;
import com.ita.if103java.ims.dto.WarehouseDto;
import com.ita.if103java.ims.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface WarehouseService {
    WarehouseDto add(WarehouseDto warehouseDto, UserDetailsImpl user);

    WarehouseDto findById(Long id, UserDetailsImpl user);

    List<WarehouseDto> findWarehousesByTopLevelId(Long topLevelId, UserDetailsImpl user);

    WarehouseDto update(WarehouseDto warehouseDto, UserDetailsImpl user);

    boolean softDelete(Long id, UserDetailsImpl user);

    Page<WarehouseDto> findAllTopLevel(Pageable pageable, UserDetailsImpl user);

    List<WarehouseDto> findAllTopLevelList( UserDetailsImpl user);

    Map<Long, String> findAllWarehouseNames(UserDetailsImpl user);

    List<WarehouseDto> findChildrenById(Long id, UserDetailsImpl user);

    Integer findTotalCapacity (Long id, UserDetailsImpl user);

    List<UsefulWarehouseDto> findUsefulWarehouses(Long capacity, UserDetailsImpl user);
}
