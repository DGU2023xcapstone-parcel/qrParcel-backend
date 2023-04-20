package com.capstone.project.repository;

import com.capstone.project.domain.entity.TrackingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackingInfoRepository extends JpaRepository<TrackingInfo, Long> {

    Optional<TrackingInfo> findByInvoiceNo(String invoiceNo);
}
