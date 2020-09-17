package Tshop;

import Tshop.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.core.Ordered;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationPageViewHandler {


    @Autowired
    private ReservationPageRepository reservationPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenReservationAccepted_then_CREATE (@Payload ReservationAccepted reservationAccepted) {
        try {
            if (reservationAccepted.isMe()) {
                // view 객체 생성
                ReservationPage reservationPage = new ReservationPage();
                // view 객체에 이벤트의 Value 를 set 함
                reservationPage.setReservationId(reservationAccepted.getReservationId());
                reservationPage.setAgencyName(reservationAccepted.getAgencyname());
                reservationPage.setBookId(reservationAccepted.getBookId());
                reservationPage.setStatus(reservationAccepted.getStatus());
                // view 레파지 토리에 save
                reservationPageRepository.save(reservationPage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenReservationCanceled_then_UPDATE (@Payload ReservationCanceled reservationCanceled) {
        try {
            if (reservationCanceled.isMe()) {
                // view 객체 조회
                List<ReservationPage> reservationList = reservationPageRepository.findByReservationId(reservationCanceled.getReservationId());
                for(ReservationPage reservationPage : reservationList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    reservationPage.setStatus(reservationCanceled.getStatus());
                    // view 레파지 토리에 save
                    reservationPageRepository.save(reservationPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}