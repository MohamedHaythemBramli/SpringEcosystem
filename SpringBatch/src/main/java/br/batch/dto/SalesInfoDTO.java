package br.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesInfoDTO {
    private String product;
    private String seller;
    private Integer sellerId;
    private double price;
    private String city;
    private String category;
}

