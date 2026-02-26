package application.controller;

import application.dto.ExpenseRequest;
import application.dto.ExpenseResponse;
import application.entity.Expense;
import application.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseViewController {

    private final ExpenseService service;

    public ExpenseViewController(ExpenseService service) {
        this.service = service;
    }

    @GetMapping
    public String listExpenses(Model model) {

        List<ExpenseResponse> expenses = service.listAllExpenses();

        double total = expenses.stream()
                .mapToDouble(ExpenseResponse::getAmount)
                .sum();

        model.addAttribute("expenses", expenses);
        model.addAttribute("totalAmount", total);
        model.addAttribute("categories", service.getAllCategories());

        return "expenses/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("expense", new ExpenseRequest());
        return "expenses/form";
    }

    @PostMapping
    public String createExpense(@Valid @ModelAttribute("expense") ExpenseRequest request,
                                BindingResult result) {

        if (result.hasErrors()) {
            return "expenses/form";
        }

        service.createExpense(request);
        return "redirect:/expenses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable ("id") Long id, Model model) {
        ExpenseResponse expense = service.findById(id);

        ExpenseRequest request = new ExpenseRequest();
        request.setAmount(expense.getAmount());
        request.setCategory(expense.getCategory());
        request.setDate(expense.getDate());
        request.setDescription(expense.getDescription());

        model.addAttribute("expense", request);
        model.addAttribute("expenseId", id);

        return "expenses/edit-form";
    }

    @PostMapping("/update/{id}")
    public String updateExpense(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("expense") ExpenseRequest request,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "expenses/edit-form";
        }
        service.updateExpense(id, request);
        return "redirect:/expenses";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable ("id") Long id) {
        service.deleteById(id);
        return "redirect:/expenses";
    }
    @GetMapping("/filter")
    public String filterExpenses(
            @RequestParam(value = "yearMonth", required = false) String yearMonth,
            @RequestParam(value = "category", required = false) String category,
            Model model) {

        List<Expense> expenses;

        boolean hasMonth = (yearMonth != null && !yearMonth.isBlank());
        boolean hasCategory = (category != null && !category.isBlank());

        if (hasMonth && hasCategory) {

            YearMonth ym = YearMonth.parse(yearMonth);
            LocalDate start = ym.atDay(1);
            LocalDate end = ym.atEndOfMonth();

            expenses = service.findByMonthAndCategory(start, end, category);

        } else if (hasMonth) {

            YearMonth ym = YearMonth.parse(yearMonth);
            LocalDate start = ym.atDay(1);
            LocalDate end = ym.atEndOfMonth();

            expenses = service.findByDateBetween(start, end);

        } else if (hasCategory) {

            expenses = service.findByCategory(category);

        } else {

            expenses = service.getAllExpenses();
        }

        double total = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        model.addAttribute("expenses", expenses);
        model.addAttribute("totalAmount", total);
        model.addAttribute("categories", service.getAllCategories());
        model.addAttribute("selectedMonth", yearMonth);
        model.addAttribute("selectedCategory", category);

        return "expenses/list";
    }
}