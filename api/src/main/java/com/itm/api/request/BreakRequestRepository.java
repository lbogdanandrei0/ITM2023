package com.itm.api.request;

import com.itm.api.request.model.BreakRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakRequestRepository extends JpaRepository<BreakRequest, Long> {
}
