package cinema;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {
    private static final int VIP_SEATS = 4;

    int row;
    int column;
    int price;
    boolean reserved;
    String token;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = row <= VIP_SEATS ? 10 : 8;
        this.reserved = false;
    }

    public Seat() {}

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @JsonIgnore
    public boolean isAvailable() {
        return !reserved;
    }

    public void setReserved() {
        this.reserved = true;
    }

    public int getPrice() {
        return price;
    }

    @JsonIgnore
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
