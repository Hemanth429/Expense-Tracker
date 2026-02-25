package application.dto;

import java.time.LocalDate;

public class ExpenseResponse {

    private Long id;
    private double amount;
    private String category;
    private LocalDate date;
    private String description;

    public ExpenseResponse() {
    }

    public ExpenseResponse(Long id, double amount, String category,LocalDate date, String description) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }


    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }


}
