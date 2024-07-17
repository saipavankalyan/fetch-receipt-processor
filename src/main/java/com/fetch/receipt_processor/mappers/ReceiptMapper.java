package com.fetch.receipt_processor.mappers;

import com.fetch.receipt_processor.dto.ItemDto;
import com.fetch.receipt_processor.dto.ReceiptRequestDto;
import com.fetch.receipt_processor.entity.ItemDbo;
import com.fetch.receipt_processor.entity.ReceiptDbo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReceiptMapper {

    ReceiptDbo map(ReceiptRequestDto request);

    ItemDbo mapItem(ItemDto item);

    List<ItemDbo> mapItems(List<ItemDbo> item);

}
