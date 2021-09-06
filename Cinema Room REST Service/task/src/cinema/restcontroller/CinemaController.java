package cinema.restcontroller;

import cinema.Cinema;
import cinema.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
public class CinemaController {

    Cinema cinema = new Cinema();

    @GetMapping("/seats")
    public Map<String, Object> getSeats() {
        return Map.of(
                "total_rows", cinema.getRows(),
                "total_columns", cinema.getRowSeats(),
                "available_seats", cinema.getAvailableSeats()
        );
    }

    @PostMapping("/purchase")
    public ResponseEntity bookSeats(@RequestBody Seat desiredSeat) {
        Seat seat;

        try {
            seat = cinema.getSeat(desiredSeat.getRow(), desiredSeat.getColumn());
        } catch (ArrayIndexOutOfBoundsException e) {
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }

        UUID token = null;
        if (seat.isAvailable()) {
            token = UUID.randomUUID();
            cinema.buyTicket(seat, token);
        } else {
            return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(Map.of("token", token, "ticket", seat), HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity returnTicket(@RequestBody Map<String, String> token) {
        Seat seat = cinema.returnTicket(UUID.fromString(token.get("token")));
        if (seat == null) {
            return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(Map.of("returned_ticket", seat), HttpStatus.OK);

    }

    @PostMapping("/stats")
    public ResponseEntity getStats(@RequestParam(value = "password", required = false) String password) {
        if (password == null) {
            return new ResponseEntity(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(cinema.getCinemaStats(), HttpStatus.OK);
    }

}
