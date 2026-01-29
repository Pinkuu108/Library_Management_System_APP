package com.lb.service;

import com.lb.entity.Reservation;
import com.lb.payload.dto.ReservationDTO;
import com.lb.payload.request.ReservationRequest;
import com.lb.payload.request.ReservationSearchRequest;
import com.lb.payload.response.PageResponse;

public interface ReservationService {

    ReservationDTO createReservation(ReservationRequest reservationRequest) throws Exception;

    ReservationDTO createReservationForUser(ReservationRequest reservationRequest,
                                            Long userId) throws Exception;

    ReservationDTO cancelReservation(Long reservationId) throws Exception;

    ReservationDTO fulfillReservation(Long reservationId) throws Exception;

    PageResponse<ReservationDTO> getMyReservations(ReservationSearchRequest searchRequest) throws Exception;

    PageResponse<ReservationDTO> searchReservations(ReservationSearchRequest searchRequest);

}
