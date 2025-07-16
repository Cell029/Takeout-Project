package com.sky.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryOrdersPageQueryDTO {
    private int page;

    private int pageSize;

    private Integer status;
}
