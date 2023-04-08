package com.capstone.project.domain.entity;

import com.capstone.project.BaseTimeEntity;
import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

public class TrakingInfo extends BaseTimeEntity implements Serializable {
    
}
