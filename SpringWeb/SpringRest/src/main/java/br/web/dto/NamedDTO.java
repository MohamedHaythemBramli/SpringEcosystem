package br.web.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NamedDTO extends BaseDTO{
    private String name;
}
