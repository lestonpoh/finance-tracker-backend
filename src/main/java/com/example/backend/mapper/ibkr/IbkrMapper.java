package com.example.backend.mapper.ibkr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.backend.model.ibkr.IbkrReportDTO;
import com.example.backend.model.ibkr.getIbkrReportApiResponse.CashReportCurrency;
import com.example.backend.model.ibkr.getIbkrReportApiResponse.FlexQueryResponseDTO;
import com.example.backend.model.ibkr.getIbkrReportApiResponse.OpenPosition;
import com.example.backend.utility.NumberUtil;

@Component
public class IbkrMapper {

    public static IbkrReportDTO toIbkrReportDTO(FlexQueryResponseDTO flexQueryResponse) {
        IbkrReportDTO ibkrReportDTO = new IbkrReportDTO();

        // map cash positions
        List<IbkrReportDTO.Cash> cashList = new ArrayList<>();
        for (CashReportCurrency c : flexQueryResponse.getFlexStatements()
                .getFlexStatement()
                .getCashReport()
                .getCashReportCurrencyList()) {
            float value = NumberUtil.roundToDp(Float.parseFloat(c.getSlbNetCash()), 2);

            if ("BASE_SUMMARY".equals(c.getCurrency())) {
                ibkrReportDTO.setTotalCashSgd(value);
            } else {
                IbkrReportDTO.Cash cash = new IbkrReportDTO.Cash();
                cash.setCurrency(c.getCurrency());
                cash.setValue(value);
                cashList.add(cash);
            }
        }
        ibkrReportDTO.setCashList(cashList);

        // map stock positions
        List<IbkrReportDTO.Position> positionList = new ArrayList<>();

        for (OpenPosition p : flexQueryResponse.getFlexStatements()
                .getFlexStatement()
                .getOpenPositions()
                .getOpenPositionList()) {
            IbkrReportDTO.Position position = new IbkrReportDTO.Position();
            position.setCurrency(p.getCurrency());
            position.setSymbol(p.getSymbol());
            position.setDescription(p.getDescription());
            position.setPosition(NumberUtil.roundToDp(Float.parseFloat(p.getPosition()), 2));
            position.setPositionValue(NumberUtil.roundToDp(Float.parseFloat(p.getPositionValue()), 2));
            position.setPositionValueSGD(NumberUtil
                    .roundToDp(Float.parseFloat(p.getPositionValue()) * Float.parseFloat(p.getFxRateToBase()), 2));
            position.setCostPrice(NumberUtil.roundToDp(Float.parseFloat(p.getCostBasisPrice()), 2));
            position.setCurrentPrice(NumberUtil.roundToDp(Float.parseFloat(p.getMarkPrice()), 2));
            position.setUnrealizedGains(NumberUtil.roundToDp(Float.parseFloat(p.getFifoPnlUnrealized()), 2));

            float percent = Float.parseFloat(p.getCostBasisPrice()) == 0 ? 0
                    : (Float.parseFloat(p.getMarkPrice())
                            - Float.parseFloat(p.getCostBasisPrice())) / Float.parseFloat(p.getCostBasisPrice()) * 100;
            position.setUnrealizedGainsPercent(NumberUtil.roundToDp(percent, 2));
            positionList.add(position);
        }

        ibkrReportDTO.setPositionList(positionList);

        // set total position and asset values
        float totalPositionValueSGD = positionList.stream()
                .map(IbkrReportDTO.Position::getPositionValueSGD)
                .reduce(0f, Float::sum);
        ibkrReportDTO.setTotalPostionValueSGD(totalPositionValueSGD);
        ibkrReportDTO.setTotalAssetValueSGD(totalPositionValueSGD + ibkrReportDTO.getTotalCashSgd());

        return ibkrReportDTO;
    }
}
