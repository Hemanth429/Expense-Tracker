package application.controller;

import application.dto.ExpenseRequest;
import application.dto.ExpenseResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import application.service.ExpenseService;
import java.net.URI;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<ExpenseResponse>create(@Valid @RequestBody ExpenseRequest request){

        ExpenseResponse created= service.createExpense(request);
        return ResponseEntity.created(URI.create("/api/expenses/"+created.getId())).body(created);
    }
    @GetMapping
    public List<ExpenseResponse> findAll(){
        return service.listAllExpenses();
    }
    @GetMapping("/{id}")
    public ExpenseResponse findById(@PathVariable("id") Long id){
        return service.findById(id);

    }
    @PutMapping("/{id}")
    public ExpenseResponse update(@PathVariable ("id") Long id, @Valid @RequestBody ExpenseRequest request){
        return service.updateExpense(id, request);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ("id") Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/by-category")
    public List<ExpenseResponse> byCategory(@RequestParam ("category") String category) {
        return service.listAllExpensesByCategory(category);
    }

    @GetMapping("/search/by-date-range")
    public List<ExpenseResponse> byDateRange(
            @RequestParam ("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam ("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return service.listAllExpensesByDate(start, end);
    }

    @GetMapping("/summary/month")
    public Long monthTotal(@RequestParam ("yearMonth") String yearMonth) {
        return service.monthTotal(YearMonth.parse(yearMonth));
    }



}
