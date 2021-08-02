package com.innovat.quietanzeservice.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel
@Data
@ToString
public class RicezioneQuietanzaRequest {
	
    @NotNull(message="{NotNull.Quietanza.idDelega}")
    @Size(min = 4, max = 50, message="{Size.Quietanza.idDelega}")
    private String codiceIdentificativoDelegaF24ZZ;
	
    @NotNull(message="{NotNull.Quietanza.codiceContratto}")
    @Size(min = 2, max = 50, message="{Size.Quietanza.codiceContratto}")
    private String codiceContrattoPagamentoOnline;
	

    @ApiModelProperty(name = "quietanza", dataType = "byte[]", example = "[1, 12, 23]")
    @NotNull(message="{NotNull.Quietanza.quietanza}")
    private byte[] quietanza;
	
	public RicezioneQuietanzaRequest() {}

}
