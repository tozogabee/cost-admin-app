package cost.admin.app.service;

import cost.admin.app.exception.EntityNotFoundException;
import cost.admin.app.model.SpendEntity;
import cost.admin.app.model.category.ProductCategory;
import cost.admin.app.model.repository.SpendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SpendService {

    private SpendRepository spendRepository;

    @Autowired
    public SpendService(SpendRepository spendRepository) {
        this.spendRepository = spendRepository;
    }

    public List<SpendEntity> getAllSpends() {
        return this.spendRepository.findAll();
    }

    @Transactional
    public SpendEntity save(SpendEntity spendEntity) {
        return this.spendRepository.save(spendEntity);
    }

    @Transactional
    public void deleteEntity(long id) throws EntityNotFoundException {
        Optional<SpendEntity> foundEntity = this.spendRepository.findById(id);
        if(foundEntity.isPresent()) {
            this.spendRepository.delete(foundEntity.get());
        } else {
            throw new EntityNotFoundException(foundEntity.get().getId()+" ID entity not exist");
        }
    }

    @Transactional
    public void deleteAllEntities() {
        this.spendRepository.deleteAll();
    }

    @Transactional
    public SpendEntity update(SpendEntity spendEntity) throws EntityNotFoundException {
        Optional<SpendEntity> existEntity = this.spendRepository.findById(spendEntity.getId());
        if(existEntity.isPresent()) {
            existEntity.get().setCategory(spendEntity.getCategory());
            existEntity.get().setSummary(spendEntity.getSummary());
            existEntity.get().setSum(spendEntity.getSum());
            existEntity.get().setPaid(spendEntity.getPaid());
            existEntity.get().setCurrency(spendEntity.getCurrency());
            return this.spendRepository.save(existEntity.get());
        } else {
            throw new EntityNotFoundException("Spend entity data not found by ID "+spendEntity.getId());
        }
    }

    public Map<ProductCategory,Double> priceListByCategories(List<SpendEntity> spendEntities) {
        Map<ProductCategory, Double> priceMap = new HashMap<>();
        double sumOfPrice = 0;
        for(SpendEntity spendEntity : spendEntities) {
            if(priceMap.get(spendEntity.getCategory()) != null) {
                sumOfPrice += spendEntity.getSum();
                priceMap.put(spendEntity.getCategory(),sumOfPrice);
            } else {
                priceMap.put(spendEntity.getCategory(), spendEntity.getSum());
            }
        }
        return priceMap;
    }

    public double getSumPriceByCategory(ProductCategory category) {
        double sumOfPrice = 0;
        List<SpendEntity> foundByCategory = this.spendRepository.findSpendEntitiesByCategory(category);
        if(!foundByCategory.isEmpty()) {
            for (SpendEntity actEntity : foundByCategory) {
                sumOfPrice += actEntity.getSum();
            }
        }
        return sumOfPrice;
    }

    public Map<LocalDateTime, Map<ProductCategory, Double>> getSumPriceBeetweenToDates(LocalDateTime fromDate,LocalDateTime toDate) {
        List<SpendEntity> allEntitiesBeetweenTwoDates = this.spendRepository.findSpendEntitiesBetweenMonth(fromDate,toDate);
        Map<LocalDateTime, Map<ProductCategory,Double>> priceListByDate = new HashMap<>();
        Map<ProductCategory, Double> priceByCategory = new HashMap<>();
        double sumOfPrice = 0;
        for(SpendEntity entity : allEntitiesBeetweenTwoDates) {

            if(priceByCategory.get(entity.getCategory()) != null) {
                sumOfPrice += entity.getSum();
                priceByCategory.put(entity.getCategory(), sumOfPrice);
                priceListByDate.put(LocalDateTime.of(entity.getPaid().getYear(),entity.getPaid().getMonth(),entity.getPaid().getDayOfMonth(),0,0),priceByCategory);
            } else {
                priceByCategory.put(entity.getCategory(), entity.getSum());
                priceListByDate.put(LocalDateTime.of(entity.getPaid().getYear(),entity.getPaid().getMonth(),entity.getPaid().getDayOfMonth(),0,0),priceByCategory);
            }
        }

        return priceListByDate;
    }



}
