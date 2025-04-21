package com.alxsshv.repository;

import com.alxsshv.model.ServingSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServingSizeRepository
        extends JpaRepository<ServingSize, Long> {
}
