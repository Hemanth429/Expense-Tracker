package application.repo;

import application.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Expense> findByDateBetweenOrderByDate(LocalDate start, LocalDate end);
    List<Expense> findByDateBetweenAndCategoryIgnoreCaseOrderByDateDesc(
            LocalDate start,
            LocalDate end,
            String category
    );
    @Query(value = "SELECT COALESCE(SUM(amount), 0) FROM expenses WHERE DATE_FORMAT(date, '%Y-%m') = ?1", nativeQuery = true)
    Long sumByMonth(String yearMonth);
    @Query("SELECT DISTINCT e.category FROM Expense e ORDER BY e.category")
    List<String> findDistinctCategories();
}

