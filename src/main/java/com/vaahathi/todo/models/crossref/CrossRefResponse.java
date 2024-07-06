package com.vaahathi.todo.models.crossref;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrossRefResponse {
    private String gearValue;
    private String gearLabel;
}
