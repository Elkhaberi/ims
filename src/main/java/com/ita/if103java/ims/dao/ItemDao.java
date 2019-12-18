package com.ita.if103java.ims.dao;

import com.ita.if103java.ims.entity.Item;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemDao {
    List<Item> getItems(Pageable pageable);

    Item findItemByName(String name);

    Item findItemByAccountId(Long id);

    Item findItemById(Long id);

    Item addItem(Item item);

    boolean softDeleteItem(Long id);
}
