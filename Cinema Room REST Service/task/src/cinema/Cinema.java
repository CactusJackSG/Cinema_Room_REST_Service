package cinema;


import java.util.*;
import java.util.stream.Collectors;

public class Cinema {

    private static final int ROWS = 9;
    private static final int ROW_SEATS = 9;
    private final Seat[][] seats;
    private Map<UUID, Seat> purchasedTicket = new HashMap<>();

    public Cinema() {
        this.seats = new Seat[ROWS][ROW_SEATS];
        for (int row = 0; row < ROWS; row++) {
            for (int row_seat = 0; row_seat < ROW_SEATS; row_seat++) {
                seats[row][row_seat] = new Seat(row + 1,
                        row_seat + 1);
            }
        }
    }

    public void buyTicket(Seat seat, UUID token) {
        seat.setReserved();
        purchasedTicket.put(token, seat);
    }

    public Seat returnTicket(UUID token) {
        Seat returnSeat = purchasedTicket.get(token);
        if (returnSeat != null) {
            purchasedTicket.remove(token);
        }
        return returnSeat;
    }

    public CinemaStats getCinemaStats() {
        int income = 0;
        for (Seat seat : purchasedTicket.values()) {
            income += seat.getPrice();
        }
        return new CinemaStats(income, ROW_SEATS * ROWS - purchasedTicket.size(), purchasedTicket.size());
    }

    public Seat getSeat(int row, int row_seat) {
        return seats[row - 1][row_seat - 1];
    }

    public int getRows() {
        return ROWS;
    }

    public int getRowSeats() {
        return ROW_SEATS;
    }

    public List<Seat> getAvailableSeats() {
        return Arrays.stream(seats).flatMap(Arrays::stream).filter(Seat::isAvailable).collect(Collectors.toList());
    }
}
