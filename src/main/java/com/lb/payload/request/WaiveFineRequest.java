package com.lb.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaiveFineRequest {


    @NotNull(message = "Fine Id is mandatory")
    private Long fineId;

    @NotNull(message = "Waiver reason is mandatory")
    private String reason;
}
