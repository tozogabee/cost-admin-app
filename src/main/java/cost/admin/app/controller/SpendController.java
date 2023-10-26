package cost.admin.app.controller;

import cost.admin.app.exception.CategoryNotFoundException;
import cost.admin.app.exception.EntityNotFoundException;
import cost.admin.app.model.SpendEntity;
import cost.admin.app.model.category.ProductCategory;
import cost.admin.app.model.input.SpendDto;
import cost.admin.app.service.SpendService;
import cost.admin.app.util.SpendEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class SpendController {
    @Autowired
    private SpendService spendService;

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SpendEntity>> getAllSpendEntities() {
        return ResponseEntity.ok(this.spendService.getAllSpends());
    }

    @PostMapping(value = "/addSpent", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SpendEntity> addNewSpent(@RequestBody SpendDto spendDto) {
        SpendEntity newEntity = SpendEntityMapper.MAPPER.convertToEntityFromDto(spendDto);
        return ResponseEntity.ok(this.spendService.save(newEntity));
    }

    @DeleteMapping(value = "/delete", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteAllEntities() {
        this.spendService.deleteAllEntities();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteEntityById(@PathVariable long id) throws EntityNotFoundException {
       this.spendService.deleteEntity(id);
       return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/update", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SpendEntity> updateEntity(@RequestBody SpendDto spendDto) throws EntityNotFoundException {
        SpendEntity updateTableEntity = SpendEntityMapper.MAPPER.convertToEntityFromDto(spendDto);
        return ResponseEntity.ok(this.spendService.update(updateTableEntity));
    }

    @GetMapping(value = "/stats/categories/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<ProductCategory,Double>> statsByCategories() {
        List<SpendEntity> spendEntities = this.spendService.getAllSpends();
        Map<ProductCategory, Double> priceMap = this.spendService.priceListByCategories(spendEntities);
        return ResponseEntity.ok(priceMap);
    }

    @GetMapping(value="/stats/categories", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<ProductCategory,Double>> statsByGivenCategory(@RequestParam ProductCategory category) throws CategoryNotFoundException {
        return ResponseEntity.ok(this.spendService.getSumPriceByCategory(category));
    }

    @GetMapping(value = "/stats", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Map<ProductCategory, Double>>> statsByBetweenTwoDates(@RequestParam("fromDate") LocalDateTime fromDate, @RequestParam("toDate") LocalDateTime toDate){
        Map<String, Map<ProductCategory,Double>> sumBetweenTwoDates = this.spendService.getSumPriceBeetweenToDates(fromDate,toDate);
        return ResponseEntity.ok(sumBetweenTwoDates);
    }

}
