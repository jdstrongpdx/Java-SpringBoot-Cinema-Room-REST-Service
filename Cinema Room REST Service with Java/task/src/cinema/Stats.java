package cinema;

public class Stats {
    int income;
    int available;
    int purchased;

    public Stats(){}

    public Stats(int rows, int columns) {
        this.income = 0;
        this.available = rows * columns;
        this.purchased = 0;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }
}
