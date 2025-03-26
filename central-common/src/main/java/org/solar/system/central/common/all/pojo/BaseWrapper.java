package org.solar.system.central.common.all.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
@JsonInclude(Include.NON_NULL)
public abstract class BaseWrapper implements Serializable{
	
	private static final long serialVersionUID = 1L;
	protected boolean success;
	protected String date;
	protected String message;

}
