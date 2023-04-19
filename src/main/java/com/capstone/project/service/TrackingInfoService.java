package com.capstone.project.service;

import com.capstone.project.domain.dto.InputParcelDto;
import com.capstone.project.domain.dto.ParcelDto;
import com.capstone.project.domain.dto.ParcelStateDto;
import com.capstone.project.domain.entity.Member;
import com.capstone.project.domain.entity.TrackingInfo;
import com.capstone.project.domain.entity.state.State;
import com.capstone.project.repository.TrackingInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TrackingInfoService {

    private final MemberService memberService;
    private final TrackingInfoRepository trackingInfoRepository;

    @Transactional
    public ParcelDto connectMemberAndTrackingInfo(Member member, final InputParcelDto inputParcelDto){
        TrackingInfo saveTracking = trackingInfoRepository.save(
                TrackingInfo.builder()
                        .invoiceNo(inputParcelDto.getInvoiceNo())
                        .productName(inputParcelDto.getProductName())
                        .receiverAddress(inputParcelDto.getReceiverAddress())
                        .sender(inputParcelDto.getSender())
                        .member(member)
                        .isComplete(false)
                        .state(new State("INIT"))
                        .build()
        );
        memberService.connectMemberToTracking(member, saveTracking);

        return ParcelDto.builder()
                .invoiceNo(saveTracking.getInvoiceNo())
                .sender(saveTracking.getSender())
                .productName(saveTracking.getProductName())
                .receiverAddress(saveTracking.getReceiverAddress())
                .build();
    }
    @Transactional
    public ParcelStateDto updateTrackingInfoReadyState(TrackingInfo trackingInfo, String receiverName, String receiverPhoneNum){
        State state = trackingInfo.getState();
        State updateState = state.updateStateReady();

        TrackingInfo save = trackingInfoRepository.save(
                TrackingInfo.builder()
                        .invoiceNo(trackingInfo.getInvoiceNo())
                        .productName(trackingInfo.getProductName())
                        .receiverAddress(trackingInfo.getReceiverAddress())
                        .sender(trackingInfo.getSender())
                        .member(trackingInfo.getMember())
                        .isComplete(false)
                        .state(updateState)
                        .build()
        );

        return ParcelStateDto.builder()
                .invoiceNo(save.getInvoiceNo())
                .productName(save.getProductName())
                .receiverAddress(save.getReceiverAddress())
                .sender(save.getSender())
                .receiverName(receiverName)
                .receiverPhoneNum(receiverPhoneNum)
                .curState(save.getState().getCurState())
                .build();

    }
}
