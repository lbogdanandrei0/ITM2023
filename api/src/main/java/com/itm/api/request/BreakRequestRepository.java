package com.itm.api.request;

import com.itm.api.request.model.BreakRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BreakRequestRepository extends JpaRepository<BreakRequest, Long> {

    @Query(nativeQuery = true, value = "select * from break_request where external_uuid = cast(:externalUuid as uuid)")
    Optional<BreakRequest> findByExternalUuid(@Param("externalUuid") String externalUuid);

}
