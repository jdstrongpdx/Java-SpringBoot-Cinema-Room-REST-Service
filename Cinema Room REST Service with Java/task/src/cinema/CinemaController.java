package cinema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
public class CinemaController {
    Cinema cinema = new Cinema(9, 9);

    @GetMapping("/seats")
    public String getSeatList() throws JsonProcessingException {
        return cinema.serializer();
    }

    @GetMapping("/stats")
    public ResponseEntity<String> getStats(@RequestParam(value = "password", required = false) Optional<String> password) throws JsonProcessingException {
        if (password.isPresent()) {
            if (password.get().equals("super_secret")) {
                return new ResponseEntity<>(cinema.getStats(), HttpStatus.OK);
            }
        }
        HashMap<String, String> error = new HashMap<>();
        error.put("error", "The password is wrong!");
        return new ResponseEntity<>(new ObjectMapper().writeValueAsString(error), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseSeats(@RequestBody Seat inputSeat) throws JsonProcessingException {
        Seat seat = cinema.getSeat(inputSeat.getRow(), inputSeat.getColumn());
        if (seat == null) {
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "The number of a row or a column is out of bounds!");
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(error), HttpStatus.BAD_REQUEST);
        }
        if (seat.getTicket() != null) {
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "The ticket has been already purchased!");
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(error), HttpStatus.BAD_REQUEST);
        }
        Ticket ticket = cinema.purchaseTicket(inputSeat.getRow(), inputSeat.getColumn());
        return new ResponseEntity<>(new ObjectMapper().writeValueAsString(ticket), HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnSeats(@RequestBody Token inputToken) throws JsonProcessingException {
        Ticket ticket = cinema.returnTicket(inputToken);
        if (ticket == null) {
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "Wrong token!");
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(error), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ObjectMapper().writeValueAsString(ticket), HttpStatus.OK);
    }

}
