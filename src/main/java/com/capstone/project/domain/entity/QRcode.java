package com.capstone.project.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class QRcode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QRCODE_ID")
    private Long id;

    @Column(nullable = false)
    private String invoiceNo;

    @Embedded
    private Address receiverAddress;

    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String receiverPhoneNumber;

}
