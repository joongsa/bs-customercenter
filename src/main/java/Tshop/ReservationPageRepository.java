package Tshop;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationPageRepository extends CrudRepository<ReservationPage, Long> {
    List<ReservationPage> findByReservationId(Long reservationId);
}