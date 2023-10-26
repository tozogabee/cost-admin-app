package cost.admin.app.service;

import cost.admin.app.AppApplication;
import cost.admin.app.exception.CategoryNotFoundException;
import cost.admin.app.exception.EntityNotFoundException;
import cost.admin.app.model.Currency;
import cost.admin.app.model.SpendEntity;
import cost.admin.app.model.category.ProductCategory;
import cost.admin.app.model.repository.SpendRepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AppApplication.class)
@Transactional
public class SpendServiceTest {

    @Autowired
    private SpendService spendService;


    @Test
    public void testSavedEntityToDB() {
        //Given
        SpendEntity newEntity = createNewEntity(ProductCategory.FOOD,"Chichken breast",LocalDateTime.now(), 1450, Currency.HUF);
        this.spendService.save(newEntity);

        //When
        List<SpendEntity> getSavedEntity = this.spendService.getAllSpends();

        //Then
        assertTrue(getSavedEntity.size() == 1);
    }

    @Test
    public void testUpdatingASavedEntityinDB() throws EntityNotFoundException {
        //Given
        SpendEntity newEntity = createNewEntity(ProductCategory.FOOD,"Chichken breast",LocalDateTime.now(), 1450, Currency.HUF);
        this.spendService.save(newEntity);
        SpendEntity updatedEntity = createNewEntity(newEntity.getId(),ProductCategory.FINANCIAL, "Stock Market", LocalDateTime.now(), 1450, Currency.HUF);
        this.spendService.update(updatedEntity);

        //When
        List<SpendEntity> entities = this.spendService.getAllSpends();

        //Then
        assertTrue(entities.size() == 1);
        assertEquals(ProductCategory.FINANCIAL, entities.get(0).getCategory());
        assertEquals("Stock Market", entities.get(0).getSummary());
    }

    @Test
    public void testCalculateSumOfPricePerCategories() throws EntityNotFoundException {
        //Given
        SpendEntity newEntity1 = createNewEntity(ProductCategory.FOOD,"Chichken breast",LocalDateTime.now(), 1450, Currency.HUF);
        SpendEntity newEntity2 = createNewEntity(ProductCategory.FOOD,"Beef steak",LocalDateTime.now(), 4560, Currency.HUF);
        SpendEntity newEntity3 = createNewEntity(ProductCategory.FINANCIAL, "Stock Market", LocalDateTime.now(), 4567, Currency.CHF);
        this.spendService.save(newEntity1);
        this.spendService.save(newEntity2);
        this.spendService.save(newEntity3);

        //When
        List<SpendEntity> entities = this.spendService.getAllSpends();
        Map<ProductCategory,Double> pricePerCategory = this.spendService.priceListByCategories(entities);

        //Then
        assertTrue(pricePerCategory.size() == 2);
        assertNotNull(pricePerCategory.get(ProductCategory.FOOD));
        assertNotNull(pricePerCategory.get(ProductCategory.FINANCIAL));
        assertEquals(6010,pricePerCategory.get(ProductCategory.FOOD));
        assertEquals(4567,pricePerCategory.get(ProductCategory.FINANCIAL));
    }

    @Test
    public void testCalculationByFinancialCategory() throws CategoryNotFoundException {
        //Given
        SpendEntity newEntity1 = createNewEntity(ProductCategory.FINANCIAL, "Stock Market", LocalDateTime.now(), 4567, Currency.CHF);
        SpendEntity newEntity2 = createNewEntity(ProductCategory.FINANCIAL, "Home", LocalDateTime.now(), 8888, Currency.CHF);
        SpendEntity newEntity3 = createNewEntity(ProductCategory.FINANCIAL, "Rolex", LocalDateTime.now(), 8888, Currency.CHF);
        this.spendService.save(newEntity1);
        this.spendService.save(newEntity2);
        this.spendService.save(newEntity3);

        //When
        Map<ProductCategory, Double> pricePerFinancial = this.spendService.getSumPriceByCategory(ProductCategory.FINANCIAL);

        //Then
        assertTrue(pricePerFinancial.size() == 1);
        assertEquals(22343,pricePerFinancial.get(ProductCategory.FINANCIAL));
    }

    @Test
    public void testCalculationInOneMothPerCategory() throws CategoryNotFoundException {
        //Given
        SpendEntity newEntity1 = createNewEntity(ProductCategory.FINANCIAL, "Stock Market", LocalDateTime.now(), 4567, Currency.CHF);
        SpendEntity newEntity2 = createNewEntity(ProductCategory.FINANCIAL, "Home", LocalDateTime.now(), 8888, Currency.CHF);
        SpendEntity newEntity3 = createNewEntity(ProductCategory.FINANCIAL, "Rolex", LocalDateTime.now(), 8888, Currency.CHF);
        SpendEntity newEntity4 = createNewEntity(ProductCategory.FOOD, "Chicken", LocalDateTime.now(), 1234, Currency.CHF);

        this.spendService.save(newEntity1);
        this.spendService.save(newEntity2);
        this.spendService.save(newEntity3);
        this.spendService.save(newEntity4);

        //When
        Map<String, Map<ProductCategory,Double>> priceInOneMonth = this.spendService.getSumPriceBeetweenToDates(LocalDateTime.of(2023,10,1,0,0),LocalDateTime.of(2023,11,1,0,0));

        //Then
        assertTrue(priceInOneMonth.size() == 1);
        assertEquals(22343,priceInOneMonth.get("2023-OCTOBER").get(ProductCategory.FINANCIAL));
        assertEquals(1234,priceInOneMonth.get("2023-OCTOBER").get(ProductCategory.FOOD));

    }



    private SpendEntity createNewEntity(ProductCategory category, String summary, LocalDateTime dateTime, double price, Currency currency) {
        SpendEntity spendEntity = new SpendEntity();
        spendEntity.setCategory(category);
        spendEntity.setSummary(summary);
        spendEntity.setPaid(dateTime);
        spendEntity.setSum(price);
        spendEntity.setCurrency(currency);
        return spendEntity;
    }

    private SpendEntity createNewEntity(Long id,ProductCategory category, String summary, LocalDateTime dateTime, double price, Currency currency) {
        SpendEntity spendEntity = new SpendEntity();
        spendEntity.setId(id);
        spendEntity.setCategory(category);
        spendEntity.setSummary(summary);
        spendEntity.setPaid(dateTime);
        spendEntity.setSum(price);
        spendEntity.setCurrency(currency);
        return spendEntity;
    }

    @AfterClass
    public void clearAllDatas() {
        this.spendService.deleteAllEntities();
    }

}
