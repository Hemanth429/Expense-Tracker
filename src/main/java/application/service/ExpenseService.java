package application.service;

import application.dto.ExpenseRequest;
import application.dto.ExpenseResponse;
import application.entity.Expense;
import org.springframework.stereotype.Service;
import application.repo.ExpenseRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;


@Service
public class ExpenseService {

    private final ExpenseRepository repo;

    public ExpenseService(ExpenseRepository repo) {
        this.repo = repo;
    }

    public ExpenseResponse createExpense(ExpenseRequest req) {
        Expense e = new Expense();
        e.setAmount(req.getAmount());
        e.setCategory(req.getCategory());
        e.setDate(req.getDate());
        e.setDescription(req.getDescription());

        Expense saved = repo.save(e);
        return toResponse(saved);
    }

    public List<ExpenseResponse> listAllExpenses() {
        List<Expense> list = repo.findAll();
        List<ExpenseResponse> out = new ArrayList<>();
        for (Expense e : list) {
            out.add(toResponse(e));
        }
        return out;
    }

    public ExpenseResponse findById(Long id) {
        Expense e = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Expense not Found: " + id));
        return toResponse(e);
    }

    public ExpenseResponse updateExpense(Long id, ExpenseRequest req) {
        Expense e = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Expense not Found: " + id));

        e.setAmount(req.getAmount());
        e.setCategory(req.getCategory());
        e.setDate(req.getDate());
        e.setDescription(req.getDescription());

        Expense saved = repo.save(e);
        return toResponse(saved);
    }

    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Expense not Found: " + id);
        }
        repo.deleteById(id);
    }

    public List<ExpenseResponse> listAllExpensesByCategory(String category) {
        List<Expense> list = repo.findByCategoryIgnoreCaseOrderByDateDesc(category);
        List<ExpenseResponse> out = new ArrayList<>();
        for (Expense e : list) {
            out.add(toResponse(e));
        }
        return out;
    }

    public List<ExpenseResponse> listAllExpensesByDate(LocalDate start, LocalDate end) {
        List<Expense> list = repo.findByDateBetweenOrderByDate(start, end);
        List<ExpenseResponse> out = new ArrayList<>();
        for (Expense e : list) {
            out.add(toResponse(e));
        }
        return out;
    }

    public Long monthTotal(YearMonth ym) {
        return repo.sumByMonth(ym.toString());
    }

    private ExpenseResponse toResponse(Expense e) {
        return new ExpenseResponse(
                e.getId(),
                e.getAmount(),
                e.getCategory(),
                e.getDate(),
                e.getDescription()
        );
    }
    public List<Expense> findByCategory(String category) {
        return repo.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Expense> findByDateBetween(LocalDate start, LocalDate end) {
        return repo.findByDateBetweenOrderByDate(start, end);
    }

    public List<Expense> findByMonthAndCategory(LocalDate start, LocalDate end, String category) {
        return repo.findByDateBetweenAndCategoryIgnoreCaseOrderByDateDesc(start, end, category);
    }
    public List<Expense> getAllExpenses() {
        return repo.findAll();
    }
    public List<String> getAllCategories() {
        return repo.findDistinctCategories();
    }
}

