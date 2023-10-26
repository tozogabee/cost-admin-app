package cost.admin.app.service;

import cost.admin.app.AppApplication;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AppApplication.class)
@Transactional
public class SpendServiceTest {

    @Autowired
    private SpendService spendService;


    @Test
    public void testSavedEntityToDB() {
        SpendEntity newEntity = createNewEntity(ProductCategory.FOOD,"Chichken breast",LocalDateTime.now(), 1450, Currency.HUF);
        this.spendService.save(newEntity);
        List<SpendEntity> getSavedEntity = this.spendService.getAllSpends();
        assertTrue(getSavedEntity.size() == 1);
    }

    @Test
    public void testUpdatingASavedEntityinDB() throws EntityNotFoundException {
        SpendEntity newEntity = createNewEntity(ProductCategory.FOOD,"Chichken breast",LocalDateTime.now(), 1450, Currency.HUF);
        this.spendService.save(newEntity);
        SpendEntity updatedEntity = createNewEntity(newEntity.getId(),ProductCategory.FINANCIAL, "Stock Market", LocalDateTime.now(), 1450, Currency.HUF);
        this.spendService.update(updatedEntity);
        List<SpendEntity> entities = this.spendService.getAllSpends();
        assertTrue(entities.size() == 1);
        assertEquals(ProductCategory.FINANCIAL, entities.get(0).getCategory());
        assertEquals("Stock Market", entities.get(0).getSummary());
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
