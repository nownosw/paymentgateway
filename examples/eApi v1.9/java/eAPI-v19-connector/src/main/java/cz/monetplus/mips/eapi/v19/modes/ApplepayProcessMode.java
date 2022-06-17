package cz.monetplus.mips.eapi.v19.modes;

import cz.monetplus.mips.eapi.v19.ArgsConfig;
import cz.monetplus.mips.eapi.v19.RunMode;
import cz.monetplus.mips.eapi.v19.connector.entity.responses.ApplepayProcessResponse;
import cz.monetplus.mips.eapi.v19.service.ExamplesService;
import cz.monetplus.mips.eapi.v19.service.MipsException;
import cz.monetplus.mips.eapi.v19.service.RespCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplepayProcessMode implements RunMode {
    final
    ExamplesService examplesService;

    public ApplepayProcessMode(ExamplesService examplesService) {
        this.examplesService = examplesService;
    }

    @Override
    public void proc(ArgsConfig aConfig) {
        try {
            if (StringUtils.trimToNull(aConfig.payId) == null) {
                throw new MipsException(RespCode.INVALID_PARAM, "Missing mandatory parameter payId");
            }
            ApplepayProcessResponse response = examplesService.applepayProcess(aConfig.payId);
            log.info("result code: {} [{}], payment status {}, payId {}", response.getResultCode(), response.getResultMessage(), response.getPaymentStatus(), response.getPayId());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArgsConfig.RunModeEnum getMode() {
        return ArgsConfig.RunModeEnum.APPLEPAY_PROCESS;
    }
}
