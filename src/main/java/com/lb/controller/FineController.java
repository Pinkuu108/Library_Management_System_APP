package com.lb.controller;

import com.lb.domain.FineStatus;
import com.lb.domain.FineType;
import com.lb.payload.dto.FineDTO;
import com.lb.payload.request.CreateFineRequest;
import com.lb.payload.request.WaiveFineRequest;
import com.lb.payload.response.PageResponse;
import com.lb.payload.response.PaymentInitiateResponse;
import com.lb.service.FineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fines")
public class FineController {
    private final FineService fineService;

    @PostMapping
    public ResponseEntity<?> createFine(
            @Valid @RequestBody CreateFineRequest fineRequest
            )  throws  Exception{
        FineDTO fineDTO = fineService.createFine(fineRequest);
        return ResponseEntity.ok().body(fineDTO);
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<?> payFine(
            @PathVariable Long id,
            @RequestParam(required = false) String transactionId
    )  throws  Exception{
        PaymentInitiateResponse res=fineService.payFine(id,transactionId);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/waive")
    public ResponseEntity<?> waiveFine(
            @Valid @RequestBody WaiveFineRequest waiveFineRequest
    ) throws Exception {

        FineDTO fineDTO = fineService.waiveFine(waiveFineRequest);
        return ResponseEntity.ok(fineDTO);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyFines(
            @RequestParam(required = false) FineStatus status,
            @RequestParam(required = false) FineType type
    ) throws Exception {

        List<FineDTO> fines = fineService.getMyFines(status, type);
        return ResponseEntity.ok(fines);
    }

    @GetMapping
    public ResponseEntity<?> getAllFines(
            @RequestParam(required = false) FineStatus status,
            @RequestParam(required = false) FineType type,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        PageResponse<FineDTO> fines = fineService
                .getAllFines(status, type, userId, page, size);

        return ResponseEntity.ok(fines);
    }

}
