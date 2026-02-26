package application.controller;

import application.dto.ExpenseRequest;
import application.dto.ExpenseResponse;
import application.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/expenses")
public class ExpenseViewController {

    private final ExpenseService service;

    public ExpenseViewController(ExpenseService service) {
        this.service = service;
    }

    @GetMapping
    public String listExpenses(Model model) {
        model.addAttribute("expenses", service.listAllExpenses());
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
    public String updateExpense(@PathVariable ("id") Long id,
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
}