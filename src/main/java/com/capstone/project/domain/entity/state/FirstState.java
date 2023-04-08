package com.capstone.project.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FirstState {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FIRST_STATE_ID")
    private Long id;

    @Column(nullable = false)
    private String workerName;

    @Column(nullable = false)
    private String workerPhoneNumber;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String locationNumber;

    @Column(nullable = false)
    private String curState;
    
}
