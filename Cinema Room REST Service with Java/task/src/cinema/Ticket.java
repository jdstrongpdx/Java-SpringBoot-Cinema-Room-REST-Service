package cinema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Ticket {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    UUID token;
    Seat ticket;

    public Ticket(){};

    public Ticket(Seat seat) {
        this.ticket = seat;
        this.token = UUID.randomUUID();
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Seat getTicket() {
        return ticket;
    }

    public void setTicket(Seat ticket) {
        this.ticket = ticket;
    }
}

