package com.innovat.quietanzeservice.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "QUIETANZE")
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class Quietanza implements Serializable{
   /**
	 * 
	 */
	private static final long serialVersionUID = -3518018508482697232L;

	@Id 
	@GeneratedValue
	@Column(name = "ID_QUIETANZA", length = 50, unique = true)
	private Long id;
	
	@Column(name = "ID_DELEGA", length = 50, unique = true)
    @NotNull(message="{NotNull.Quietanza.idDelega}")
    @Size(min = 4, max = 50, message="{Size.Quietanza.idDelega}")
    private String idDelega;
	
	@Column(name = "CODICE_CONTRATTO", length = 50)
    @NotNull(message="{NotNull.Quietanza.codiceContratto}")
    @Size(min = 2, max = 50, message="{Size.Quietanza.codiceContratto}")
    private String codContratto;
	
	@Column(name = "QUIETANZA")
    @NotNull(message="{NotNull.Quietanza.quietanza}")
    private byte[] quietanza;
	
	@Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;
	
	public Quietanza() {
		this.createdDate = new Date();
	}
	
	public Quietanza(String codiceIdentificativoDelegaF24ZZ, String codiceContrattoPagamentoOnline, byte[] quietanza) {
		this.id = null;
		this.idDelega = codiceIdentificativoDelegaF24ZZ;
		this.codContratto = codiceContrattoPagamentoOnline;
		this.quietanza = quietanza;
		this.createdDate = new Date();
	}

}