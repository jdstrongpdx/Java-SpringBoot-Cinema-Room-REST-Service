package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    int rows;
    int columns;
    @JsonIgnore
    Stats stats;
    List<Seat> seats = new ArrayList<>();

    public Cinema(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        for (int i = 1; i < rows + 1; i++) {
            for (int j = 1; j < columns + 1; j++) {

                seats.add(new Seat(i, j));
            }
        }
        this.stats = new Stats(rows, columns);
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Seat getSeat(int row, int column) {
        for (Seat seat: seats) {
            if (row == seat.getRow() && column == seat.getColumn()) return seat;
        }
        return null;
    }

    public Ticket purchaseTicket(int row, int column) {
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            if (row == seat.getRow() && column == seat.getColumn()) {
                Ticket ticket = new Ticket(seat);
                seat.setTicket(ticket);
                seats.set(i, seat);
                stats.setPurchased(stats.getPurchased() + 1);
                stats.setAvailable(stats.getAvailable() - 1);
                stats.setIncome(stats.getIncome() + seat.getPrice());
                return ticket;
            }
        }
        return null;
    }

    public Ticket returnTicket(Token token) {
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            if (seat.getTicket() != null && token.getToken().equals(seat.getTicket().getToken())) {
                Ticket ticket = seat.getTicket();
                ticket.setToken(null);
                seat.setTicket(null);
                seats.set(i, seat);
                stats.setPurchased(stats.getPurchased() - 1);
                stats.setAvailable(stats.getAvailable() + 1);
                stats.setIncome(stats.getIncome() - seat.getPrice());
                return ticket;
            }
        }
        return null;
    }

    public String serializer() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);

    }

    public String getStats() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this.stats);
    }

}
