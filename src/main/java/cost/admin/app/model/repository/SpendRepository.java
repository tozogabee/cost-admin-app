package cost.admin.app.model.repository;

import cost.admin.app.model.SpendEntity;
import cost.admin.app.model.category.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpendRepository extends JpaRepository<SpendEntity, Long> {

    List<SpendEntity> findSpendEntitiesByCategory(ProductCategory productCategory);

    @Query("SELECT S FROM SpendEntity S WHERE S.paid >= :fromDate AND S.paid <= :toDate")
    List<SpendEntity> findSpendEntitiesBetweenMonth(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);
}



/*
@Repository
public interface AppointmentEntityRepository extends JpaRepository<AppointmentEntity, Long> {

    @Query("SELECT a FROM AppointmentEntity a WHERE (a.from <= :fromDate AND a.to > :fromDate) OR (a.from < :toDate AND a.to >= :toDate)")
    List<AppointmentEntity> findAppointmentEntitiesBetweenFromAndTo(@Param("fromDate") LocalDateTime fromDate,@Param("toDate") LocalDateTime toDate);
}
 */